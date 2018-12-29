package com.cellsgame;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cellsgame.common.util.GameUtil;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.core.Connector;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;
import com.cellsgame.gateway.message.processor.MessageProcessor;
import com.google.common.collect.Maps;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Created by Yang on 16/6/16.
 */
public class TestSummon extends com.cellsgame.HuyuMessageHandler {
//    private static String ServerUrl = "192.168.10.117";
    private static String ServerUrl = "192.168.10.160";
    private static Integer ServerPort = 9999;
    private static final Integer POOL_ID  = 1;
    private String AccountId = "030c4330-8b55-4474-86d5-4198c7a935f9";
    private final static SimpleDateFormat dataFormate = new SimpleDateFormat("hh:mm:ss");
    private static Map<String, Object> player = GameUtil.createSimpleMap();


    /**
     * 构建消息处理器。
     *
     * @param service 收到消息之后,将消息分发到线程池的服务
     */
    public TestSummon(MessageProcessor service) {
        super(service);
    }

    @Override
    public String getAccount() {
        return AccountId;
    }

    private boolean isConsecutive = false;
    private int consecutiveCount = 0;
    private int validConsecutiveCount = 0;
    private boolean consecutiveLy = false;
    private int taskCount;
    private List<Integer> heroIds;
    private Integer lastHeroId;//用来核对召唤的是否是目标英雄
    private int total;
    public TestSummon(MessageProcessor service, int total, int taskCount, boolean isConsecutive) {
        this(service);
        this.taskCount = taskCount;
        this.isConsecutive = isConsecutive;
        this.total = total;
    }

    private Integer currency;
    private Deque<Integer> currencyHis;
    private Deque<Integer> heroIdsHis;
    List<String> exceptionLogs = GameUtil.createList();

    /***
     * 根据模块做日志,并标记是否是异常
     * @param cmd
     * @param isExcption
     */
    private void log(String cmd, boolean isExcption) {
        switch (cmd) {
            case "summon":
                String exWord = isExcption == false? "":"exception";
                Integer lastCurrency = currencyHis.getLast();
                Integer cost = lastCurrency - currency;
                String log = "[Test summon " + exWord + dataFormate.format(new Date()) +  "] ( lastId = " + lastHeroId +
                        ":heroId =" + heroIdsHis.getLast() + ")  ( lastCurrency =" + lastCurrency + ": currency =" +
                        currency + ": cost =" + cost + ")";
                break;
        }
    }

    private Integer getNowCurrencyByMap(Map map) {
        return getNowCurrencyByMap(map,"dptOp");
    }
    private Integer getNowCurrencyByMap(Map map,String deptName) {
        Integer num = null;
        JSONObject dptOp = (JSONObject) map.get(deptName);
        JSONArray gl = dptOp.getJSONArray("gl");
        for(int i = 0; i < gl.size(); i++) {
            JSONObject obj = (JSONObject) gl.get(i);
            Integer gid = obj.getInteger("gid");
            if(gid.equals(41060001)) {
                num  = obj.getInteger("num");
            }
        }
        return  num;
    }
    /***
     * 检查召唤结果(英雄id,背包信息变更是否正确[扣钱对不对],并做日志打印)
     * @param map
     * @return
     */
    private void checkSummonResult(Map map) {

        JSONObject qstOp = (JSONObject) map.get("qstOp");
        JSONObject heroInfo = (JSONObject)map.get("hInfo");
        Integer cid = heroInfo.getInteger("cid");
        Integer num = getNowCurrencyByMap(map);
        currency = num;
        heroIdsHis.addLast(cid);
        if(!cid.equals(lastHeroId)) {
            log("summon", true);
        }else {
            log("summon", false);
            heroIds.remove(cid);
            consecutiveCount++;
            validConsecutiveCount++;
        }
        currencyHis.addLast(num);
    }
    private Integer getHeroIdByMap(Map map) {
        JSONObject heroInfo = (JSONObject)map.get("hInfo");
        Integer cid = heroInfo.getInteger("cid");
        return cid;
    }
    /****
     * 收到450002命令的处理方法.
     * @param connection
     * @param map
     */
    private  void getCmd450002Function(Connection connection, Map map) {
//        checkSummonResult(map);
        if(validConsecutiveCount == taskCount || consecutiveCount == total) {
            sendMessage(connection,450003,GameUtil.createSimpleMap());
            return;

        }

        int heroId;
/*        if(!consecutiveLy) {//如果是在不是正在连续地取英雄(就是连续模式下取第二个及以上),那么读取英雄列表
            heroIds = (List<Integer>) map.get("hInfo");
        }*/
        heroId = heroIds.get(validConsecutiveCount);
        int index = validConsecutiveCount;

        if(validConsecutiveCount < taskCount && consecutiveCount < total) {
            sendMessage(connection, 450002, getDataMap(new Object[]{"cid", heroId, "idx", index}));
            validConsecutiveCount++;
            consecutiveCount++;
           // consecutiveLy = true;
        }
    }

    /***
     * 收到450001的处理方法,此时应该有了英雄列表才是
     * @param connection
     */
    private void getCmd450001Function(Connection connection, Map map) {
        heroIds = (List<Integer>) map.get("heroes");
        int heroId = heroIds.get(validConsecutiveCount);
        int index = 0;
        validConsecutiveCount++;
        consecutiveCount++;
        sendMessage(connection, 450002, getDataMap(new Object[]{"cid", heroId, "idx", index}));
        lastHeroId = heroId;
//                    sendMessage(connection,450002,getSummonMsg(heroId,index));
//        if(!isConsecutive || validConsecutiveCount == 5) {
//            sendMessage(connection, 450002, getDataMap(new Object[]{"cid", heroId, "idx", index}));
//        }else {
//
//        }
    }
    private void summonIn(Connection connection,int poolId, Map map) {
        if(total == consecutiveCount){
            System.exit(0);
        }
        if(heroIds == null) {
            heroIds = GameUtil.createList();
        }else {
            heroIds.clear();
        }
        validConsecutiveCount = 0;
        if(heroIdsHis == null) {
            heroIdsHis = new LinkedList<>();
        }else {
            heroIdsHis.clear();
        }
        if(currencyHis == null) {
            currencyHis = new LinkedList<>();
        }else {
            currencyHis.clear();
        }
        sendMessage(connection, 450001,getDataMap(new Object[]{"poolid",1}));
        //  Integer num = getNowCurrencyByMap(map,"dptInfo");
//        currency = num;
//        currencyHis.addLast(num);
    }
    public static Map<String,Object> getDataMap(Object[] params) {
        if(params == null || params.length == 0 || params.length % 2 != 0.) {
            throw new RuntimeException("参数错误");
        }
        Map<String, Object> data = GameUtil.createSimpleMap();
        for (int i = 0; i < params.length; i+=2) {
            if(params[i] instanceof  String) {
                String name =(String) params[i];
                data.put(name,params[i + 1]);
            }else {
                throw new RuntimeException("参数错误");
            }
        }
        return data;

    }
    public static Map<String,Object> getSummonMsg(int heroId, int index) {
        Map<String, Object> data = GameUtil.createSimpleMap();
        data.put("cid",heroId);
        data.put("idx",index);
        return data;
    }
    private static Map<String,Object> getSummonInMsg() {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("poolid",1);
        return dataMap;
    }


    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) {
        String co = (String)map.get("co");
        if (co.equals("0")) {
            switch (cmd) {
                case 10001:
                    //sendMessage(connection, 20099, getEnterGameMsg(AccountId));
                    sendMessage(connection,20101,getDataMap(new Object[]{"serverId", 20180828,
                            "accountId","030c4330-8b55-4474-86d5-4198c7a935f4","name","凶凶凶4"}));
                    break;
                case 20101:
                    initPlayer(map);
                    System.out.println(map);
//                    sendMessage(connection, 450001,getSummonInMsg());
                    summonIn(connection,POOL_ID,map);
                    break;
                case 450001://已发送选定英雄池编号,进入英雄选择阶段
                   getCmd450001Function(connection,map);
                    break;
                case 450002://已选中目标英雄
                        getCmd450002Function(connection,map);
                        break;
                    case 450003: //已退出英雄池;
                        summonIn(connection,POOL_ID,map);
//                        System.out.println("已退出英雄池,程序结束 打印日志");
//                        exceptionLogs.forEach(x -> System.out.println(x));
                      //  System.exit(0);
                        break;
                case 450004:
                    break;
            }
        } else {

        }
    }

    public static void initPlayer(Map<String, Object> data) {
        for (String key : data.keySet()) {
            player.put(key, data.get(key));
        }
    }
    // 获取英雄列表
    public static List<Object> getHeroes() {
        if (player.containsKey("heroes")) {
            return (List<Object>) player.get("heroes");
        }
        return GameUtil.createList();
    }

    private static Map<String, Object> getEnterGameMsg(String accountId) {

            Map<String, Object> dataMap = GameUtil.createSimpleMap();
            dataMap.put("serverId", 20180828);
            dataMap.put("accountId", accountId);

            return dataMap;
    }


    public static void main(String[] args) {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();

        addressMap.put("a", new InetSocketAddress(ServerUrl, ServerPort));


        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());

        connector.setHandler(new TestSummon(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1)),10,5,true));

        connector.start();
    }
    public void start() {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();

        addressMap.put("a", new InetSocketAddress(ServerUrl, ServerPort));


        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());
        connector.setHandler(this);
        connector.start();
    }

}
