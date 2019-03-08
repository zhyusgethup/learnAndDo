package com.master.gm.elaGener.log.ela.bean;
//
public class GuildFightLog extends GuildLog {
    private int gId;
    private String gName;

    public void generData(){
        jsonData.put("g_type",3);
        jsonData.put("gId",gId);
        jsonData.put("gName",gName);
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }
}
