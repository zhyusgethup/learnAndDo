package com.master.gm.elaGener.log.ela.bean;
//
public abstract class GoodLog extends BaseELog {
    protected int cid;
    protected String acc;

    public GoodLog( ) {
        super("17");
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }
}
