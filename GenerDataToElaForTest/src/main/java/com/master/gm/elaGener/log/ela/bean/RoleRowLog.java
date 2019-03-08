package com.master.gm.elaGener.log.ela.bean;

public class RoleRowLog  extends BaseELog{
    private int r_type;

    public RoleRowLog() {
        super("12");
    }

    public void setR_type(int r_type) {
        this.r_type = r_type;
    }

    @Override
    protected void generData() {
        jsonData.put("r_type",r_type);
    }
}
