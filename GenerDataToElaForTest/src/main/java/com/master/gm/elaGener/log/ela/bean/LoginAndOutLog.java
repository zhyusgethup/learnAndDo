package com.master.gm.elaGener.log.ela.bean;
//
public class LoginAndOutLog extends BaseELog {

    private String acc;
    private int point;
    private long role_create;
    private int inGuild;
    private long ser_create;
    private long lgTime;
    private int type;
    private long lgoutTime;
    private int nearInst;

    public LoginAndOutLog() {
       super("2");
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setRole_create(long role_create) {
        this.role_create = role_create;
    }

    public void setInGuild(int inGuild) {
        this.inGuild = inGuild;
    }

    public void setSer_create(long ser_create) {
        this.ser_create = ser_create;
    }

    public void setLgTime(long lgTime) {
        this.lgTime = lgTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLgoutTime(long lgoutTime) {
        this.lgoutTime = lgoutTime;
    }


    public void setNearInst(int nearInst) {
        this.nearInst = nearInst;
    }

    @Override
    protected void generData() {
        jsonData.put("acc",acc);
        jsonData.put("point",point);
        jsonData.put("role_create",role_create);
        jsonData.put("inGuild",inGuild);
        jsonData.put("ser_create",ser_create);
        jsonData.put("type",type);
        jsonData.put("nearInst",nearInst);
        if(type == 1) {
            jsonData.put("lgTime",lgTime);
        }else{
            jsonData.put("lgoutTime",lgoutTime);
            jsonData.put("onTime",lgoutTime - lgTime);
        }
    }
}
