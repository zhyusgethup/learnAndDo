package com.master.gm.service.analyze;

import java.util.List;
import java.util.Map;

public interface LoginLogoutService {
    GoodsComputeService getGoodsComputeService();
    DBService getDBService();
    public Map<String,String> getGoodsInfo(long start, long end);
    /***
     * use
     * 获取道具的增加量和消耗量,isAdded=true是增加
     * @return
     */
    public  void getGoodsCidTotalByServerDate(long start,long end);

    /***
     * 获取建设度
     * @param start
     * @param end
     * @return
     */
    public  Map<String, String> getGuildBuild(long start, long end);
    /***
     * 获取公会数量 或 总人数
     * @param start
     * @param end
     * @return
     */
    public  Map<String, String> getGuildCount(long start, long end, String param);

    /***
     * 返回每个用户参与项目,角色养成的值 注意,如果值是0,那么不会存在key
     * (11 u_type, 12 r_type, 13 g_type)
     * @return
     */
    public  Map<String, String> getUserParticipation2(long start, long end, int logType, String param, int paramType) ;

    /***
     * 获取用户参与活动相关数据
     * @param start
     * @param end
     * @param cids
     * @return
     */
    public  Map<String, String> getAcitivityUserJoinCountInfo(long start, long end, List<Integer> cids);

    /***
     * 获取副本流失
     * @param insts
     * @param start
     * @param end
     * @return
     */
    public  Map<String, String> getInstBreakPointInfo(List<Integer> insts, long start, long end);

    /***
     * 获取新手引导流失数据
     * @param start
     * @param end
     * @return
     */
    public  Map<String, String> getNewPlayerBreakPointInfo(long start, long end);

    /***
     * 获取玩家流失相关数据
     * @param start
     * @param end
     * @param maxPoint
     * @param param
     * @param data
     */
    public  void getInfoForBreakPoint(long start, long end, int maxPoint, String param, Map<String, Map<String, Map<Integer, Long>>> data);

    /***
     * use
     * 根据角色创建时间与服务器开服时间的差值和登陆日期与角色创建时间的
     * 差值计算满足条件的总数和存留数
     * @param cre 角色创建时间和服务器开服时间相差的天数
     * @param lo 登陆时间和角色创建时间相差的天数
     * @return int[0] 满足角色创建时间和服务器开服时间相差天数的去重账号数
     *          int[1] 在int[0]基础上满足登陆日期与角色创建时间的去重账号数,即存留
     *
     *          0-1 次留 0-2 2留 0-3 3留 0-4:4留
     */
    public  Map<String, String> getRetentionByTwoMinusValues(int cre, int lo);

    /***
     * use 1.0
     * @return 获取新注册用户
     */
    public  Map<String, String> getNewRegesterAccountByTime(long startTime, long endTime);

    /***
     *use1.0
     * 根据玩家在线时长和日期统计
     * @param start
     * @param end
     * @param timeStart 在线时段开始时长(不包括该时间点)
     * @param timeEnd 在线时段结束时长(包括该时间点)
     * @return
     */
    public  Map<String, String> getOnlinePlayerCountByTimeTime(long start, long end, long timeStart, long timeEnd);

    /***
     * 获取公会活跃玩家比
     * @return
     */
    public  Map<String, String> getGuildActUserRatio(long point_s, long point_e,long start, long end);

    /**
     * use
     * 统计每个服务器的活跃账号数目  指定时间范围类登陆过指定天数即以上
     *
     * @param start
     * @param end
     * @return
     */
    public  Map<String, String> getActiveAccountCountByTimeAndServer(long start, long end, int num,boolean withGuild);

    /**
     * use
     * 根据在日期，小时数获取该小时类的平均在线时长，以玩家的账号为基本统计单元
     *
     * @return
     */
    public  Map<String, String> getAvgOnlineTimeByHourDate(long start, long end, Map<String, Object> longCache);

    String getRetentionByTwoMinusValuesIgloreServer(int cre, int lo);
}
