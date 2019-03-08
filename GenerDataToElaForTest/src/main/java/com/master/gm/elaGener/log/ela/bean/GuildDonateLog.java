package com.master.gm.elaGener.log.ela.bean;

import org.apache.lucene.analysis.da.DanishAnalyzer;
//
public class GuildDonateLog extends GuildLog {
    private int num;

    public GuildDonateLog() {
        this.g_type = 4;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    protected void generData() {
        super.generData();
        jsonData.put("num",num);
    }
}
