package com.master.gm.elaGener.log.ela.bean;
//
public class GuildMemeberLog extends GuildLog {
    private int gMemCount;

    public GuildMemeberLog() {
        g_type = 5;
    }

    public void setgMemCount(int gMemCount) {
        this.gMemCount = gMemCount;
    }

    @Override
    protected void generData() {
        super.generData();
        jsonData.put("gMemCount",gMemCount);
    }
}
