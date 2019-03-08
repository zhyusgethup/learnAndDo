package com.master.gm.elaGener.log.ela.bean;
//
public class GoodMinusLog extends GoodLog{
    private int minus;

    public void setMinus(int minus) {
        this.minus = minus;
    }

    @Override
    protected void generData() {
        jsonData.put("cid",cid);
        jsonData.put("minus",minus);
        jsonData.put("acc",acc);
    }
}
