package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

public interface Adjust {
    public void adjust(User user, Server server);

    public default void checkIsParamNull(User user, Server server){
        if(null == user || null == server)
            throw new ActionException("user 或 server 为null");
    }

    public default void adjustor(User user, Server server){
        checkIsParamNull(user,server);
        adjust(user,server);
    }
}
