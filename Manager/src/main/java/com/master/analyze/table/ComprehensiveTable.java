package com.master.analyze.table;

import com.master.analyze.analyeBean.TimeEnum;
import com.master.analyze.http.TableNeed;
import com.master.analyze.analyeBean.TableEnum;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class ComprehensiveTable extends MyTable {

    public ComprehensiveTable(TableEnum e) {
        super(e);
    }

    public long getEndClock(){
        return TimeUtil.getBeforeHourClock();
    }

    public String fixTimeStrFromMysql(String mysqlTimeStr){
        return mysqlTimeStr.substring(0,13);
    }
    public String getNearTimeStr(){
        return TimeEnum.HOUR_BEFORE.getTimeStr();
    }

    @Override
    public int generateDatasFromLogs(LoginLogoutService service, StringBuilder msg) {
        Map<String,Object> longCache = new HashMap<>();
        long endClock = getEndClock();
        longCache.put("endClock",endClock);
        longCache.put("timeStr",TimeEnum.HOUR_BEFORE.getTimeStr());
        longCache.put("beforeClock",TimeEnum.HOUR_BEFORE_BEFORE.getTimeClock());
        Map<String, String> serversConf = null;
        serversConf = TableNeed.getServersFromServerConf();
//        serversConf = genServerConf();
//            String regesterUserStr = TableNeed.getCountByTime(TableNeed.Q_REGISTER_COUNT_URL,beforeClock, endClock);
//        longCache.put("date",TimeUtil.formateLongByFormatter(TimeUtil.YMD, endClock));
        longCache.put("oldTime",0L);
//        longCache.put("monthStart_1",TimeEnum.MONTH_START_1);
//        longCache.put("monthEnd_1",TimeUtil.getEndMonthClock());
        long now = System.currentTimeMillis();
        longCache.put("weekStart_2",TimeEnum.WEEK_START_2.getTimeClock());
        longCache.put("dayStart_2",TimeEnum.DAY_START_2.getTimeClock());
        longCache.put("monthStart_2", TimeEnum.MONTH_START_2.getTimeClock());
        int size = productRows(longCache, serversConf, service, msg);
        return size;
    }

    public static void main(String[] args) {
        MyTable table = new ComprehensiveTable(TableEnum.COMPREHENSIVE);
        System.out.println();
    }
}
