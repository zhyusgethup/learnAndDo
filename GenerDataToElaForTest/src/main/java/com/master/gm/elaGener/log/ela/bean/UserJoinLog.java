package com.master.gm.elaGener.log.ela.bean;

import sun.security.action.PutAllAction;

public class UserJoinLog extends BaseELog{
    private int u_type;

    public UserJoinLog( ) {
        super( "11");
    }

    public void setU_type(int u_type) {
        this.u_type = u_type;
    }

    @Override
    protected void generData() {
        jsonData.put("u_type",u_type);
    }
}
