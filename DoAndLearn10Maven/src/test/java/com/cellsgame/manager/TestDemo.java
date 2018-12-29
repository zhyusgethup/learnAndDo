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
public class TestDemo extends com.cellsgame.HuyuMessageHandler {
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
    public TestDemo(MessageProcessor service) {
        super(service);
    }

    public TestDemo(MessageProcessor service, String accountId, Integer command) {
        super(service);
        this.AccountId = accountId;
        this.command = command;
    }

    public TestDemo(String accountId, Integer command) {
        super(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(10)));
        this.AccountId = accountId;
        this.command = command;
    }

    public TestDemo(String accountId, int command, String name) {
        this(accountId, command);
        this.name = name;
    }

    public TestDemo(String accountId, int command, String name, String... params) {
        this(accountId, command, name);
        this.datas = params;
    }


    public TestDemo(String accountId, int command, String name, Object[] datas) {
        this(accountId, command, name);
        this.datas = datas;

    }

    public Map<String, Object> getDataMap() {
        if (datas == null) {
            return GameUtil.createSimpleMap();
        }
        return getDataMap(datas);
    }

    public synchronized static Map<String, Object> getDataMap(Object[] params) {
        if (params == null || params.length % 2 != 0.) {
            throw new RuntimeException("参数错误");
        }

        Map<String, Object> data = GameUtil.createSimpleMap();
        if (params.length == 0) {
            return data;
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
                    sendMessage(connection, 20099, getDataMap(new Object[]{"serverId", 20180828, "accountId", AccountId}));
                    break;
                case 20099:
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
                        case TestManagerDemo.FRIEND_ADD_NEW:
                            sendMessage(connection, TestManagerDemo.FRIEND_ADD_NEW, getDataMap());
                            break;
                        case TestManagerDemo.CHAT_CHAT:
                            sendMessage(connection, TestManagerDemo.CHAT_CHAT, getDataMap(new Object[]{"type", 3, "msg", "你再发一个"}));
                            break;
                        case TestManagerDemo.CHAT_PRIVATE_MSG:
                            sendMessage(connection, TestManagerDemo.CHAT_PRIVATE_MSG, getDataMap());
                            break;
                        case TestManagerDemo.CHAT_GET_1v1_CACHE:
                            sendMessage(connection, TestManagerDemo.CHAT_GET_1v1_CACHE, getDataMap());
                            break;
                        case TestManagerDemo.CHAT_GET_MAIN_UI_CACHE:
                            sendMessage(connection, TestManagerDemo.CHAT_GET_MAIN_UI_CACHE, getDataMap());
                            break;
                        case TestManagerDemo.Player_Test_Logout:
                            sendMessage(connection, TestManagerDemo.Player_Test_Logout,getDataMap(new Object[]{}));
                            break;
                    }

                    break;
                case TestManagerDemo.FRIEND_ADD_NEW:
                    getMessage();
                    break;
                case TestManagerDemo.CHAT_CHAT:
                    getMessage();
                    break;
                case TestManagerDemo.CHAT_PRIVATE_MSG:
                    getMessage();
                    break;
                case TestManagerDemo.CHAT_GET_1v1_CACHE:
                    getMessage();
                    break;
                case TestManagerDemo.CHAT_GET_MAIN_UI_CACHE:
                    getMessage();
                    break;
                default:
                    getMessage();
            }
        } else {
            System.err.println("[" + name + "]" + map);
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

        connector.setHandler(new com.cellsgame.manager.TestDemo(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1))));

        connector.start();
    }

}


