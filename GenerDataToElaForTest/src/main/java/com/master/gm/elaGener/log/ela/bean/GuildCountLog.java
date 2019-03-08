package com.master.gm.elaGener.log.ela.bean;
//
public class GuildCountLog extends GuildLog {
    private int gCount;

    public GuildCountLog() {
        this.g_type = 5;
    }

    public void setgCount(int gCount) {
        this.gCount = gCount;
    }

    @Override
    protected void generData() {
        super.generData();
        jsonData.put("gCount",gCount);
    }
}
