package com.master.analyze.analyeBean;


import com.master.analyze.table.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/***
 * 这个类用于注册类的行和列, 用于方便获取 显示表头,和表明,
 * 它用于实例化Table类
 */
public enum TableEnum {
    COMPREHENSIVE("综合数据" ,"com", ComprehensiveTable.class, ColumnEnum.TIME, ColumnEnum.SERVER, ColumnEnum.NEW_REGESTER_USER, ColumnEnum.TOTAL_REGESTER_USER
            ,ColumnEnum.DAY_ACTIVITY_USER, ColumnEnum.WEEK_ACTIVITY_USER, ColumnEnum.MONTH_ACTIVITY_USER, ColumnEnum.AVG_PLAYER_ONLINE_TIME
//            ,ColumnEnum.PAY_USER_COUNT, ColumnEnum.PAY_AMOUNT, ColumnEnum.ARPPU, ColumnEnum.ARPU, ColumnEnum.PAY_PERMEATE, ColumnEnum.LTV
    ),
    TOTAL_COUNT("总计数据","tot", TotalCountTable.class,ColumnEnum.TIME, ColumnEnum.SERVER,  ColumnEnum.TOTAL_REGESTER_USER , ColumnEnum.AVG_PLAYER_ONLINE_TIME
//            ,ColumnEnum.PAY_USER_COUNT, ColumnEnum.PAY_AMOUNT, ColumnEnum.ARPPU, ColumnEnum.PAY_PERMEATE, ColumnEnum.LTV
            ),

    USER_RENTENTION("用户留存","usren", RententionTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.TOTAL_REGESTER_USER,ColumnEnum.NEW_REGESTER_USER
            ,ColumnEnum.RetentionNextDay,ColumnEnum.Retention2Day,ColumnEnum.Retention3Day,ColumnEnum.Retention5Day
            ,ColumnEnum.Retention7Day,ColumnEnum.Retention14Day,ColumnEnum.Retention30Day,ColumnEnum.WEEK_ACTIVITY_USER
            ,ColumnEnum.TWO_WEEK_ACTIVITY_USER
    ),
//    TEST("test", ColumnEnum.C1, ColumnEnum.C2, ColumnEnum.C3, ColumnEnum.C4, ColumnEnum.C5),

    ON_LINE_TIME_PLAYER_COUNT("在线时长","online", OnlineTimeTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.NEW_REGESTER_USER,ColumnEnum.MINUTE1,
            ColumnEnum.MINUTE5,ColumnEnum.MINUTE10,ColumnEnum.MINUTE15,ColumnEnum.MINUTE20,ColumnEnum.MINUTE30,
            ColumnEnum.MINUTE60,ColumnEnum.MINUTE90,ColumnEnum.MINUTE120,ColumnEnum.HOUR3,ColumnEnum.HOUR4,ColumnEnum.HOUR8,ColumnEnum.HOUR12),

    PLAYER_GUILD_POINT("新手引导流失","plaGuild",PlayerGuildPointTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.NEW_PLAYER_GUILD_POINT),

    USER_POINT("用户流失","useLos",UserPointTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.RetentionNextDay,
            ColumnEnum.Retention2Day,ColumnEnum.Retention3Day,ColumnEnum.Retention5Day,ColumnEnum.Retention7Day,
            ColumnEnum.Retention14Day,ColumnEnum.Retention30Day,ColumnEnum.PLAYER_INSTANCE_POINT),

    USER_JOIN("用户参与","useJoi",UserJoinTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.POLIT_INST,ColumnEnum.ANEC_INST,ColumnEnum.PRAC_INST,ColumnEnum.ARENA_JOIN,ColumnEnum.ACTIVITY_JOIN),

    ROLE_GROW_UP("角色养成","rolG",RoleGrowTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.LEVEL_UP,ColumnEnum.WAKE_UP,ColumnEnum.BREAK,ColumnEnum.SKILL_UP,ColumnEnum.SKILL_HI,
            ColumnEnum.SUMMON,ColumnEnum.BLESS,ColumnEnum.S_MAKE,ColumnEnum.S_WAKE),

    GUILD_ACT_TABLE("公会活跃","guiA",GuildActTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.GUILD_COUNT,
            ColumnEnum.GUILD_ADD,ColumnEnum.GUILD_FIGHT,ColumnEnum.GUILD_DONATE,ColumnEnum.GUILD_BUILD,ColumnEnum.GUILD_ACT_PLAYER_RATIO),

    GOOD_AGG_TABLE("道具消耗","gooC",GoodsTable.class,ColumnEnum.TIME,ColumnEnum.SERVER,ColumnEnum.GOODS_COUNT),

    RENTENTION("99留存表",StayTable.TABLESTR,StayTable.class),

    ;
    private String tableName;
    @Getter
    private List<ColumnEnum> columnEnums;

    @Getter
    private Class<? extends AbstractTable> clasz;
    @Getter
    private String tableStr;

    TableEnum(String tableName,String tableStr,Class<? extends AbstractTable> clasz,ColumnEnum... columnEnums) {
        this.columnEnums = new ArrayList<>();
        for (int i = 0; i < columnEnums.length; i++) {
            this.columnEnums.add(columnEnums[i]);
        }
        this.tableName = tableName;
        this.tableStr = tableStr;
        this.clasz = clasz;
    }

    public int getIndex(ColumnEnum e) {
        return columnEnums.indexOf(e);
    }

    public String getTableName() {
        return tableName;
    }

}
