package com.master.analyze.analyeBean;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.master.util.MathUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


public class GoodCidBean {
    @Getter@Setter
    private int cid;
    private double product;
    private double cost;
    private double curCount;
    private double hisCount;
    @Getter@Setter
    private Map<String,GoodUserBean> map;
    private String server;
    private double ra;
    private double avg;
    private double agL;
    private double agM;
    private double agS;
    public static final String COST = "co";//消耗
    public static final String PRODUCT = "pr";//生产
    public static final String CUR = "cu";//生产
    public static final String HIS = "hi";//历史
    public static final String RA = "ra"; //比值
    public static final String AVG = "av";//平均
    public static final String AGL= "agL";//大值
    public static final String AGM = "agM";//中值
    public static final String AGS = "agS";//小值
    public static final int COST_INDEX = 0;//消耗
    public static final int PRODUCT_INDEX = 1;//生产
    public static final int CUR_INDEX = 2;//现存
    public static final int RA_INDEX = 3; //比值
    public static final int AVG_INDEX = 4;//平均
    public static final int AGL_INDEX= 5;//大值
    public static final int AGM_INDEX = 6;//中值
    public static final int AGS_INDEX = 7;//小值
    @Getter@Setter
    private Table<String,String,Double> topData; //Server,算法,数据
    @Getter@Setter
    private Table<String,String,GoodUserBean> bottomData;//server,acc,bean

    public GoodCidBean() {
        topData = HashBasedTable.create();
        bottomData = HashBasedTable.create();
    }
    public GoodCidBean(int cid) {
        this();
        this.cid = cid;
    }

    public void computeRa(){
        Map<String, Double> column = topData.column(RA);
        double total_pro =0;
        double total_cost = 0;
        for(String server:column.keySet()){
            Double pro = topData.get(server, PRODUCT);
            total_pro += pro;
            if(null != pro && pro != 0.0) {
                Double cost = topData.get(server, COST);
                column.put(server,cost/pro);
                total_cost += cost;
            }
        }
        if(total_pro != 0){
            column.put(Row.TOTAL_COUNT,total_cost/total_pro);
        }
    }

    public void totalServerCount(Map<String, Double> column) {
        double total=0;
       for(Double va:column.values()){
           total += va;
       }
       column.put(Row.TOTAL_COUNT,total);
    }
    public void computeAvg(){
        Map<String, Double> column = topData.column(AVG);
        double total_cur =0;
        double total_size = 0;
        for(String server:column.keySet()){
            double size = bottomData.row(server).size();
            total_size += size;
            if( size != 0.0) {
                Double cur = topData.get(server, CUR);
                column.put(server,cur/size);
                total_cur += cur;
            }
        }
        if(total_size!= 0){
            column.put(Row.TOTAL_COUNT,total_cur/total_size);
        }
    }
    public void generData(Map<String,String> data) {
        totalServerCount(topData.column(PRODUCT));
        totalServerCount(topData.column(COST));
        totalServerCount(topData.column(CUR));
        computeRa();
        computeAvg();
        computeAgg();
        Map<String, Map<String, Double>> maps = topData.rowMap();
        for(Map.Entry<String, Map<String, Double>> entryS: maps.entrySet()){
            String rowKey = entryS.getKey();
            for(Map.Entry<String, Double> entry:entryS.getValue().entrySet()){
                String colKey = entry.getKey();
                if(colKey.equals(HIS)) {
                    continue;
                }
                Double value = entry.getValue();
                if(null == value){
//                    data.put("d"+Row.SP + colKey + cid + Row.SP + rowKey,Row.NAN);
                }else {
                    data.put("d"+Row.SP + colKey + cid + Row.SP + rowKey,MathUtil.saveTwoDecimals(value));
                }
            }
        }
        //产出 pr
//        data.put("d"+Row.SP + "pr" + cid + Row.SP + server,String.valueOf(product));
//        data.put("d"+Row.SP + "co" + cid + Row.SP + server,String.valueOf(cost));
//        if(product == 0) {
//            data.put("d"+Row.SP + "ra" + cid + Row.SP + server,Row.NAN);
//        }else {
//            ra = (double)cost/(double)product;
//            data.put("d"+Row.SP + "ra" + cid + Row.SP + server,String.valueOf(ra));
//        }
//        data.put("d"+Row.SP + "cu" + cid + Row.SP + server,String.valueOf(curCount));
//
//        data.put("d"+Row.SP + "av" + cid + Row.SP + server,String.valueOf(avg));

//        String[] agg = getAgg();
//        data.put("d"+Row.SP + "agL" + cid + Row.SP + server,String.valueOf(agg[0]));
//        data.put("d"+Row.SP + "agM" + cid + Row.SP + server,String.valueOf(agg[1]));
//        data.put("d"+Row.SP + "agS" + cid + Row.SP + server,String.valueOf(agg[2]));
    }

    private void computeAgg(){
        List<GoodUserBean> allBeans = new ArrayList<>();
        for(String server:topData.rowKeySet()){
            List<GoodUserBean> list = new ArrayList<>(bottomData.row(server).values());
            allBeans.addAll(list);
            getAgg(list,server);
        }
        getAgg(allBeans,Row.TOTAL_COUNT);
    }
    public void getAgg(List<GoodUserBean> list, String server){
        int size = list.size();
        if(0 == size)
            return ;
        if(1 == size){
            topData.put(server,AGL,1.00);
            return;
        }
        Collections.sort(list,(o1,o2)->(int)(-o1.getCurCount() + o2.getCurCount()));
        double curCount = topData.get(server,CUR);
        if(2 == size){
            double v = list.get(0).getCurCount() / curCount;
            topData.put(server,AGL,v);
            topData.put(server,AGM,1.0 - v);
            return;
        }
        int m = size/25;
        int p1 = m > 0?m:1;
        m = size / 5;
        double t1  = 0;
        for (int i = 0; i < p1; i++) {
            t1 += list.get(i).getCurCount();
        }
        topData.put(server,AGL,t1/curCount);
        t1 = 0;
        int p2 = m > p1?m:(p1 + 1);
        for (int i = p1; i < p2; i++) {
            t1 += list.get(i).getCurCount();
        }
        topData.put(server,AGM,t1/curCount);
        t1 = 0;
        for (int i = p2; i < size; i++) {
            t1 += list.get(i).getCurCount();
        }
        topData.put(server,AGS,t1/curCount);
    }
    private void getAvg(){
        int size = map.size();
        if(0 == size)
            return;
        avg = curCount/size;
    }

    public void compute(){
//        for(GoodUserBean userBean:map.values()) {
//            curCount += userBean.getCurCount();
//            hisCount += userBean.getHisCount();
//        }
        Map<String, Map<String, GoodUserBean>> maps = bottomData.rowMap();
        Set<Map.Entry<String, Map<String, GoodUserBean>>> entries = maps.entrySet();
        for(Map.Entry<String, Map<String, GoodUserBean>> entry:entries) {
            String server = entry.getKey();
            Map<String, GoodUserBean> colMap = entry.getValue();
            double cur = 0.0;
            double his = 0.0;
            for(GoodUserBean bean:colMap.values()) {
                cur += bean.getCurCount();
                his += bean.getHisCount();
            }
            topData.put(server,HIS,his);
            topData.put(server,CUR,cur);
        }
    }

    public List<CGoodsBean> replace(long start, long end){
        List<CGoodsBean> list = new ArrayList<>();
        Map<String, Map<String, GoodUserBean>> maps = bottomData.rowMap();
        Set<Map.Entry<String, Map<String, GoodUserBean>>> entries = maps.entrySet();
        for(Map.Entry<String, Map<String, GoodUserBean>> entry:entries) {
            String server = entry.getKey();
            Map<String, GoodUserBean> colMap = entry.getValue();
            for(GoodUserBean bean:colMap.values()) {
                list.add(bean.replace(start, end));
            }
            topData.put(server,HIS,topData.get(server,PRODUCT));
            topData.put(server,CUR,topData.get(server,PRODUCT) - topData.get(server,COST));
        }
        return list;
    }

    public GoodUserBean getUserBean(String acc) {
        if(map.containsKey(acc))
            return map.get(acc);
        return null;
    }



    @Override
    public String toString() {
        return "GoodCidBean{" +
                "cid=" + cid +
                ", product=" + product +
                ", cost=" + cost +
                ", curCount=" + curCount +
                ", hisCount=" + hisCount +
                ", map=" + map +
                '}';
    }

    public void addUser(GoodUserBean bean) {
        map.put(bean.getAcc(),bean);
    }
}
