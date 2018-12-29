package com.cellsgame.manager;

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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Created by Yang on 16/6/16.
 */
public class TestGuildFight extends com.cellsgame.HuyuMessageHandler {
    //    private static String ServerUrl = "192.168.10.117";
    private static String ServerUrl = "192.168.10.160";
    private static Integer ServerPort = 9999;
    private static final Integer POOL_ID = 1;
    private String AccountId = "030c4330-8b54-4491-86d5-4198c7a935f9";
    private final static SimpleDateFormat dataFormate = new SimpleDateFormat("hh:mm:ss");
    private static Map<String, Object> player = GameUtil.createSimpleMap();
    private String name;
    private Object[] datas;

    public String getAccount() {
        if (name != null) {
            return name;
        }
        return AccountId;
    }

    public void getMessage() {
        System.out.println("[" + name + "]" + data.toString());
    }

    /**
     * 构建消息处理器。
     *
     * @param service 收到消息之后,将消息分发到线程池的服务
     */
    public TestGuildFight(MessageProcessor service) {
        super(service);
    }

    public TestGuildFight(MessageProcessor service, String accountId, Integer command) {
        super(service);
        this.AccountId = accountId;
        this.command = command;
    }

    public TestGuildFight(String accountId, Integer command) {
        super(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(10)));
        this.AccountId = accountId;
        this.command = command;
    }

    public TestGuildFight(String accountId, int command, String name) {
        this(accountId, command);
        this.name = name;
    }

    public TestGuildFight(String accountId, int command, String name, String... params) {
        this(accountId, command, name);
        this.datas = params;
    }


    public TestGuildFight(String accountId, int command, String name, Object[] datas) {
        this(accountId, command, name);
        this.datas = datas;

    }

    public Map<String, Object> getDataMap() {
        if (datas == null) {
            return GameUtil.createSimpleMap();
        }
        return getDataMap(datas);
    }


    public synchronized static Map<String, Object> getDataMap(Object... params) {
        Map<String, Object> data = GameUtil.createSimpleMap();
        if (null == params || params.length == 0) {
            return data;
        }
        if (params.length % 2 != 0.) {
            throw new RuntimeException("参数错误");
        }

        for (int i = 0; i < params.length; i += 2) {
            if (params[i] instanceof String) {
                String name = (String) params[i];
                data.put(name, params[i + 1]);
            } else {
                throw new RuntimeException("参数错误");
            }
        }
        return data;

    }

    public static Map<String, Object> getSummonMsg(int heroId, int index) {
        Map<String, Object> data = GameUtil.createSimpleMap();
        data.put("cid", heroId);
        data.put("idx", index);
        return data;
    }

    private static Map<String, Object> getSummonInMsg() {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("poolid", 1);
        return dataMap;
    }

    private Integer command;

    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) {
        String co = (String) map.get("co");
        if (co.equals("0")) {
            switch (cmd) {
                case 10001:
                    sendMessage(connection, 20101, getDataMap(new Object[]{"serverId", 20180828, "accountId", AccountId, "name", name}));
                    break;
                case 20101:
                    System.out.println(map);
                    initPlayer(map);
                    pid = getPidFromPlayer(player);
                    printMessage(getMessage("20099"));
                    if (command == null) {
                        System.out.println("请输出命令");
                        Scanner sc = new Scanner(System.in);
                        command = Integer.valueOf(sc.next());
                    }
                    switch (command) {
                        case TestGuildFightManager.ShopBuy:
                            sendMessage(connection, TestGuildFightManager.ShopBuy, getDataMap());
                            break;
                        case TestGuildFightManager.ShopOpen:
                            sendMessage(connection, TestGuildFightManager.ShopOpen, getDataMap());
                            break;
                        case TestGuildFightManager.RANK_GET_RANK_LIST:
                            sendMessage(connection, TestGuildFightManager.RANK_GET_RANK_LIST, getDataMap());
                            break;
                        case TestGuildFightManager.Refresh:
                            sendMessage(connection, TestGuildFightManager.Refresh, getDataMap());
                            break;
                        case TestGuildFightManager.ARENA_CHALLENGE:
                            sendMessage(connection, TestGuildFightManager.ARENA_CHALLENGE, getDataMap());
                            break;
                        case TestGuildFightManager.MakeTeam:
                            sendMessage(connection, TestGuildFightManager.MakeTeam, getDataMap());
                            break;
                        case TestGuildFightManager.Guild_WordInfo:
                            sendMessage(connection, TestGuildFightManager.Guild_WordInfo, getDataMap(null));
                            break;
                        case TestManagerDemo.Player_Test_Logout:
                            sendMessage(connection, TestGuildFightManager.Player_Test_Logout, getDataMap(new Object[]{}));
                            break;
                        case TestGuildFightManager.Guild_Create:
                            sendMessage(connection, TestGuildFightManager.Guild_Create, getDataMap());
                            break;
                        case TestGuildFightManager.Guild_CityInfo:
                            sendMessage(connection, TestGuildFightManager.Guild_CityInfo, getDataMap());
                            break;
                        case TestGuildFightManager.Guild_Investigate:
                            sendMessage(connection, TestGuildFightManager.Guild_Investigate, getDataMap());
                            break;
                        case TestGuildFightManager.Guild_Attack_Troops: {
                            List out = GameUtil.createList();
                            List in = GameUtil.createList();
                            out.add(in);
                            in.add(0);
                            in.add(236);
                            in.add(0);
                            in.add(0);
                            sendMessage(connection, TestGuildFightManager.Guild_Attack_Troops, getDataMap(new Object[]{"op", out}));
                        }
                        break;
                        case TestGuildFightManager.Guild_Guard_Troops: {
                            List out = GameUtil.createList();
                            List in = GameUtil.createList();
                            out.add(in);
                            in.add(224);
                            in.add(225);
                            in.add(0);
                            in.add(0);
                            sendMessage(connection, TestGuildFightManager.Guild_Guard_Troops, getDataMap(new Object[]{"op", out}));
                        }
                        break;
                        case TestGuildFightManager.StrongHolGuild_Enter:
                            sendMessage(connection, TestGuildFightManager.StrongHolGuild_Enter, getDataMap());
                            break;
                        case TestGuildFightManager.Guild_Attack_In:
                            sendMessage(connection, TestGuildFightManager.Guild_Attack_In, getDataMap());
                            break;
                        case TestGuildFightManager.FightBegin:
                            sendMessage(connection, TestGuildFightManager.FightBegin, getDataMap());
                            break;
                    }
                    break;
                default:
                    getMessage();
            }
        } else {
            System.err.println("[" + name + "]" + map);
        }
    }

    private List<Integer> heroIds;
    private int sumCount;
    private int maxSumCount = 5;
    private int consecutiveCount = 0;
    private int validConsecutiveCount = 0;
    private Integer lastHeroId;//用来核对召唤的是否是目标英雄

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

    public void start() {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();

        addressMap.put("a", new InetSocketAddress(ServerUrl, ServerPort));


        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());

        connector.setHandler(this);
        connector.start();
    }

    public static void main(String[] args) {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();

        addressMap.put("a", new InetSocketAddress(ServerUrl, ServerPort));


        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());

        connector.setHandler(new com.cellsgame.manager.TestGuildFight(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1))));

        connector.start();
    }

}

