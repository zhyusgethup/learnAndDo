package com.master.gm.elaGener.log.ela.bean;
//
public class GoodAddLog extends  GoodLog {
    private int add;

    public void setAdd(int add) {
        this.add = add;
    }

    @Override
    protected void generData() {
        jsonData.put("cid",cid);
        jsonData.put("add",add);
        jsonData.put("acc",acc);
    }
}
