package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.CreateLog;
import com.master.gm.elaGener.log.ela.bean.LoginAndOutLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

public class LoginAction extends Action {
    public LoginAction(Adjust adjust) {
        super(adjust);
        log = new LoginAndOutLog();
        name = "登陆游戏";
    }



    public static class StaLoginAdjuster implements Adjust{
        @Override
        public void adjust(User user, Server server) {
            if(!server.getRegeterUser().contains(user.getAcc()))
                throw new ActionException("玩家" + user.getAcc() + "还没在服务器" + server.getServerId() + "注册");
            if(server.getOnlineUser().contains(user.getAcc()))
                throw new ActionException("玩家" + user.getAcc() + "在服务器" + server.getServerId() + "已登陆");

            String acc = user.getAcc();
            server.getUser(acc).setLoginTime(System.currentTimeMillis());
            server.getOnlineUser().add(acc);
        }
    }

    @Override
    public void gener(User user, Server server) {
        LoginAndOutLog log = (LoginAndOutLog)this.log;
        log.setServer(server.getServerId());
        log.setAcc(user.getAcc());
        log.setInGuild(server.getUsers().get(user.getAcc()).getGuildId() == 0?0:1);
        log.setLgTime(server.getUsers().get(user.getAcc()).getLoginTime());
        log.setNearInst(server.getUsers().get(user.getAcc()).getNearInst());
        log.setLgoutTime(server.getUsers().get(user.getAcc()).getLogoutTime());
        log.setPoint(server.getUser(user.getAcc()).getPoint());
        log.setRole_create(server.getUser(user.getAcc()).getCre_role_time());
        log.setSer_create(server.getSer_cre_time());
        log.setType(1);
    }

    public static void main(String[] args) {

    }
}
