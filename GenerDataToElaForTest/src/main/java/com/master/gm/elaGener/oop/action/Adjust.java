package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.HashMap;
import java.util.Map;

public abstract class Adjust {
    Map<String,Object[]> datas = new HashMap<>();
    public abstract void adjust(User user, Server server);
    public abstract void base(User user, Server server);
    private Action action;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void checkIsParamNull(User user, Server server){
        if(null == user || null == server)
            throw new ActionException("user 或 server 为null");
    }

    public  void adjustor(User user, Server server){
        checkIsParamNull(user,server);
        adjust(user,server);
    }

}
