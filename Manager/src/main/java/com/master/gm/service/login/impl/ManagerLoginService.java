package com.master.gm.service.login.impl;

import com.alibaba.fastjson.JSON;
import com.gmdesign.bean.GroupBean;
import com.gmdesign.bean.MenuBean;
import com.gmdesign.bean.other.GmHashMap;
import com.gmdesign.bean.other.UserForService;
import com.gmdesign.exception.GmException;
import com.gmdesign.util.DateUtil;
import com.master.analyze.http.TableNeed;
import com.master.bean.ComputerTableBean;
import com.master.cons.Cons;
import com.master.gm.BaseService;
import com.master.gm.bean.GmSql;
import com.master.gm.service.login.ManagerLoginServiceIF;
import com.master.util.SqlUtil;
import com.master.util.TimeUtil;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author DJL
 * @ClassName ManagerLoginService
 * @Description GM登录系统跳转过来的信息验证
 * @date 2015-12-10 下午7:40:26
 */
@IocBean
public class ManagerLoginService extends BaseService implements ManagerLoginServiceIF {

    @Override
    public String judgeUserData(String menuData, UserForService user)
            throws GmException {
//		if(user==null||user.getName()==null||user.getUserKey()==null){
//			throw new GmException("用户信息有误~~~");
//		}
//		String key=new String(StringUtil.hex2byte(user.getUserKey().getBytes()));
//		if(!key.equals(user.getName())){
//			throw new GmException("登录方式有误~~~");
//		}
        if (menuData.length() < 1) {
            throw new GmException("菜单数据缺失~~~");
        }
        List<GroupBean> Menus;
        try {
            Menus = JSON.parseArray(menuData, GroupBean.class);
        } catch (Exception e) {
            throw new GmException("菜单数据有误~~~");
        }
        StringBuilder sb = new StringBuilder(1024);
        return convertMenu(Menus, sb);
    }

    private String dataIsNull(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    @Override
    public List<String[]> initWindowComprehensive() throws GmException {
        List<String[]> result = new ArrayList<>();
        Date now = new Date();
        Date start = DateUtil.getDaytoDay(now, -1);
        Date end = DateUtil.getDaytoDay(now, -7);
        Sql sql = SqlUtil.getSql(GmSql.GET_COMPREHENSIVE, DateUtil.getDateString(end), DateUtil.getDateString(start));
        this.dao.execute(sql);
        List<GmHashMap> mapList = sql.getList(GmHashMap.class);
        for (GmHashMap obj : mapList) {
            String[] res = new String[13];
            res[11] = dataIsNull(obj.get("newPlayer"));
            res[12] = dataIsNull(obj.get("dayPayNum"));
            res[0] = dataIsNull(obj.get("logTime"));
            res[1] = dataIsNull(obj.get("player"));
            res[2] = dataIsNull(obj.get("register"));
            res[3] = dataIsNull(obj.get("login"));
            res[4] = dataIsNull(obj.get("pay"));
            res[5] = dataIsNull(obj.get("dayPay"));
            res[6] = dataIsNull(obj.get("payNum"));
            Object o1 = obj.get("arppu") != null ? obj.get("arppu") : 0;
            Object o2 = obj.get("permeate") != null ? obj.get("permeate") : 0;
            Object o3 = obj.get("convert") != null ? obj.get("convert") : 0;
            Object o4 = obj.get("arpu") != null ? obj.get("arpu") : 0;
            res[7] = ComputerTableBean.convertThree(o1);
            res[8] = ComputerTableBean.convertThree(o4);
            res[9] = ComputerTableBean.convertTwo(Double.parseDouble(o2.toString()));
            res[10] = ComputerTableBean.convertTwo(Double.parseDouble(o3.toString()));
            result.add(res);
        }
        return result;
    }

    @Override
    public List<String[]> initWindowRate() throws GmException {
        Date now = new Date();
        Date start = DateUtil.getDaytoDay(now, -1);
        Date end = DateUtil.getDaytoDay(now, -7);
        Sql sql = SqlUtil.getSql(GmSql.QUERY_7_LOSS, DateUtil.getDateString(end), DateUtil.getDateString(start));
        this.dao.execute(sql);
        List<GmHashMap> mapList = sql.getList(GmHashMap.class);
        Map<String, String[]> reMap = new HashMap<>();
        String key;
        for (GmHashMap re : mapList) {
            key = re.get("logTime").toString() + re.get("createNum").toString();
            String[] r = reMap.get(key);
            if (r == null) {
                r = new String[9];
                r[0] = re.get("logTime").toString();
                r[1] = re.get("createNum").toString();
            }
            int index = Integer.valueOf(re.get("tDay").toString());
            r[index] = ComputerTableBean.convertProportion(re.get("loss"));
            reMap.put(key, r);
        }
        return new ArrayList<>(reMap.values());
    }

    public static String convertMenu(Collection<GroupBean> Menus, StringBuilder sb) {
        String k = "";
        for (GroupBean gb : Menus) {
            if (gb.getGroupIcon() != null && gb.getGroupIcon().length() > 1) {
                k = "<i class='" + gb.getGroupIcon() + "'></i>";
            }
            sb.append("<li><a href='#'>").append(k).append("<span class='title'>").append(gb.getGroupName()).append("</span></a>");
            sb.append("<ul>");
            if (gb.getMenu() != null && !gb.getMenu().isEmpty()) {
                for (MenuBean mb : gb.getMenu()) {
                    sb.append("<li><a href='#' onclick=\"forwardPage('").append(mb.getUrl()).append("','").
                            append(mb.getId()).append("');\"><span class='title'>").append(mb.getTitle()).append("</span></a></li>");
                }
            }
            if (gb.getGroups() != null && !gb.getGroups().isEmpty()) {
                convertMenu(gb.getGroups(), sb);
            }
            sb.append("</ul>");
            sb.append("</li>");
        }
        return sb.toString();
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Map<String ,List<String>> initRetentionData() throws GmException {
        Map<String, List<String>> map = new ConcurrentHashMap<>();
        List<String> time = new ArrayList<>();
        List<String> server = new ArrayList<>();
        return map;
    }

    /***
     * 初始化综合数据
     * @return
     * @throws GmException
     */
    @Override
    public Map<String ,List<String>> initComprehensive() throws GmException {
        Map<String,List<String>> map = new ConcurrentHashMap<>();
//        Sql sql = SqlUtil.getSql(GmSql.GET_COMPREHENSIVE);
//        this.dao.execute(sql);
//        List<GmHashMap> mapList = sql.getList(GmHashMap.class);
//        List<String> time = new ArrayList<>();
//        List<String> server = new ArrayList<>();
//        List<String> registerUsers = new ArrayList<>();
//        List<String> avgOnlineTime = new ArrayList<>();
//        List<String> payUsersCount = new ArrayList<>();
//        List<String> payAmount = new ArrayList<>();
//        List<String> arppu = new ArrayList<>();
//        List<String> arpu = new ArrayList<>();
//        List<String> payPermeate = new ArrayList<>();
//        List<String> ltv = new ArrayList<>();
//        List<String> totalRegisterUsers = new ArrayList<>();
//        List<String> weekActivityUsers = new ArrayList<>();
//        List<String> dayActivityUsers = new ArrayList<>();
//        List<String> monthActivityUsers = new ArrayList<>();
//
//        if (mapList.size() > 0) {
//            for (int i = 0; i < mapList.size(); i++) {
//                Date date = (Date) mapList.get(i).get("time");
//                if (null == date) {
//                    time.add("");
//                } else {
//                    time.add(sdf.format(date));
//                }
//                server.add(dataIsNull(mapList.get(i).get("server")));
//                registerUsers.add(dataIsNull(mapList.get(i).get("registerUsers")));
//                long avgOnlineTime1 = (long) mapList.get(i).getOrDefault("avgOnlineTime", 0);
//                avgOnlineTime.add(formateTime(avgOnlineTime1));
//                payUsersCount.add(dataIsNull(mapList.get(i).get("payUsersCount")));
//                payAmount.add(dataIsNull(mapList.get(i).get("payAmount")));
//                arppu.add(dataIsNull(mapList.get(i).get("arppu")));
//                arpu.add(dataIsNull(mapList.get(i).get("arpu")));
//                payPermeate.add(dataIsNull(mapList.get(i).get("payPermeate")) + "%");
//                ltv.add(dataIsNull(mapList.get(i).get("ltv")));
//                totalRegisterUsers.add(dataIsNull(mapList.get(i).get("server")));
//                weekActivityUsers.add(dataIsNull(mapList.get(i).get("server")));
//                dayActivityUsers.add(dataIsNull(mapList.get(i).get("server")));
//                monthActivityUsers.add(dataIsNull(mapList.get(i).get("server")));
//            }
//        } else {
//            long endClock = TimeUtil.getBeforeHourClock();
//            String timeStr = TimeUtil.formateLongByFormatter(TimeUtil.LEAST_HOUR, endClock);
//            long beforeClock = endClock - 1000 * 60 * 60;
//            Map<String, String> serversConf = TableNeed.getServersFromServerConf();
//            Set<Map.Entry<String, String>> entries = serversConf.entrySet();
////            String regesterUserStr = TableNeed.getCountByTime(TableNeed.Q_REGISTER_COUNT_URL,beforeClock, endClock);
//            String date = TimeUtil.formateLongByFormatter(TimeUtil.YMD, endClock);
//            long oldTime = 0;
////            int hour = TimeUtil.getHour(endClock);
//            long monthStart_1 = TimeUtil.getBeforeMonthClock();
//            long monthEnd_1 = TimeUtil.getEndMonthClock();
//            long now = System.currentTimeMillis();
//            long weekStart_2 = now - 7 * 24 * 60 * 60 * 1000;
//            long dayStart_2 = now - 1 * 24 * 60 * 60 * 1000;
//            long monthStart_2 = now - 31 * 24 * 60 * 60 * 1000;
//            for (Map.Entry<String, String> entry : entries) {
//                String serverId = entry.getKey();
//                String serverName = entry.getValue();
//                //时间
//                time.add(timeStr);
//                //服务器
//                server.add(serverName);
//                //新增注册
////                long newRegesterAccountByServerAndTime = LoginLogoutServiceImpl.getNewRegesterAccountByServerAndTime(serverId, beforeClock, endClock);
////                registerUsers.add(String.valueOf(newRegesterAccountByServerAndTime));
//                //总注册
////                long total = LoginLogoutServiceImpl.getNewRegesterAccountByServerAndTime(serverId, oldTime, endClock);
////                totalRegisterUsers.add(String.valueOf(total));
//                //活跃用户
////                long weekActivePlayerCountSimple = LoginLogoutServiceImpl.getActiveAccountCountByTimeAndServer(serverId,weekStart_2, endClock,1);
////                weekActivityUsers.add(String.valueOf(weekActivePlayerCountSimple));
////                long dayActivePlayerCountSimple = LoginLogoutServiceImpl.getActiveAccountCountByTimeAndServer(serverId,dayStart_2, endClock,1);
////                dayActivityUsers.add(String.valueOf(dayActivePlayerCountSimple));
////                long monthActivePlayerCountSimple = LoginLogoutServiceImpl.getActiveAccountCountByTimeAndServer(serverId,monthStart_2, endClock,1);
////                monthActivityUsers.add(String.valueOf(monthActivePlayerCountSimple));
//                //平均在线时长
////                double avgOnlineTimeByServerHourDate = LoginLogoutServiceImpl.getAvgOnlineTimeByServerHourDate(date, hour, serverId);
////                System.out.println("avg = " + avgOnlineTimeByServerHourDate);
////                avgOnlineTime.add(formateTime(avgOnlineTimeByServerHourDate));
//                //充值用户
//                payUsersCount.add(TableNeed.getCountByTime(TableNeed.PAY_ACCOUNT_COUNT_URL,beforeClock, endClock));
//                //充值金额
//                payAmount.add(TableNeed.getCountByTime(TableNeed.PAY_AMOUNT_URL,beforeClock,endClock));
//                //ARPPU 每付费用户平均收益 时间跨度 月
//                String arppu_str = TableNeed.getCountByTime(TableNeed.PAY_ARPPU_URL, monthStart_1, monthEnd_1);
//                arppu.add(arppu_str);
//                //ARPU //   月总收入/月付费用户数
//                double monthPayAmount = TableNeed.getDoubleCountByTime(TableNeed.PAY_AMOUNT_URL, monthStart_1, monthEnd_1);
//                //付费渗透 //付费用户数 / 活跃用户数 时间?
//
//                //LTV // 生命周期内的充值金额 / 条件账户数
//            }
//            map.put("time",time);
//            map.put("server",server);
//            map.put("registerUsers",registerUsers);
//            map.put("avgOnlineTime",avgOnlineTime);
//            map.put("payAmount",payAmount);
//            map.put("arppu",arppu);
//            map.put("arpu",arpu);
//            map.put("payPermeate",payPermeate);
//            map.put("ltv",ltv);
//            map.put("totalRegisterUsers",totalRegisterUsers);
//            map.put("weekActivityUsers",weekActivityUsers);
//            map.put("dayActivityUsers",dayActivityUsers);
//            map.put("monthActivityUsers",monthActivityUsers);
//        }
        return map;
    }

    private String formateTime(double avgOnlineTime1) {
        if (0 == avgOnlineTime1) {
            return Cons.ZERO_STR;
        } else {
            String units = "ms";
            if (avgOnlineTime1 > 1000 * 60) {
                avgOnlineTime1 = avgOnlineTime1 / 1000 / 60;
                units = "min";
                if (avgOnlineTime1 > 60) {
                    avgOnlineTime1 = avgOnlineTime1 / 60;
                    units = "h";
                }
            }
            return String.valueOf(avgOnlineTime1) + units;
        }
    }
}
