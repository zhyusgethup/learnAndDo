package com.cellsgame.command;

import java.util.*;

public enum Cmd {
    //邮件模块
    Mail_List("邮件列表","ml",130006),

    //公会商城
    Shop_Open("公会商店列表","shopOpen",110001,"cityId",Integer.class),
    Shop_Manual_Refresh("手动刷新公会商店","shopOpen",110004,"cityId",Integer.class),
    Shop_Buy("公会商店购买","shopOpen",110002,"cityId",Integer.class,"itemIndex",Integer.class,"version",Integer.class),

    //公会模块
    Guild_Create("创建公会","cg",120001,"name",String.class),

    //竞技场
    ARENA_CHALLENGE("竞技场获取挑战名单","chlist",490002,"tid",Integer.class),

    //英雄
    Summon_In("进入召唤池","su",450001,"poolid",1),
    Hero_Summon("召唤英雄","su",450002,"cid",Integer.class,"idx",Integer.class),
    Summon_out("召唤英雄","su",450003),
    Hero_rank("英雄编队","ra",450008,"teamIndex",Integer.class,"hid",Integer.class,"heroIndex",Integer.class),
    //系统信息
    General_System_Time("系统时间","sysTime",10001),

    //玩家模块
    Test_Player_EnterGameWithName("测试玩家登陆","testEnter",20101,"serverId","20180802","accountId",String.class,"name",String.class),

    //战斗模块
    FightBegin("战斗开始","fb",460001,"typ",Integer.class,"tIdx",Integer.class,"val",Integer.class),//typ 1副本 2竞技场 3公会战
    FightAfter("战斗结算","fa",460002,"rst",Map.class,"fst",Integer.class,"turns",List.class);//
    ;
    public String toString() {
        return fullName + "[" + cmd + "]" + "  " + sign + " -> " + params;
    }


    public List<String> getPaL() {
        return paL;
    }

    public void setPaL(List<String> paL) {
        this.paL = paL;
    }

    private List<String> paL;
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }



    public static  Cmd getCmdByNumber(int number) {
        Cmd[] values = Cmd.values();
        for(Cmd cmd:values){
            if(cmd.cmd == number){
                return cmd;
            }
        }
        return General_System_Time;
    }

    public static  Cmd getCmdBySign(String sign){
        Cmd[] values = Cmd.values();
        for(Cmd cmd:values){
            if(cmd.sign.equals(sign)){
                return cmd;
            }
        }
        return General_System_Time;
    }

    private String fullName;
    private int cmd;
    private String sign;
    private Map<String, Object> params;
    private Cmd(String fullName,String name, int cmd, Object ... objs) {
        this.cmd = cmd;
        this.fullName = fullName;
        this.sign = name;
        Map<String, Object> map = new HashMap();
        paL = new ArrayList<>();
        for(int i = 0; i < objs.length; i+= 2) {
            if( ! (objs[i] instanceof  String)) {
                throw new RuntimeException("参数错误");
            }
            map.put((String)objs[i], objs[i + 1]);
            paL.add((String)objs[i]);
        }
        this.params = map;
    }
}
