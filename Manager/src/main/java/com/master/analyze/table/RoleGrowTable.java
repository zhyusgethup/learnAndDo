package com.master.analyze.table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.master.analyze.analyeBean.TableEnum;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class RoleGrowTable extends MyTable {
    public RoleGrowTable(TableEnum e) {
        super(e);
    }

    public static void main(String[] args) {
        MyTable table = new RoleGrowTable(TableEnum.ROLE_GROW_UP);
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
//        System.out.println("--------");
//        System.out.println(table.toGraphicJsonStr("2019-02-19"));
    }
}
