package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.GuildFightLog;
import com.master.gm.elaGener.log.ela.bean.GuildMemeberLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class GuildMemberAction extends Action{
//gMemCount
    public GuildMemberAction(Adjust adjust) {
        log = new GuildMemeberLog();
        name = "公会成员";
    }
//    public  static class  StaGuildMemberAdjustor implements Adjust{
//        private int gMemCount;
//        public StaGuildMemberAdjustor(int gMemCount) {
//            this.gMemCount = gMemCount;
//        }
//        @Override
//        public void adjust(User user, Server server, Map<String, Object> params) {
//            CommonActionUtil.loginCheckIsWrong(user,server);
//            ActionParamType.GUILD_G_M_COUNT.val(gMemCount);
//            params.put("gMemCount",gMemCount);
//
//        }
//    }

    @Override
    public void gener(User user, Server server) {
        GuildMemeberLog log = (GuildMemeberLog)this.log;
        log.setgMemCount((Integer)params.get("gMemCount"));

    }
}
