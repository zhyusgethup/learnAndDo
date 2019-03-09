package com.master.analyze.analyeBean;

import lombok.Data;

@Data
public class GoodUserBean {
    private String acc;
    private double product;
    private double cost;
    private double curCount;
    private double hisCount;
    private String serverName;
    private int cid;

    public CGoodsBean replace(long start, long end){
        hisCount = product;
        curCount = product - cost;
        CGoodsBean cGoodsBean = new CGoodsBean();
        cGoodsBean.setAcc(acc);
        cGoodsBean.setCurCount((int)curCount);
        cGoodsBean.setHisCount((int)hisCount);
        cGoodsBean.setServer(serverName);
        cGoodsBean.setCid(cid);
        cGoodsBean.setStart(start);
        cGoodsBean.setEnd(end);
        return cGoodsBean;
    }

    @Override
    public String toString() {
        return "GoodUserBean{" +
                "acc='" + acc + '\'' +
                ", product=" + product +
                ", cost=" + cost +
                ", curCount=" + curCount +
                ", hisCount=" + hisCount +
                '}';
    }

    public GoodUserBean(String acc) {
        this.acc = acc;
    }
}
