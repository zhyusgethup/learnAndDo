package com.master.analyze.table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.master.analyze.analyeBean.JsonTableBean;
import com.master.analyze.analyeBean.TableEnum;
import com.master.analyze.analyeBean.TimeEnum;
import com.master.cons.Cons;
import com.master.util.TimeUtil;

import java.util.Map;

public class GuildActTable extends MyTable{
    public GuildActTable(TableEnum e) {
        super(e);
    }

    protected void generateDatasIfNull(Map<String,Object> cache){
        long point_s = TimeEnum.POINT_S.getTimeClock();
        long point_e = TimeEnum.POINT_E.getTimeClock();
        cache.put("0point_s",point_s);
        cache.put("0point_e",point_e);
    }
    public static void main(String[] args) {
        MyTable table = new GuildActTable(TableEnum.GUILD_ACT_TABLE);
//        table.generateDatasIfNull();
        JsonTableBean jsonTableBean = table.toJsonStr();
  /*      System.out.println(jsonObject);
        System.out.println("--------");
        System.out.println(table.toGraphicJsonStr("2019-02-19"));*/
    }
}
