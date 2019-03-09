package com.master.analyze.table;

import com.master.analyze.http.TableNeed;
import com.master.analyze.analyeBean.TableEnum;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class OnlineTimeTable extends MyTable{
    public OnlineTimeTable(TableEnum e) {
        super(e);
    }

    public static void main(String[] args) {
        MyTable table = new OnlineTimeTable(TableEnum.ON_LINE_TIME_PLAYER_COUNT);
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
    }
}