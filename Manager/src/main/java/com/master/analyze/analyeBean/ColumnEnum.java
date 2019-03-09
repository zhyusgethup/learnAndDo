package com.master.analyze.analyeBean;

import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum ColumnEnum {
    //综合数据表
    TIME("time", "时间", (cache, rows, service) -> {
        String timeStr = cache.get("timeStr").toString();
        Map<String, String> data = new HashMap<>();
        Map<String, String> keys = (Map<String, String>) cache.get("keys");
        Set<String> strings = keys.keySet();
        for (String key : strings) {
            data.put(key, timeStr);
        }
        return data;
    }),
    SERVER("server", "服务器", (cache, rows, service) -> {
        Map<String, String> data = new HashMap<>();
        Map<String, String> keys = (Map<String, String>) cache.get("keys");
        Set<String> strings = keys.keySet();
        for (String key : strings) {
            data.put(key, keys.get(key));
        }
        return data;
    }),
    TOTAL_REGESTER_USER("total_regester_user", "总注册用户", (cache, rows, service) -> {
        return service.getNewRegesterAccountByTime((long) cache.
                get("oldTime"), (long) cache.get("endClock"));
    }),
    NEW_REGESTER_USER("new_regester_user", "新增注册", (cache, rows, service) -> {
        return service.getNewRegesterAccountByTime((long) cache.
                get("beforeClock"), (long) cache.get("endClock"));
    }),
    DAY_ACTIVITY_USER("day_activity_user", "日活跃用户", (cache, rows, service) -> {
        return service.getActiveAccountCountByTimeAndServer(
                (long) cache.get("dayStart_2"), (long) cache.get("endClock"), 1, false);
    }),
    WEEK_ACTIVITY_USER("week_activity_user", "周活跃用户", (cache, rows, service) -> {
        return service.getActiveAccountCountByTimeAndServer(
                (long) cache.get("weekStart_2"), (long) cache.get("endClock"), 1, false);
    }),
    MONTH_ACTIVITY_USER("month_activity_user", "月活跃用户", (cache, rows, service) -> {
        return service.getActiveAccountCountByTimeAndServer(
                (long) cache.get("monthStart_2"), (long) cache.get("endClock"), 1, false);
    }),
    AVG_PLAYER_ONLINE_TIME("avg_player_online_time", "人均在线时长(分)", (cache, rows, service) -> {
        return service.getAvgOnlineTimeByHourDate((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), cache);
    }),
//    PAY_USER_COUNT("pay_user_count","充值用户",(cache,rows,service) -> {
//        TableNeed.getCountByTime(TableNeed.PAY_ACCOUNT_COUNT_URL,(long)cache.get("beforeClock"), (long)cache.get("endClock"));
//    } ),
//    PAY_AMOUNT("pay_amount","充值金额",(cache,rows,service) -> {
//        TableNeed.getCountByTime(TableNeed.PAY_AMOUNT_URL,(long)cache.get("beforeClock"),(long)cache.get("endClock"));
//    } ),
//    ARPPU("arppu","ARPPU",(cache,rows,service) -> {
//        String.valueOf(TableNeed.getCountByTime(TableNeed.PAY_ARPPU_URL, (long)cache.get("monthStart_1"), (long)cache.get("monthEnd_1")));
//    } ),
//    ARPU("arpu","ARPU",(cache,rows,service) -> {
//        String.valueOf(TableNeed.getDoubleCountByTime(TableNeed.PAY_AMOUNT_URL, (long)cache.get("monthStart_1"), (long)cache.get("monthEnd_1")));
//    } ),
//    PAY_PERMEATE("pay_permeate","付费渗透",(cache,rows,service) -> {
//
//    } ),
//    LTV("ltv","LTV",(cache,rows,service) -> {
//
//    } ),


//    //表总计数据

    //  用户留存
    RetentionNextDay("retentionNextDay", "次日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 1);
    }),
    Retention2Day("retention2Day", "2日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 2);
    }),
    Retention3Day("retention3Day", "3日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 3);
    }),
    Retention5Day("retention5Day", "5日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 5);
    }),
    Retention7Day("retention7Day", "7日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 7);
    }),
    Retention14Day("retention14Day", "14日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 14);
    }),
    Retention30Day("retention30Day", "30日留存", (cache, rows, service) -> {
        return service.getRetentionByTwoMinusValues(0, 30);
    }),
    TWO_WEEK_ACTIVITY_USER("two_week_activity_user", "双周活跃", (cache, rows, service) -> {
        return service.getActiveAccountCountByTimeAndServer(
                (long) cache.get("twoWeekStart_2"), (long) cache.get("endClock"), 1, false);
    }),


    //在线时长
    MINUTE1("minute1", "1分钟以下", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), 0L, TimeUtil.MINUTE_MIL);
    }),
    MINUTE5("minute5", "5分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL, TimeUtil.MINUTE_MIL * 5);
    }),
    MINUTE10("minute10", "10分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 5, TimeUtil.MINUTE_MIL * 10);
    }),
    MINUTE15("minute15", "15分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 10, TimeUtil.MINUTE_MIL * 15);
    }),
    MINUTE20("minute20", "20分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 15, TimeUtil.MINUTE_MIL * 20);
    }),
    MINUTE30("minute30", "30分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 20, TimeUtil.MINUTE_MIL * 30);
    }),
    MINUTE60("minute60", "60分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 30, TimeUtil.MINUTE_MIL * 60);
    }),
    MINUTE90("minute90", "90分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 60, TimeUtil.MINUTE_MIL * 90);
    }),
    MINUTE120("minute120", "120分钟", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 90, TimeUtil.MINUTE_MIL * 120);
    }),
    HOUR3("hour3", "3小时", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.MINUTE_MIL * 120, TimeUtil.HOUR_MIL * 3);
    }),
    HOUR4("hour4", "4小时", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.HOUR_MIL * 3, TimeUtil.HOUR_MIL * 4);
    }),
    HOUR8("hour8", "8小时", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.HOUR_MIL * 4, TimeUtil.HOUR_MIL * 8);
    }),
    HOUR12("hour12", "12小时", (cache, rows, service) -> {
        return
                service.getOnlinePlayerCountByTimeTime((long) cache.
                        get("beforeClock"), (long) cache.get("endClock"), TimeUtil.HOUR_MIL * 8, TimeUtil.HOUR_MIL * 12);
    }),

    //新手引导
    NEW_PLAYER_GUILD_POINT(ColumnEnum.VAR_COL, "新手引导流失可变列", (cache, rows, service) -> {
        return service.getNewPlayerBreakPointInfo((long) cache.
                get("beforeClock"), (long) cache.get("endClock"));
    }),

    //用户流失
    PLAYER_INSTANCE_POINT(ColumnEnum.VAR_COL, "用户流失可变列", (cache, rows, service) -> {
        return service.getInstBreakPointInfo((List<Integer>) cache.get("insts"), (long) cache.
                get("beforeClock"), (long) cache.get("endClock"));
    }),

    //用户参与
    POLIT_INST("polit_inst", "剧情副本", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 11, "u_type", 1);
    }),
    ANEC_INST("ance_inst", "外传副本", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 11, "u_type", 2);
    }),
    PRAC_INST("parc_inst", "修炼塔副本", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 11, "u_type", 3);
    }),
    ARENA_JOIN("arena_inst", "竞技场", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 11, "u_type", 4);
    }),
    ACTIVITY_JOIN(ColumnEnum.VAR_COL, "活动可变列", (cache, rows, service) -> {
        return service.getAcitivityUserJoinCountInfo((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), (List<Integer>) cache.get("act"));
    }),

    //角色养成
    LEVEL_UP("level_up", "升级", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 1);
    }),
    WAKE_UP("wake_up", "觉醒", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 2);
    }),
    BREAK("break", "突破", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 3);
    }),
    SKILL_UP("skill_up", "技能升级", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 4);
    }),
    SKILL_HI("skill_hi", "技能继承", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 5);
    }),
    SUMMON("summon", "抽卡", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 6);
    }),
    BLESS("PARC_INST", "祝福", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 7);
    }),
    S_MAKE("s_make", "圣印制作", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 8);
    }),
    S_WAKE("s_wake", "圣印觉醒", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 12, "r_type", 9);
    }),


    //公会活跃
    GUILD_COUNT("guild_count", "公会数量(当天晚12点)", (cache, rows, service) -> {
        return service.getGuildCount((long) cache.
                get("0point_s"), (long) cache.get("0point_e"), "gCount");
    }),
    GUILD_ADD("guild_add", "新增公会", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 13, "g_type", 1);
    }),
    GUILD_FIGHT("guild_fight", "公会战斗", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 13, "g_type", 3);
    }),
    GUILD_DONATE("guild_donate", "公会捐献", (cache, rows, service) -> {
        return service.getUserParticipation2((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), 13, "g_type", 4);
    }),
    GUILD_BUILD("guild_build", "公会建设", (cache, rows, service) -> {
        return service.getGuildBuild((long) cache.
                get("beforeClock"), (long) cache.get("endClock"));
    }),
    GUILD_ACT_PLAYER_RATIO("guild_act_player_ratio", "公会日活跃玩家比", (cache, rows, service) -> {
        return service.getGuildActUserRatio((long) cache.
                get("beforeClock"), (long) cache.get("endClock"), (long) cache.
                get("0point_s"), (long) cache.get("0point_e"));
    }),

    //物品统计
    GOODS_COUNT(ColumnEnum.VAR_COL, "物品统计变化", (cache, rows, service) -> {
        return service.getGoodsInfo((long) cache.
                get("beforeClock"), (long) cache.get("endClock"));
    }),



    //99乘法表一般的存留表 //该表的算法除了遵守AbstractTable的抽象方法,其余全部自制
    VAR_99(ColumnEnum.VAR_COL,"99乘法表可变列",null);


    ;
    private ColumnArithmetic rowArithmetic;
    private String key;
    private String word;

    public void  generate(Map<String, Object> cache, Row[] rows, LoginLogoutService service) {
        try {
            Row.putDataIntoRows(this.key, rows, rowArithmetic.getValue(cache, rows, service));
        } catch (Exception e) {
            e.printStackTrace();
            Row.putDataIntoRows(this.key, rows, (Map) null);
        }
    }

    ColumnEnum(String key, String word, ColumnArithmetic rowArithmetic) {
        this.key = key;
        this.word = word;
        this.rowArithmetic = rowArithmetic;
    }

    public static ColumnEnum getColumnEnumByKey(String key) {
        return valueOf(key.toUpperCase());
    }

    public String getKey() {
        return key;
    }

    public String getWord() {
        return word;
    }

    public static final String VAR_COL = "VAR";

    public static void main(String[] args) {
        ColumnEnum new_regester_user = getColumnEnumByKey("new_regester_user");
        System.out.println(new_regester_user);
    }

    @Override
    public String toString() {
        return "Col:[" + key + "|" + word + "]";
    }
}
