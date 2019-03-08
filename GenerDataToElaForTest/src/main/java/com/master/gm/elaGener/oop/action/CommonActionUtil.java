package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

public class CommonActionUtil {
    public static void loginCheckIsWrong(User user, Server server){
        if(!server.getRegeterUser().contains(user.getAcc()))
            throw new ActionException("玩家" + user.getAcc() + "还没在服务器" + server.getServerId() + "注册");
        if(!server.getOnlineUser().contains(user.getAcc()))
            throw new ActionException("玩家" + user.getAcc() + "没登陆服务器" + server.getServerId());
    }
}
