package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.GuildCountLog;
import com.master.gm.elaGener.log.ela.bean.GuildDonateLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

public class GuildDonateAction extends Action{
    public GuildDonateAction(Adjust adjust) {
        super(adjust);
        log = new GuildDonateLog();
        name = "公会捐献";
    }

    public class StaGuildDonateAdjustor implements Adjust{
        private int num;
        public StaGuildDonateAdjustor(int num) {
            this.num = num;
        }
        @Override
        public void adjust(User user, Server server) {
            CommonActionUtil.loginCheckIsWrong(user,server);
            params.put("num",num);
        }
    }
    @Override
    public void gener(User user, Server server) {
        GuildDonateLog log = (GuildDonateLog)this.log;
        log.setNum((Integer)params.get("num"));

    }
}
