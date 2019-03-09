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

public class UserPointTable extends MyTable{
    public UserPointTable(TableEnum e) {
        super(e);
    }
    protected int varType = Cons.END_VAR_COL;

    @Override
    protected void generateDatasIfNull(Map<String, Object> cache) {
        List<Integer> insts = GameServerCache.getInstanceCids();
        cache.put("insts",insts);
    }

    public static void main(String[] args) {
        MyTable table = new UserPointTable(TableEnum.USER_POINT);
        GameServerCache.init();
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
    }
}
