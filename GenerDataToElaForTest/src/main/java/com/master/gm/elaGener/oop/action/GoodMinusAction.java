package com.master.gm.elaGener.oop.action;

import com.master.gm.elaGener.log.ela.bean.GoodAddLog;
import com.master.gm.elaGener.log.ela.bean.GoodLog;
import com.master.gm.elaGener.log.ela.bean.GoodMinusLog;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;

import java.util.Map;

public class GoodMinusAction extends Action {
    public GoodMinusAction(Adjust adjust) {
        super(adjust);
        log = new GoodMinusLog();
        name = "添加道具";
    }
    public class StaGoodMinusAdjuster implements Adjust{
        private int cid;
        private int minus;

        public StaGoodMinusAdjuster(int cid, int minus) {
            this.cid = cid;
            this.minus = minus;
        }

        @Override
        public void adjust(User user, Server server) {
            CommonActionUtil.loginCheckIsWrong(user,server);
            String acc = user.getAcc();
            Map<Integer, Integer> good = server.getUser(acc).getGood();
            if(!good.containsKey(cid))
                throw new ActionException("玩家" + acc + "没有道具" + cid);
            Integer num = good.get(cid);
            if(num < minus)
                throw new ActionException("玩家" + acc + "道具" + cid + "不足[" + num + ":" + minus + "]");
            good.put(cid,num - minus);
            ActionParamType.GOOD_MINUS.val(minus);
            ActionParamType.GOOD_CID.val(cid);
            params.put("minus", minus);
            params.put("cid", cid);
        }
    }
    @Override
    public void gener(User user, Server server) {
        CommonActionUtil.loginCheckIsWrong(user,server);
        GoodMinusLog log = (GoodMinusLog)this.log;
        log.setAcc(user.getAcc());
        log.setMinus( (Integer)params.get("minus"));
        log.setCid((Integer)params.get("cid"));
    }
}