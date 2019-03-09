package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.GuildCountLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class GuildCountAction extends Action{

    public GuildCountAction(Adjust adjust) {
        super(adjust);
        log = new GuildCountLog();
        name = "公会数量";
    }

    public   static class  StaGuildCountAdjustor implements Adjust{
        private int count;
        public StaGuildCountAdjustor(int count) {
            this.count = count;
        }
        @Override
        public void adjust(User user, Server server, Map<String, Object> params) {
            CommonActionUtil.loginCheckIsWrong(user,server);
            params.put("count",count);
        }
    }
    @Override
    public void gener(User user, Server server) {
        CommonActionUtil.loginCheckIsWrong(user,server);
        GuildCountLog log = (GuildCountLog)this.log;
        log.setgCount((Integer)params.get("count"));
    }
}
