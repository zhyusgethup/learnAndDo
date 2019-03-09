package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.LoginAndOutLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class LoginOutAction extends LoginAction {
    public LoginOutAction(Adjust adjust) {
        super(adjust);
        name = "退出游戏";
    }

    @Override
    public void gener(User user, Server server) {
        super.gener(user,server);
        LoginAndOutLog log = (LoginAndOutLog)this.log;
        log.setType(0);
    }

//    public static class StaLoginoutAdjuster implements Adjust{
//        @Override
//        public void adjust(User user, Server server, Map<String, Object> params) {
//            CommonActionUtil.loginCheckIsWrong(user,server);
//            String acc = user.getAcc();
//            server.getUser(acc).setLogoutTime(System.currentTimeMillis());
//            server.getOnlineUser().remove(acc);
//        }
//    }
}
