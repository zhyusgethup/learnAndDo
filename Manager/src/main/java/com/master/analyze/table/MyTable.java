package com.master.analyze.table;


import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.master.analyze.http.TableNeed;
import com.master.analyze.analyeBean.*;
import com.master.cons.Cons;
import com.master.gm.service.analyze.DBService;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.StringUtils;
import com.master.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MyTable extends AbstractTable{
    public static final Logger log = LoggerFactory.getLogger(MyTable.class);
    @Getter
    protected int varType = Cons.NO_VAR_COL;
    @Getter
    protected String tableStr;//
    @Getter
    protected String tableName;//
    @Getter
    protected List<ColumnEnum> columnEnums;//
    @Getter
    protected int lastRowIndex; //size 数值上劲量=size 计算方式是逻辑上递增,所以请务必在逻辑中递增.
    @Getter //向前缓存的日志天数  用于启动加载和内存清理
    private int preDays = 30;
    /****
     * 下面的结构式为了扩展表的列 注册表头需要填个容器
     */
    @Getter
    protected Map<String, Integer> head = new HashMap<>();

    protected String getPreTimeStr() {
        long endClock = TimeUtil.getTodayStartClock();
        long beforeClock = endClock - TimeUtil.DAY_MIL * 30;
        return TimeUtil.formateLongByFormatter(TimeUtil.YMD, beforeClock);
    }

    @Getter
    @Setter
    protected TreeBasedTable<Integer, Integer, String> datas;

    public boolean isEmpty() {
        System.out.println("size" + datas.rowMap().size());
        return datas.rowMap().size() <= 1;
    }

    protected MyTable() {
    }

    public long getEndClock() {
        return TimeUtil.getTodayStartClock();
    }

    public String fixTimeStrFromMysql(String mysqlTimeStr) {
        return mysqlTimeStr.substring(0, 10);
    }

    public String getNearTimeStr() {
        long endClock = getEndClock();
//        long endClock = 1548864000000L;
        long beforeClock = endClock - TimeUtil.DAY_MIL;
        return TimeUtil.formateLongByFormatter(TimeUtil.YMD, beforeClock);
    }


    public void clearTable(){
        datas.clear();
        head.clear();
        for (int i = 0; i < columnEnums.size(); i++) {
            datas.put(0, i, columnEnums.get(i).getWord());
            head.put(columnEnums.get(i).getKey(), i);
        }
        lastRowIndex = 1;
    }
    /***
     * 服务器启动时表的逻辑
     * @param service
     */
    public StringBuilder startupLoad(LoginLogoutService service) {
        StringBuilder msg = new StringBuilder(getTableFullName() + "开始初始化数据;");
        try {
            String endClock = TimeUtil.formateLongByFormatter(TimeUtil.LEAST_HOUR, getEndClock());
            msg.append("时间参数为:" + endClock + ";");
            int mysql_size = service.getDBService().query(this, getPreTimeStr(), endClock, msg);
            msg.append("从mysql中查找数据,找到的数据量为:" + mysql_size + ";");
            if (!isTableContainsValueInThatCol(ColumnEnum.TIME, getNearTimeStr())) {
                int log_size = generateDatasFromLogs(service, msg);
                msg.append("从elasticsearch中提取分析出的数据量为:" + log_size + ";");
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg.append("初始化错误{" + e.getMessage() + "}");
        }
        return msg;
    }

    private String getTableFullName() {
        return tableName + "[" + tableStr + "]";
    }

    /***
     * 抓取日志并且做清理检查
     * @param service
     */
    public StringBuilder fetchFromLogsAndCheckClear(LoginLogoutService service) {
        StringBuilder msg = new StringBuilder(getTableFullName() + "开始抓取数据;");
        try {
            msg.append("时间参数为:" + getNearTimeStr() + ";");
            int log_size = generateDatasFromLogs(service, msg);
            msg.append("从elasticsearch中提取分析出的数据量为:" + log_size + ";");
            int reduce = clearCacheByTimeStr("2019-01-01 00", getPreTimeStr());
            msg.append("开始检查可清理数据,清除的数据量:" + reduce + ";");
        } catch (Exception e) {
            e.printStackTrace();
            msg.append("再次抓取错误{" + e.getCause() + "}");
        }
        return msg;

    }

    public MyTable(TableEnum e) {
        tableName = e.getTableName();
        columnEnums = e.getColumnEnums();
//        headNames = new LinkedList<>();
//        headKeys = new LinkedList<>();
        datas = TreeBasedTable.create((o1, o2) -> o1 - o2, (o1, o2) -> o1 - o2);
        for (int i = 0; i < columnEnums.size(); i++) {
            datas.put(0, i, columnEnums.get(i).getWord());
            head.put(columnEnums.get(i).getKey(), i);
//            headNames.add(columnEnums.get(i).getWord());
//            headKeys.add(columnEnums.get(i).getName());
            lastColIndex++;
/*            if(columnEnums.get(i).getKey() == ColumnEnum.VAR_COL)
                lastColIndex--;*/
        }
        tableStr = e.getTableStr();
        lastRowIndex = 1;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    protected void generateDatasIfNull(Map<String, Object> cache) {
        //用于选择性继承的钩子
    }

    /***
     * 抓取的时段日志的逻辑流程
     * @param service
     * @param msg
     */
    public int generateDatasFromLogs(LoginLogoutService service, StringBuilder msg) {
        Map<String, Object> longCache = new HashMap<>();
        long endClock = TimeEnum.DAY_BEFORE.getTimeClock();
//        long endClock = 1548864000000L;
        longCache.put("endClock", endClock);
        long beforeClock = TimeEnum.DAY_BEFORE_BEFORE.getTimeClock();
//        long beforeClock = 1548777600000L;
        longCache.put("beforeClock", beforeClock);
        longCache.put("timeStr", TimeEnum.DAY_BEFORE_BEFORE.getTimeStr());
        Map<String, String> serversConf = null;
        generateDatasIfNull(longCache);
        serversConf = TableNeed.getServersFromServerConf();
//        serversConf = genServerConf();
        int size = productRows(longCache, serversConf, service, msg);
        return size;
    }

    private String[] changeSortMapToArray(SortedMap<Integer, String> map) {
        if (null == map || map.size() == 0)
            return null;
        String[] array = new String[map.lastKey() + 1];
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            array[entry.getKey()] = entry.getValue();
        }
        return array;
    }

    /***
     * 更具列查看表是否包含值
     * @param e
     * @param value
     * @return
     */
    public boolean isTableContainsValueInThatCol(ColumnEnum e, String value) {
        int colIndex = columnEnums.indexOf(e);
        if (colIndex == -1) {
            return false;
        }
        Map<Integer, String> column = datas.column(colIndex);
        for (Map.Entry<Integer, String> entry : column.entrySet()) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }


    /***
     * 更具时间获取表中的数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getRowsIndexesByTimeStr(long startTime, long endTime) {
        int colIndex = columnEnums.indexOf(ColumnEnum.TIME);
        if (colIndex == -1) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        Map<Integer, String> column = datas.column(colIndex);
        for (Map.Entry<Integer, String> entry : column.entrySet()) {
            if(entry.getKey() != 0){
                long time = TimeUtil.aotoParseDatetimeStrToLong(entry.getValue());
                if (time >= startTime && time < endTime) {
                    list.add(entry.getKey());
                }
            }
        }
        return list;
    }

    /***
     * 用于从表中查询数据,比如按时间查询很常用
     * @param e
     * @param value
     * @return
     */
    public List<SortedMap<Integer, String>> getRowsByColAndValue(ColumnEnum e, String value) {
        int colIndex = columnEnums.indexOf(e);
        if (colIndex == -1) {
            return null;
        }
        List<SortedMap<Integer, String>> rows = new ArrayList<>();
        Map<Integer, String> column = datas.column(colIndex);
        for (Map.Entry<Integer, String> entry : column.entrySet()) {
            if (entry.getValue().equals(value)) {
                Integer rowIndex = entry.getKey();
                SortedMap<Integer, String> row = datas.row(rowIndex);
                rows.add(row);
            }
        }
        return rows;
    }

    /**
     * 按照协议转换成前端可用图像数据
     *
     * @param
     * @return 目前固定上层 server, 中层 是除了server, time 其他;
     */
    public Map toGraphicJsonStr(String value) {
        List<SortedMap<Integer, String>> rowsByColAndValue = getRowsByColAndValue(ColumnEnum.TIME, value);
        //TODO 目前server 和time 必定是 0 ,1 先不定位过滤
        SortedMap<Integer, String> head = datas.row(0);
        Map<String, List<Map<String, String>>> re = new HashMap<>();
        for (int i = 0; i < rowsByColAndValue.size(); i++) {
            SortedMap<Integer, String> row = rowsByColAndValue.get(i);
            List<Map<String, String>> list = new ArrayList<>();
            re.put(row.get(1), list);
            if (head.size() > 1) {
                Map<String, String> cols = new HashMap<>();
                list.add(cols);
                for (int j = 2; j < head.size(); j++) {
                    cols.put(head.get(j), StringUtils.replaceBlackTo0(row.get(j)));
                }
            }
        }
        return re;
    }
    /**
     * 按照协议转换成前端可用图像数据
     *
     * @param
     * @return 目前固定上层 server, 中层 是除了server, time 其他;
     *//*
    public String toGraphicJsonStr(String value) {
        List<SortedMap<Integer, String>> rowsByColAndValue = getRowsByColAndValue(ColumnEnum.TIME, value);
        //TODO 目前server 和time 必定是 0 ,1 先不定位过滤
        StringBuilder sb = new StringBuilder("{");
        SortedMap<Integer, String> head = datas.row(0);
        for (int i = 0; i < rowsByColAndValue.size(); i++) {
            SortedMap<Integer, String> row = rowsByColAndValue.get(i);
            String server = row.get(1);
            sb.append("\"" + server + "\":[");
            if (head.size() > 1) {
                sb.append("{");
                for (int j = 2; j < head.size(); j++) {
                    String col = head.get(j);
                    if (j == head.size() - 1) {
                        sb.append("\"" + col + "\":\"" + StringUtils.replaceBlackTo0(row.get(j)) + "\"");
                    } else {
                        sb.append("\"" + col + "\":\"" + StringUtils.replaceBlackTo0(row.get(j)) + "\",");
                    }
                }
                sb.append("}");
            }
            if (i == rowsByColAndValue.size() - 1) {
                sb.append("]");
            } else {
                sb.append("],");
            }
        }
        sb.append("}");
        return sb.toString();
    }*/

    /***
     * 获取全table数据
     * @return
     */
    public JsonTableBean toJsonStr() {
        return toJsonStr(1, datas.size());
    }

    /***
     * 根据分页获取json表信息
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public ResultBean getJsonStrByPageSizeAndPageIndex(int pageSize, int pageIndex) {
        int from, to;
        if (pageSize == 0 && pageIndex == 0) {
            pageSize = lastRowIndex - 1;
            pageIndex = 1;
        }
        if (pageSize < 0 || pageIndex <= 0)
            throw new AnalyeException("分页参数错误");
        int size = lastRowIndex - 1;
        from = size - pageSize * pageIndex + pageSize;
        to = size - pageSize * pageIndex;
        if (from < 1)
            throw new AnalyeException("超出最后一页");
        if(to < 1)
            to = 0;
        return new ResultBean(new PageObject(pageSize, pageIndex, lastRowIndex - 1, toJsonStr(from, to)));
    }


    /***
     * @param from 从1开始,
     * @param to 不大于datas.size()
     *           左闭右开
     * @return 2019-02-28改成统一装载bean 方便维护, 扩展
     */
//    public String toJsonStr(int from, int to) {
//        int size = datas.size();
//        if(from < 1 || from >= size || to < 2 || to > size){
//            throw new RuntimeException("table转json 参数异常");
//        }
//        String[] headNames = changeSortMapToArray(datas.row(0));
//        StringBuilder sb = new StringBuilder("{\"name\":\"" + tableName + "\",\"th\":[{\"0\":\"#\",");
//        for (int i = 0; i < headNames.length; i++) {
//            if (i != headNames.length - 1) {
//                sb.append("\"" + (i + 1) + "\":\"" + headNames[i] + "\",");
//            } else {
//                sb.append("\"" + (i + 1) + "\":\"" + headNames[i] + "\"");
//            }
//        }
//        sb.append("}],\"content\":[");
//        for (int i = from; i < to; i++) {
//            String[] strings = changeSortMapToArray(datas.row(i));
//            if (null != strings) {
//                sb.append("{\"0\":" + (i + 1) + ",");
//                for (int j = 0; j < strings.length; j++) {
//                    if (j == strings.length - 1) {
//                        sb.append("\"" + (j + 1) + "\":\"" + strings[j] + "\"");
//                    } else {
//                        sb.append("\"" + (j + 1) + "\":\"" + strings[j] + "\",");
//                    }
//                }
//                if (i == datas.size() - 1) {
//                    sb.append("}");
//                } else {
//                    sb.append("},");
//                }
//            }
//        }
//        sb.append("]}");
//        return sb.toString();
//    }

    /***
     * @param from 从1开始,
     * @param to 不大于datas.size()
     *           左闭右开
     * @return
     */
    public JsonTableBean toJsonStr(int from, int to) {
        int size = datas.size();
        if (from < 1 || from >= size || to < 0 || to > size) {
            throw new RuntimeException("table转json 参数异常");
        }
        JsonTableBean json = new JsonTableBean(tableName);
        json.addHead(datas.row(0));
        List<Map<Integer, String>> list = new ArrayList<>();
        for (int i = from; i > to; i--) {
            list.add(datas.row(i));
        }
        json.addContent(list);
        return json;
    }

    public void fillRowsIntoData(Row[] rows) {
        for (int j = 0; j < rows.length; j++) {
            datas.row(rows[j].getIndex()).putAll(rows[j].getData());
//            System.out.println(datas.row(rows[j].getIndex()));
        }
        fillBlackIntoTable();
    }

    private void fillBlackIntoTable() {
        for (int i = 0; i < lastRowIndex ; i++) {
            for (int j = 1; j < datas.row(0).size(); j++) {
                if(!datas.contains(i,j)){
                    datas.put(i,j,"");
                }
            }
        }
    }

    public String printState() {
        return "tableStr = " + tableStr + ";head size= " + columnEnums.size() + "  ;dataRows = " +
                (datas.rowMap().size() - 1) + " ; columnEnums = " + columnEnums;
    }

    public boolean containVarCol() {
        for (int i = 0; i < columnEnums.size(); i++) {
            if (columnEnums.get(i).getKey() == ColumnEnum.VAR_COL)
                return true;
        }
        return false;
    }

    public int getVarColIndex(){
        for (int i = 0; i < columnEnums.size(); i++) {
            if(columnEnums.get(i).getKey() == ColumnEnum.VAR_COL){
                return i;
            }
        }
        return -1;
    }
    /***
     * 清除表的缓存
     */
    public int clearCacheByTimeStr(String startTime, String endTime) {
        List<Integer> indexes = getRowsIndexesByTimeStr(TimeUtil.parseDatetimeStrToLong(TimeUtil.
                LEAST_HOUR, startTime), TimeUtil.parseDateStrToLong(TimeUtil.YMD, endTime));
        int now = lastRowIndex;
        for (int i = 0; i < indexes.size(); i++) {
            datas.row(indexes.get(i)).clear();
            now--;
        }
        int reduce = lastRowIndex - now;
        combine(datas, now, lastRowIndex);
        lastRowIndex = now;
        return reduce;
    }

    private void combine(Table<Integer, Integer, String> treeTable, int now, int last) {
        for (int i = 0; i <= now; i++) {
            Map<Integer, String> row = treeTable.row(i);
            if (row.isEmpty()) {
                for (int j = i; j <= last; j++) {
                    Map<Integer, String> row2 = treeTable.row(j);
                    if (row2.isEmpty())
                        continue;
                    treeTable.row(i).putAll(row2);
                    row2.clear();
                    break;
                }
            }
        }
    }


    protected int productRows(Map<String, Object> longCache, Map<String, String> serversConf, LoginLogoutService service, StringBuilder msg) {
        //流程改变,是每次启动时都先查寻mysql 在看是否需要处理日志
//        if(!service.getDBService().hasRecordInMysqlNow(tableStr,(String)longCache.get("timeStr"))){
//            log.info("发现记录已经在mysql,不对日志进行处理");
//            return;
//        }
        Row[] rows = regesterRow(longCache, serversConf);
        for (int i = 0; i < columnEnums.size(); i++) {
            columnEnums.get(i).generate(longCache, rows, service);
        }
        fillRowsIntoData(rows);
        DBService dbService = service.getDBService();
        dbService.save(this.getTableStr(), rows, msg);
        return rows.length;
    }
    @Getter@Setter //这里目前仅用于从mysql中获取可变列的index;
    private int lastColIndex;

    public Row getRowByRKey(String rkey) {
        return new Row(rkey, this, lastRowIndex++);
    }

    protected Row[] regesterRow(Map<String, Object> longCache, Map<String, String> serversConf) {
        Map<String, String> keys = new HashMap<>();
        longCache.put(Row.KEYS, keys);
        Row[] rows = new Row[serversConf.size() + 1];
        int j = 0;
        Set<Map.Entry<String, String>> entries = serversConf.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getValue();
            Row row = new Row(key, this, lastRowIndex++);
            rows[j] = row;
            j++;
            keys.put(key, entry.getKey());
        }
        Row row = new Row(Row.TOTAL_COUNT, this, lastRowIndex++);
        rows[serversConf.size()] = row;
        keys.put(Row.TOTAL_COUNT, Row.TOTAL_COUNT_WORD);
        return rows;
    }

    protected Map<String, String> genServerConf() {
        Map<String, String> serversConf = new HashMap<>();
        serversConf.put("zhyu", "2004");
        serversConf.put("ljf", "1");
        serversConf.put("hello", "11");
        return serversConf;
    }

    @Override
    public String toString() {
        return "{" + tableName + "|" + tableStr + "|" + columnEnums;
    }
}
