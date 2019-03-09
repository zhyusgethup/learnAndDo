package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.GuildCountLog;
import com.master.gm.elaGener.log.ela.bean.GuildDonateLog;
import com.master.gm.elaGener.log.ela.bean.GuildFightLog;
import com.master.gm.elaGener.log.ela.bean.GuildLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class GuildFightAction extends Action{
    public GuildFightAction(Adjust adjust) {
        log = new GuildFightLog();
        name = "公会战斗";
    }

//    public  static class  StaGuildFightAdjustor implements Adjust{
//        private int gid;
//        private String gName;
//        public StaGuildFightAdjustor(int gid,String gName) {
//            this.gid = gid;
//            this.gName =gName;
//        }
//        @Override
//        public void adjust(User user, Server server, Map<String, Object> params) {
//            params.put("gName",gName);
//            params.put("gid",gid);
//        }
//    }
    @Override
    public void gener(User user, Server server) {
        CommonActionUtil.loginCheckIsWrong(user,server);
        GuildFightLog log = (GuildFightLog)this.log;
        log.setgId((Integer)params.get("gid"));
        log.setgName((String) params.get("gName"));
    }
}
