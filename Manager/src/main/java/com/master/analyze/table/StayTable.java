package com.master.analyze.table;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.TreeBasedTable;
import com.master.analyze.analyeBean.*;
import com.master.analyze.http.ParamCache;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;
import lombok.Getter;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.GET;
import sun.reflect.generics.tree.Tree;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@IocBean
public class StayTable extends AbstractTable {
    @Getter
    protected TreeBasedTable<Integer, Integer, String> datas;
    private Map<String,Integer> ckIndex;
    private Map<String,Integer> rkIndex;
    @Getter
    private LocalDate gameOnlineDate;
    @Getter
    private int lastRowIndex;
    @Getter
    private int lastColIndex;

    private String tableName = "全服存留表";

    public static final String TABLESTR = "stay";

    /***
     * 作为构造入口而已
     * @param
     */
    public StayTable(){
        gameOnlineDate = LocalDateTime.parse(ParamCache.getServerOnlineTime(), TimeUtil.LEAST_MINUTE).toLocalDate();
        datas = datas = TreeBasedTable.create((o1, o2) -> o1 - o2, (o1, o2) -> o1 - o2);
        ckIndex = new HashMap<>();
        rkIndex = new HashMap<>();
    }
    private static final String BLACK_WORDS = "开服时间:";

    private StringBuilder sb;
    /***
     * 构建或追加重构表结构
     * @param service
     */
    private void buildTable(LoginLogoutService service){
        sb = new StringBuilder();
        sb = new StringBuilder("开服时间为=" + gameOnlineDate.format(TimeUtil.MD_C) +" ;发现数据的size=" + datas.size()
                + "; ");
        if(datas.size() == 0){
            buildBlackTable(service);
        }else {
            checkAndBuildTable(service);
        }
    }

    /***
     *
     */
    private void buildColRowAndFill(String rowK, String colK){
        buildColRow(rowK, colK);
        fillBlackInRow();
    }

    private void fillBlackInRow() {
        SortedMap<Integer, String> row = datas.row(lastRowIndex);
        for (int i = 1; i < lastColIndex; i++) {
            row.put(i,"");
        }
    }

    private void buildColRow(String rowK, String colK){
        lastRowIndex++;
        lastColIndex++;
        ckIndex.put(colK,lastColIndex);
        rkIndex.put(rowK,lastRowIndex);
        datas.put(0, lastColIndex, colK);
        datas.put(lastRowIndex, 0 , rowK);
        SortedMap<Integer, String> row = new TreeMap<Integer, String>((o1, o2) -> o1 - o2);
        datas.row(lastRowIndex).putAll(row);
        Map<Integer, String> col = new HashMap<>();
        datas.column(lastColIndex).putAll(col);
    }

    /***
     * 检查并更新表结构,要求
     * 3.根据当前时间加入新的行和列
     * @param service
     */
    private void checkAndBuildTable(LoginLogoutService service) {
        LocalDate yestoday = LocalDate.now().minusDays(1);
        if(yestoday.compareTo(gameOnlineDate) != lastColIndex + 1 || lastColIndex != lastRowIndex) {
            throw new AnalyeException("行列错误");
        }
       generOneDaysData(yestoday, service);
    }

    /***
     *   /***
     *      * 创建表结构,要求
     *      * 1.根据时间是否创建 @Link{BLACK_WORDS}.
     *      * 2.根据当前时间加入所有行和列
     *      * 3.检查表中的每一个行里是否已有数据,如果没有填""
     *
     * @param service
     */
    private void buildBlackTable(LoginLogoutService service) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        datas.row(0).put(0,BLACK_WORDS + gameOnlineDate.format(TimeUtil.YMD));
        if(yesterday.compareTo( gameOnlineDate ) <= 0) {
            sb.append("昨天日期不足以计算存留; ");
        }else {
            LocalDate startDay = gameOnlineDate.plusDays(1);
            while(startDay.compareTo(yesterday) <= 0) {
               generOneDaysData(startDay, service);
               startDay = startDay.plusDays(1);
            }
        }
    }

    public void generOneDaysData(LocalDate startDay, LoginLogoutService service){
        String cKey = startDay.format(TimeUtil.MD_C);
        String rKey = startDay.minusDays(1).format(TimeUtil.YMD_1);
        buildColRowAndFill(rKey,cKey);
        int rI = lastRowIndex;//1
        int cI = lastColIndex;//1
        if(rI != cI){
            throw new AnalyeException("行列出现错误");
        }
        sb.append("计算存留; [");
        for (int i = 1; i <= rI; i++) {
            String value = service.getRetentionByTwoMinusValuesIgloreServer(i,cI);
            sb.append(" " + i + " :" + rI + " :" + value  + " ;");
            datas.put(i,cI,value);
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
    }

    @Override
    public StringBuilder startupLoad(LoginLogoutService service) {
        buildTable(service);
        return sb;
    }


    @Override
    public StringBuilder fetchFromLogsAndCheckClear(LoginLogoutService service) {
        buildTable(service);
        return sb;
    }

    @Override
    public Map toGraphicJsonStr(String value) {
        return null;
    }

    @Override
    public ResultBean getJsonStrByPageSizeAndPageIndex(int pageSize, int pageIndex) {
        JsonTableBean json = new JsonTableBean(tableName);
        json.addHead(datas.row(0));
        List<Map<Integer, String>> list = new ArrayList<>();
        for (int i = 1; i <= lastColIndex; i++) {
            list.add(datas.row(i));
        }
        json.addContent(list);
        return new ResultBean(new PageObject(0, 0, 0, json));
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String printState() {
        return "lastRowIndex = " + lastRowIndex + "  ; lastColIndex = " + lastColIndex;
    }

    @Override
    public String getTableStr() {
        return TABLESTR;
    }

    @Override
    public void clearTable() {
        datas.clear();
    }

    public static void main(String[] args) {
//        LocalDate today = LocalDate.of(2019,12,31);
//        String format = today.format(DateTimeFormatter.ofPattern("M月d日"));
//        System.out.println(format);

        TreeBasedTable<Integer, Integer, String> trees =  TreeBasedTable.create((o1, o2) -> o1 - o2, (o1, o2) -> o1 - o2);
        TreeMap<Integer, String> tree = new TreeMap<>();
        tree.put(0,"123");
        trees.row(0).putAll(tree);
        System.out.println(trees);
        tree.put(1,"234");
        System.out.println(trees);
    }
}
