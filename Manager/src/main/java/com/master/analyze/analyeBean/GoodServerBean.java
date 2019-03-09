package com.master.analyze.analyeBean;

import lombok.Data;

import java.util.*;

@Data
public class GoodServerBean {
    private String serverName;
    private double product;
    private double cost;
    private double curCount;
    private double hisCount;
    private long start;
    private long end;
    private Map<Integer,GoodCidBean> map;

    public GoodServerBean(String serverName,long start, long end) {
        this.serverName = serverName;
        this.end = end;
        this.start = start;
        map = new HashMap<>();
    }

//    public List<CGoodsBean> replace(long start, long end){
//        List<CGoodsBean> list = new ArrayList<>();
//        for(GoodCidBean bean:map.values()) {
//            list.addAll(bean.replace(start, end));
//            curCount += bean.getCurCount();
//            hisCount += bean.getHisCount();
//        }
//        return list;
//    }
    
//    public void compute() {
//        for(GoodCidBean goodCidBean:map.values()) {
//            goodCidBean.compute();
//            curCount += goodCidBean.getCurCount();
//            hisCount += goodCidBean.getHisCount();
//        }
//    }

    public GoodCidBean getCidBean(int cid) {
        if(map.containsKey(cid))
            return map.get(cid);
        return null;
    }
    /***
     * 为了效率,请小单位算到大单位,这最后计算
     * @param cid
     */
    public void addCidBean(GoodCidBean cid) {
        map.put(cid.getCid(),cid);
    }

    @Override
    public String toString() {
        return "GoodServerBean{" +
                "serverName='" + serverName + '\'' +
                ", product=" + product +
                ", cost=" + cost +
                ", curCount=" + curCount +
                ", hisCount=" + hisCount +
                ", start=" + start +
                ", end=" + end +
                ", map=" + map +
                '}';
    }
}
