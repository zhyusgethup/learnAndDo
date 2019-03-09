package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.UserJoinLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class UserJoinAction extends Action {
    public UserJoinAction(Adjust adjust) {
        super(adjust);
        log = new UserJoinLog();
    }

    public  static class  UserJoinAdjuster implements Adjust{
        private int u_type;
        public UserJoinAdjuster(int u_type) {
            this.u_type = u_type;
        }

        @Override
        public void adjust(User user, Server server, Map<String, Object> params) {
            CommonActionUtil.loginCheckIsWrong(user,server);
            ActionParamType.U_TYPE.val(u_type);
            params.put("u_type",u_type);
        }
    }
    @Override
    public void gener(User user, Server server) {
        UserJoinLog log = (UserJoinLog)this.log;
        log.setU_type((Integer)params.get("u_type"));

    }
}
