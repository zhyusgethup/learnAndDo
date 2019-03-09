package com.master.analyze.table;

import com.master.analyze.http.TableNeed;
import com.master.analyze.analyeBean.TableEnum;
import com.master.analyze.cache.GameServerCache;
import com.master.cons.Cons;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserJoinTable extends MyTable {
    public UserJoinTable(TableEnum e) {
        super(e);
    }
    protected int varType = Cons.END_VAR_COL;

    @Override
    protected void generateDatasIfNull(Map<String, Object> cache) {
        List<Integer> insts = GameServerCache.getActivityCids();
        cache.put("act",insts);
    }

    public static void main(String[] args) {
        MyTable table = new UserJoinTable(TableEnum.USER_JOIN);
        GameServerCache.init();
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
    }
}
