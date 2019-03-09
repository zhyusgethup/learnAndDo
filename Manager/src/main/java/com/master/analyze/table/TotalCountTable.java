package com.master.analyze.table;

import com.master.analyze.http.TableNeed;
import com.master.analyze.analyeBean.TableEnum;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class TotalCountTable extends MyTable {
    public TotalCountTable(TableEnum e) {
        super(e);
    }

    @Override
    protected void generateDatasIfNull(Map<String, Object> cache) {
        cache.put("oldTime",0L);
    }

    public static void main(String[] args) {
        MyTable table = new TotalCountTable(TableEnum.TOTAL_COUNT);
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
    }
}
