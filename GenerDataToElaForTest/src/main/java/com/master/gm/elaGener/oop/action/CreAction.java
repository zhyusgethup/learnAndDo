package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.CreateLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class CreAction<just> extends Action {


    public CreAction(Adjust adjust) {
        super(adjust);
        log = new CreateLog();
        name = "创建角色";
    }

    @Override
    public void gener(User user, Server server) {
        CreateLog log = (CreateLog)this.log;
        log.setServer(server.getServerId());
        log.setAccount_id(user.getAcc());
    }

    public static class StandCreAdjust implements Adjust{
        @Override
        public void adjust(User user, Server server, Map<String, Object> params) {
            if(server.getRegeterUser().contains(user.getAcc()))
                throw new ActionException("玩家" + user.getAcc() + "在服务器" + server.getServerId() + "已注册");
            server.getRegeterUser().add(user.getAcc());
            Server.UserVO vo = new Server.UserVO(user.getAcc());
            vo.setCre_role_time(System.currentTimeMillis());
            server.getUsers().put(user.getAcc(),vo);
        }
    }
    public static void main(String[] args) {
        CreAction cre = new CreAction(new StandCreAdjust());
        User acc = new User("acc01");
        Server server = new Server("2004");
        cre.action(acc, server);

        System.out.println(server);
        System.out.println(acc);
    }
}
