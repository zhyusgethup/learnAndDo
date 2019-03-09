package com.master.analyze.table;

import com.master.analyze.analyeBean.TimeEnum;
import com.master.analyze.http.TableNeed;
import com.master.analyze.analyeBean.TableEnum;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class RententionTable extends  MyTable {
    public RententionTable(TableEnum e) {
        super(e);
    }

    protected void generateDatasIfNull(Map<String, Object> cache) {
        cache.put("oldTime",0L);
//        serversConf = genServerConf();
        long now = System.currentTimeMillis();
        //计算周活跃需要
        cache.put("weekStart_2", TimeEnum.WEEK_START_2.getTimeClock());
        //计算双周活跃需要
        cache.put("twoWeekStart_2",TimeEnum.TWO_WEEK_START_2.getTimeClock());
    }


    public static void main(String[] args) {
        MyTable table = new RententionTable(TableEnum.USER_RENTENTION);
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
    }
}