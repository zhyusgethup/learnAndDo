package com.master.gm.elaGener.log.ela.bean;
//

public class CreateLog extends BaseELog {
    private String account_id = "actId";

    public CreateLog() {
        super("1");
    }


    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    @Override
    protected void generData() {
        jsonData.put("actId",account_id);
    }
}
