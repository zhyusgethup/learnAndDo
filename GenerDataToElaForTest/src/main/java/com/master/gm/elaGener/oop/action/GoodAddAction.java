package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.GoodAddLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class GoodAddAction extends Action {
    public GoodAddAction(Adjust adjust) {
        super(adjust);
        log = new GoodAddLog();
        name = "添加道具";
    }
    public class StaGoodAddAdjuster implements Adjust{
        private int cid;
        private int add;

        public StaGoodAddAdjuster(int cid, int add) {
            this.cid = cid;
            this.add = add;
        }

        @Override
        public void adjust(User user, Server server) {
            CommonActionUtil.loginCheckIsWrong(user,server);
            String acc = user.getAcc();
            Map<Integer, Integer> good = server.getUser(acc).getGood();
            if(good.containsKey(cid)){
                good.put(cid,good.get(cid) + add);
            }else{
                good.put(cid,add);
            }
            params.put("add", add);
            params.put("cid", cid);
        }
    }
    @Override
    public void gener(User user, Server server) {
        GoodAddLog log = (GoodAddLog)this.log;
        log.setAcc(user.getAcc());
        log.setAdd( (Integer)params.get("add"));
        log.setCid((Integer)params.get("cid"));
    }
}
