package com.master.analyze.analyeBean;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.master.analyze.table.MyTable;
import com.master.cons.Cons;
import lombok.Getter;
import lombok.Setter;
import org.nutz.dao.Chain;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Setter
@Getter
/*****
 * 列数据
 */
public class Row {

    static Logger log = LoggerFactory.getLogger(Row.class);
    public static final String SP = "_";
    public static final String NAN = "_";

    public Row(String key, MyTable table, int index) {
        this.key = key;
        if (StringUtils.isEmpty(key))
            throw new RuntimeException("行初始化失败");
        if (null == table)
            throw new RuntimeException("行初始化失败");
        this.table = table;
        data = new TreeMap<Integer, String>((o1, o2) -> o1 - o2);
        this.index = index;
    }

    private MyTable table;
    private String key;
    private SortedMap<Integer, String> data;
    private int index;
    public static final String TOTAL_COUNT = "total";
    public static final String TOTAL_COUNT_WORD = "总计";
    public static final String KEYS = "keys";

    private void checkInit() {
        if (null == data) {
            throw new RuntimeException("行没初始化");
        }
        //默认table.getColumnEnums()和data长度相同
    }

    public int getIndexByColumnEnum(String cKey) {
        Integer index = table.getHead().get(cKey);
        if (null != index)
            return index;
        log.warn("该行没有对应的列 tableStr:{}, cKey: {}",table.getTableStr(),cKey);
        throw new RuntimeException("该行没有对应的列");
    }

    public void putData(String cKey, Map<String, String> dataMap) {
        checkInit();
        int i = getIndexByColumnEnum(cKey);
        if (null == dataMap) {
            data.put(i, " ");
        } else {
            data.put(i, dataMap.getOrDefault(key, " "));
        }
    }

    public void putData(String cKey, String value) {
        checkInit();
        int i = getIndexByColumnEnum(cKey);
        if (null == value) {
            data.put(i, "");
        } else {
            data.put(i, value);
        }
    }
    public void putDataFromMysql(String cKey, String value) {
        Integer i = table.getHead().get(cKey);
        if(null == i){
            int lastColIndex = table.getLastColIndex() + 1;
            table.getHead().put(cKey,lastColIndex);
            table.getDatas().row(0).put(lastColIndex,cKey);
            table.setLastColIndex(lastColIndex);
            i = lastColIndex;
            table.setLastColIndex(lastColIndex);
        }
        if (null == value) {
            data.put(i, "");
        } else {
            data.put(i, value);
        }

    }

    /***
     * 标准模式
     * @param rows
     * @return
     */
    public static Chain[] toChains(Row[] rows) {
        List<ColumnEnum> columnEnums = rows[0].getTable().getColumnEnums();
        Chain[] chains = new Chain[rows.length];
        for (int j = 0; j < rows.length; j++) {
            SortedMap<Integer, String> data = rows[j].getData();
            Chain chain = null;
            for (int i = 0; i < columnEnums.size(); i++) {
                if (columnEnums.get(i).getKey() == ColumnEnum.VAR_COL) {
                    Map<String, String> map = new HashMap<>();
                    SortedMap<Integer, String> row = rows[0].getTable().getDatas().row(0);
                    for (int k = i; k < row.size(); k++) {
                        map.put(row.get(k), data.getOrDefault(k, ""));
                    }
                    String json = Json.toJson(map);
                    chain.add(ColumnEnum.VAR_COL, json);
                } else {
                    if (0 == i) {
                        chain = Chain.make(columnEnums.get(i).getKey(), data.get(i));
                    } else {
                        chain.add(columnEnums.get(i).getKey(), data.get(i));
                    }
                }
            }
            chains[j] = chain;
        }
        return chains;
    }

    /**
     * Table<rowKey,ckey,value>
     *
     * @param rows
     * @param data
     */
    public static void putDataIntoByEachCol(Row[] rows, Table<String, String, String> data) {
        for (int i = 0; i < rows.length; i++) {
            Map<String, Integer> head = rows[i].getTable().getHead();
            String rowKey = rows[i].getKey();
            int rIndex = rows[i].getIndex();
            SortedMap<Integer, String> row = rows[i].getTable().getDatas().row(rIndex);
            for (Map.Entry<String, Integer> entry : head.entrySet()) {
                String ckey = entry.getKey();
                Integer index = entry.getValue();
                if (data.contains(rowKey, ckey)) {
                    row.put(index, data.get(rowKey, ckey));
                } else {
                    row.put(index, "");
                }
            }
//            datas.put(0,i,columnEnums.get(i).getWord());
//            head.put(columnEnums.get(i).getName(),i);
        }
    }

    public static void putDataIntoRows(String key, String cKey, String value, Row[] rows) {
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].getKey().equals(key)) {
                rows[i].putData(cKey, value);
            }
        }
    }

    public static void putDataIntoRows(String cKey, Row[] rows, String value) {
        for (int i = 0; i < rows.length; i++) {
            rows[i].putData(cKey, value);
        }
    }

    private static void addHeadAndFillData(Map<String, String> dataMap, Row[] rows) {
//        System.out.println(dataMap);
        MyTable table = rows[0].getTable();
        int varColIndex = table.getVarColIndex();
        if(-1 != varColIndex){
            table.getHead().remove(ColumnEnum.VAR_COL);
            table.getDatas().row(0).remove(varColIndex);
        }
        if (null == dataMap) {
            //移除head里面的var头信息
            switch (table.getVarType()) {
                case Cons.END_VAR_COL:
                    break;
                case Cons.ONLY_VAR_COL:
                    break;
            }
            return;
        }
        Set<Map.Entry<String, String>> entries = dataMap.entrySet();
        Map<String, String> head = new HashMap<>();
        Map<String, String> all = new HashMap<>();
        all.putAll(dataMap);
        for (Map.Entry<String, String> entry : entries) {
            String k = entry.getKey();
            if (k.indexOf("h") == 0) {
                head.put(k, entry.getValue());
                all.remove(k);
            }
        }
//        List<String> headKeys = rows[0].getTable().getHeadKeys();
        Map<String, Integer> tHead = rows[0].getTable().getHead();
        TreeBasedTable<Integer, Integer, String> datas = rows[0].getTable().getDatas();
        //TODO 后面转换成Entry 效率高点
        for (String k : head.keySet()) {
//            "h_p1_1" = "点1" h_rowKey_rIndex=word
            int l2 = k.lastIndexOf(SP);
            String key = k.substring(k.indexOf(SP) + 1, l2);
            int index = Integer.valueOf(k.substring(l2 + 1, k.length()));
//            SortedMap<Integer, String> row = datas.row(0);
           /* if (head.get(k).equals("290")) {
                System.out.println();
            }*/
            tHead.put(key, index);
            datas.row(0).put(index, head.get(k));
        }
        Table<String, String, String> da = HashBasedTable.create();
        for (String k : all.keySet()) {
            if (k.indexOf("d") != 0) {
                continue;
            }
            //"d_p1_server1 = value 数据头_ckey_rkey(服务器)=value
            int l2 = k.lastIndexOf(SP);
            String cKey = k.substring(k.indexOf(SP) + 1, l2);
            String key = k.substring(l2 + 1);
            da.put(key, cKey, all.get(k));
//            putDataIntoRows(key,cKey,all.get(k),rows);
        }
        putDataIntoByEachCol(rows, da);
    }

    public static void  putDataIntoRows(String cKey, Row[] rows, Map<String, String> dataMap) {
        if (cKey == ColumnEnum.VAR_COL) {
            addHeadAndFillData(dataMap, rows);
        } else {
            for (int i = 0; i < rows.length; i++) {
//           log.info("rows {} ; col {} ; dataMap {}", rows, e, dataMap);
                rows[i].putData(cKey, dataMap);
            }
        }
    }

    @Override
    public String toString() {
        return "Row{" + key + "," + index + "," + data + "}";
    }
}
