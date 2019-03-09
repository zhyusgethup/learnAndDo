package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.RoleRowLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class RoleRowAction extends Action {
    public RoleRowAction(Adjust adjust) {
        super(adjust);
        log = new RoleRowLog();
    }

    public  static class  RoleRowAdjuster implements Adjust{
        private int r_type;
        public RoleRowAdjuster(int r_type) {
            this.r_type = r_type;
        }

        @Override
        public void adjust(User user, Server server, Map<String, Object> params) {
            CommonActionUtil.loginCheckIsWrong(user,server);
            ActionParamType.ROLE_GROW.val(r_type);
            params.put("r_type",r_type);

        }
    }
    @Override
    public void gener(User user, Server server) {
        RoleRowLog log = (RoleRowLog)this.log;
        log.setR_type((Integer)params.get("r_type"));

    }
}
