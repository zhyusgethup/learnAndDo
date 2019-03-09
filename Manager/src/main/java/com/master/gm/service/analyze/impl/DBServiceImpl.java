package com.master.gm.service.analyze.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.TreeBasedTable;
import com.master.analyze.analyeBean.AnalyeException;
import com.master.analyze.analyeBean.ColumnEnum;
import com.master.analyze.analyeBean.Row;
import com.master.analyze.table.MyTable;
import com.master.analyze.table.StayTable;
import com.master.gm.BaseService;
import com.master.gm.service.analyze.DBService;
import com.master.util.TimeUtil;
import com.sun.xml.internal.bind.v2.TODO;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.json.Json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean
public class DBServiceImpl extends BaseService implements DBService {
    //    private static DBService instance;
    public DBServiceImpl() {
//        dao = MyIoc.dao;
//        instance =this;
        System.out.println("o");
    }
//    public static DBService getInstance(){
//        if(null == instance){
//            instance = new DBServiceImpl();
//        }
//        return instance;
//    }


    public boolean hasRecordInMysqlNow(String tableName, String timeStr) {
        Cnd cnd = Cnd.where("time", "=", timeStr);
        int count = dao.func(tableName, "count", "1", cnd);
        return count > 0;
    }




    @Override
    public void saveStay(StayTable table) {
        int lastColIndex = table.getLastColIndex();
        int lastRowIndex = table.getLastRowIndex();
        if(lastColIndex != lastRowIndex)
            throw new AnalyeException("行列错误");
        Chain chain = Chain.make("time",table.getDatas().get(0,lastColIndex));
        Map<Integer, String> column = table.getDatas().column(0);
        Map<Integer, String> datas = table.getDatas().column(lastColIndex);
        Map<Integer, String> map = new HashMap<>();
         for (int i = 1; i <= lastRowIndex; i++) {
            map.put(i, datas.get(i));
        }
        String json = Json.toJson(map);
        chain.add(ColumnEnum.VAR_COL, json);
        dao.insert(StayTable.TABLESTR,chain);
    }

    @Override
    public void getStay(StayTable table) {
        List<Record> query = dao.query(StayTable.TABLESTR, Cnd.NEW());
        TreeBasedTable<Integer, Integer, String> datas = table.getDatas();
        int lastColIndex = table.getLastColIndex();
        int lastRowIndex = table.getLastRowIndex();
        for (int i = 0; i < query.size(); i++) {
            Record record = query.get(i);
            String time = record.getString(ColumnEnum.TIME.getKey());
            LocalDate thatDay = LocalDate.parse(time,TimeUtil.MD_C);
            int minus = thatDay.compareTo(table.getGameOnlineDate());
            datas.put(0,minus,time);
            if(minus <= 0){
                throw new AnalyeException("数据库数据和游戏上线时间不匹配");
            }
            Map<Integer, String> map = JSONObject.parseObject(record.getString(ColumnEnum.VAR_COL), Map.class);
            for (int j = 1; j <= minus; j++) {
                datas.put(j,minus,map.get(j));
                if(null == datas.column(0).get(j)) {
//                    datas.put()
                }
            }
        }
    }

    public static void main(String[] args) {
        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
//        DBServiceImpl dbService = new DBServiceImpl();
//        dbService.dao = ioc.get(Dao.class);
//        boolean com = dbService.hasRecordInMysqlNow("", "2019-02-27 15");
//        System.out.println(com);
//        MyTable table = new PlayerGuildPointTable(TableEnum.PLAYER_GUILD_POINT);
//        table.startupLoad(ioc.get(LoginLogoutServiceImpl.class));
        Dao dao = ioc.get(Dao.class);
        String json = "{\n" +
                "   \"190\": \"\",\n" +
                "   \"290\": \"\",\n" +
                "   \"170\": \"\",\n" +
                "   \"270\": \"\",\n" +
                "   \"150\": \"\",\n" +
                "   \"250\": \"\",\n" +
                "   \"130\": \"\",\n" +
                "   \"230\": \"\",\n" +
                "   \"110\": \"\",\n" +
                "   \"210\": \"\",\n" +
                "   \"90\": \"\",\n" +
                "   \"70\": \"\",\n" +
                "   \"50\": \"\",\n" +
                "   \"30\": \"\",\n" +
                "   \"10\": \"\",\n" +
                "   \"180\": \"\",\n" +
                "   \"280\": \"\",\n" +
                "   \"160\": \"\",\n" +
                "   \"260\": \"\",\n" +
                "   \"140\": \"\",\n" +
                "   \"240\": \"\",\n" +
                "   \"120\": \"\",\n" +
                "   \"220\": \"\",\n" +
                "   \"100\": \"\",\n" +
                "   \"200\": \"\",\n" +
                "   \"80\": \"\",\n" +
                "   \"60\": \"\",\n" +
                "   \"40\": \"\",\n" +
                "   \"20\": \"\"\n" +
                "}";
        Chain add = Chain.make("time", "2019-01-23").add("server", "ljfServer").add("VAR", json);
        dao.insert("plaguild", add);
        ioc.depose();
    }

    @Override
    public void save(String tableName, Row[] rows, StringBuilder msg) {
        if (null == rows)
            return;
        Chain[] chains = Row.toChains(rows);
        for (int i = 0; i < chains.length; i++) {
            dao.insert(tableName, chains[i]);
        }
        msg.append("存数据入mysql:" + chains.length + ";");
    }

    @Override
    public int query(MyTable table, String start, String end, StringBuilder msg) {
        String tableName = table.getTableStr();
        Cnd cnd = Cnd.where("time", ">=", start).where("time", "<=", end);
        List<Record> query = dao.query(tableName, cnd);
        Row[] rows = new Row[query.size()];
        for (int i = 0; i < query.size(); i++) {
            Record re = query.get(i);
            String server = re.getString(ColumnEnum.SERVER.getKey());
            Row row = table.getRowByRKey(server);
            rows[i] = row;
            List<ColumnEnum> columnEnums = table.getColumnEnums();
            for (int j = 0; j < columnEnums.size(); j++) {
                ColumnEnum columnEnum = columnEnums.get(j);
                String cKey = columnEnum.getKey();
                if (cKey == ColumnEnum.VAR_COL) {
                    if(table.getHead().containsKey(cKey)){
                        Integer index = table.getHead().remove(cKey);
                        table.getDatas().row(0).remove(index);
                        table.setLastColIndex(table.getLastRowIndex() - 1);
                    }
                    if (null != re.getString(cKey)) {
                        Map<String, String> map = JSONObject.parseObject(re.getString(cKey), Map.class);
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            row.putDataFromMysql(entry.getKey(), entry.getValue());
                        }
                    }

                } else {
                    if (cKey == ColumnEnum.TIME.getKey()) {
                        String mysqlValue = re.getString(cKey);
                        row.putData(cKey, table.fixTimeStrFromMysql(mysqlValue));
                    } else {
                        row.putData(cKey, re.getString(cKey));
                    }
                }
            }
        }
        table.fillRowsIntoData(rows);
        return query.size();
    }

}
