package com.master.gm.elaGener.log.ela.bean;
//
public class GuildLog extends BaseELog {
    protected int g_type;

    public GuildLog() {
        super("13");
    }

    @Override
    protected void generData() {
        jsonData.put("g_type",g_type);
    }
}
