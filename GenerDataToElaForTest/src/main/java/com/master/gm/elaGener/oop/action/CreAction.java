package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.CreateLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class CreAction extends Action {


    Adjust adjust;
    public CreAction() {
        log = new CreateLog();
        name = "创建角色";
    }

    @Override
    public void gener(User user, Server server) {
        CreateLog log = (CreateLog)this.log;
        log.setServer(server.getServerId());
        log.setAccount_id(user.getAcc());
    }

    public static class StandCreAdjust extends Adjust{
        public long getCre_role_time() {
            return cre_role_time;
        }

        public void setCre_role_time(long cre_role_time) {
            this.cre_role_time = cre_role_time;
        }

        private long cre_role_time;
        public void base(User user, Server server){
            if(server.getRegeterUser().contains(user.getAcc()))
                throw new ActionException("玩家" + user.getAcc() + "在服务器" + server.getServerId() + "已注册");
            server.getRegeterUser().add(user.getAcc());
            Server.UserVO vo = new Server.UserVO(user.getAcc());
            server.getUsers().put(user.getAcc(),vo);
        }


        @Override
        public void adjust(User user, Server server) {
            Server.UserVO vo = new Server.UserVO(user.getAcc());
            vo.setCre_role_time(cre_role_time);
        }
    }
    public static void main(String[] args) {
        CreAction cre = new CreAction();
        User acc = new User("acc01");
        Server server = new Server("2004");
        cre.action(acc, server);

        System.out.println(server);
        System.out.println(acc);
    }
}
