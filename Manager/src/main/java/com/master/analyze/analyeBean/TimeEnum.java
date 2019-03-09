package com.master.analyze.analyeBean;

import com.master.util.TimeUtil;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter //集中定义时间
public enum TimeEnum {

    //公用部分
    DAY_BEFORE("当天凌晨整点", () -> TimeUtil.getTodayStartClock(), TimeUtil.YMD),
    //这个值才是公用表显示的时间字符串
    DAY_BEFORE_BEFORE("昨天凌晨整点", () -> DAY_BEFORE.timeClock - TimeUtil.DAY_MIL,TimeUtil.YMD),

    //综合表使用
    //这个值才是综合表显示的时间字符串
    HOUR_BEFORE("当前时间整点", () -> TimeUtil.getBeforeHourClock(), TimeUtil.LEAST_HOUR),
    HOUR_BEFORE_BEFORE("当前时间整点之前1小时整点", () -> TimeEnum.HOUR_BEFORE.timeClock - TimeUtil.HOUR_MIL),
    WEEK_START_2("当前7天前那刻", () -> System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000),
    DAY_START_2("当前1天前那刻", () -> System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000),
    MONTH_START_2("当前31天前那刻", () -> System.currentTimeMillis() - 31 * 24 * 60 * 60 * 1000),
/*   ARPU ARPPU
   MONTH_START_1("本月初的整点", () -> TimeUtil.getBeforeMonthClock()),
   MONTH_END_1("下月初的整点", () -> TimeUtil.getEndMonthClock()),*/

    //公会活跃表
    POINT_S("缝隙扫描整点日志时间头", () -> DAY_BEFORE.timeClock + TimeUtil.MINUTE_MIL * 5),
    POINT_E("缝隙扫描整点日志时间尾", () -> DAY_BEFORE.timeClock - TimeUtil.HOUR_MIL),

    //留存表
    TWO_WEEK_START_2("当前14天前那刻", () -> System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000),

    //







    ;
    //留存时间计算,希望更改这里能改变整个留存的时间跨度
    public static final long RENT_TIME = TimeUtil.DAY_MIL;
    TimeEnum(String desc, TimeGet funcs) {
        this.desc = desc;
        this.funcs = funcs;
    }

    TimeEnum(String desc, TimeGet funcs, DateTimeFormatter dtf) {
        this.desc = desc;
        this.funcs = funcs;
        this.dtf = dtf;
    }

    private DateTimeFormatter dtf;
    private String timeStr;
    private long timeClock;
    private String desc;//描述
    private TimeGet funcs;

    public void flush(long time) {
        timeClock = funcs.getTimeClock();
        if (null != dtf)
            timeStr = TimeUtil.formateLongByFormatter(dtf, timeClock);
    }
}
