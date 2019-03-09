package com.master.analyze.table;

import com.master.analyze.analyeBean.TableEnum;
import com.master.cons.Cons;

public class PlayerGuildPointTable extends MyTable {
    public PlayerGuildPointTable(TableEnum e) {
        super(e);
    }
    protected int varType = Cons.ONLY_VAR_COL;
    public static void main(String[] args) {
        MyTable table = new PlayerGuildPointTable(TableEnum.PLAYER_GUILD_POINT);
//        table.generateDatasIfNull();
//        String s = table.toJsonStr();
//        JSONObject jsonObject = JSON.parseObject(s);
//        System.out.println(jsonObject);
//        System.out.println("--------");
//        System.out.println(table.toGraphicJsonStr("2019-02-19"));
    }
}
