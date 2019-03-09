package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public interface Adjust {
    public void adjust(User user, Server server, Map<String, Object> params);

    public default void checkIsParamNull(User user, Server server){
        if(null == user || null == server)
            throw new ActionException("user 或 server 为null");
    }

    public default void adjustor(User user, Server server,Map<String, Object> params){
        checkIsParamNull(user,server);
        adjust(user,server, params);
    }
}
