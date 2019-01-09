package com.cellsgame;

import com.alibaba.fastjson.JSONObject;
import com.cellsgame.common.util.GameUtil;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.core.Connector;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;
import com.cellsgame.gateway.message.processor.MessageProcessor;
import com.cellsgame.manager.TestGuildManager;
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
public class TestGuild extends com.cellsgame.HuyuMessageHandler {
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

    public String getMessage(String cmd) {
        switch (cmd) {
            case "20099":
                JSONObject pInfo = (JSONObject) data.get("pInfo");
                Integer guildId = pInfo.getInteger("guildId");
                String chars = pInfo.getString("guinm");
                String guinm = new String(chars);
                return "guildId = " + guildId + ",  guinm = " + guinm;
            case "120022":
                return data.toString();
            case "120003":
                return data.toString();
            case "120016":
                return data.toString();

        }
        return null;
    }

    /**
     * 构建消息处理器。
     *
     * @param service 收到消息之后,将消息分发到线程池的服务
     */
    public TestGuild(MessageProcessor service) {
        super(service);
    }

    public TestGuild(MessageProcessor service, String accountId, String command) {
        super(service);
        this.AccountId = accountId;
        this.command = command;
    }

    public TestGuild(String accountId, String command) {
        super(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(10)));
        this.AccountId = accountId;
        this.command = command;
    }

    public TestGuild(String accountId, String command, String name) {
        this(accountId, command);
        this.name = name;
    }

    public TestGuild(String accountId, String command, String name, String... params) {
        this(accountId, command, name);
        this.datas = params;
    }


    public TestGuild(String accountId, String command, String name, Object[] datas) {
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

    private String command;

    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) {
        String co = (String) map.get("co");
        if (co.equals("0")) {
            switch (cmd) {
                case 10001:
                    sendMessage(connection, 20101, getDataMap(new Object[]{"serverId", 20180828, "accountId", AccountId, "name", name}));
                    break;
                case 20101:
                    initPlayer(map);
                    pid = getPidFromPlayer(player);
                    printMessage(getMessage("20099"));
                    if (command == null) {
                        System.out.println("请输出命令");
                        Scanner sc = new Scanner(System.in);
                        command = sc.next();
                    }
                    switch (command) {
                        case "guild_Create"://创建军团 1
                            sendMessage(connection, 120001, getDataMap());
                            break;
                        case "guild_Join"://加入军团 1
                            sendMessage(connection, 120002, getDataMap());
                            break;
                        case "guild_Query"://查询军团 1
//                            sendMessage(connection, 120003, getDataMap(new Object[]{"name", "惜缘"}));
                            sendMessage(connection, 120003, getDataMap());
                            break;
                        case "guild_AcceptJoin"://同意加入军团 1
                            sendMessage(connection, 120004, getDataMap(new Object[]{"pid", 19, "accept", true}));
                            break;
                        case "guild_ModifyNotice"://修改公告 1
                            sendMessage(connection, 120005, getDataMap(new Object[]{"content", "新公告"}));
                            break;
                        case "guild_ModifyDesc"://修改宣言 1
                            sendMessage(connection, 120006, getDataMap(new Object[]{"content", "新宣言"}));
                            break;
                        case TestGuildManager.Guild_ModifyLogo://修改LOGO 0
                            sendMessage(connection, 120031, getDataMap(new Object[]{"lid", 5}));
                            break;
                        case "guild_ModifyName"://修改名字 0
                            sendMessage(connection, 120008, getDataMap(new Object[]{"name", ""}));
                            break;
                        case TestGuildManager.Guild_CancelJoin:
                            sendMessage(connection, 120030, getDataMap(new Object[]{"id", 21}));
                            break;
                        case "guild_ModifyRight"://修改权限 1
                            sendMessage(connection, 120009, getDataMap(new Object[]{"pid", 37, "right", 103}));
                            break;
                        case "guild_Out"://离开军团    1     注意pid是人的id   !+1
                            sendMessage(connection, 120010, getDataMap(new Object[]{"pid", 25}));
                            break;
                        case "guild_ChgOwner"://转让军团  1
                            sendMessage(connection, 120011, getDataMap(new Object[]{"pid", 15}));
                            break;
                        case "guild_Dissolution"://解散军团 1
                            sendMessage(connection, 120012, getDataMap(new Object[]{}));
                            break;
                        case "guild_CancelDissolution"://取消解散军团  0
                            sendMessage(connection, 120013, getDataMap(new Object[]{}));
                            break;
                        case TestGuildManager.Guild_Donate://捐献  2
                            sendMessage(connection, 120014, getDataMap(new Object[]{"dCid", 60200002}));
                            break;
                        case "guild_Enter"://进入军团  0
                            sendMessage(connection, 120015, getDataMap(new Object[]{}));
                            break;
                        case "guild_ShowMemberList"://查询军团成员 1
                            sendMessage(connection, 120016, getDataMap(new Object[]{}));
                            break;
                        case "guild_ShowReqList"://查询请求列表   1
                            sendMessage(connection, 120017, getDataMap(new Object[]{}));
                            break;
                        case "guild_InvitePlayer"://邀请玩家加入 0
                            sendMessage(connection, 120018, getDataMap(new Object[]{"pid", 1231231}));
                            break;
                        case "guild_ModifyNeedReqStatus"://修改公会是否需要请求  3
                            sendMessage(connection, 120021, getDataMap(new Object[]{"need", false}));
                            break;
                        case TestGuildManager.Guild_GetGuildLog://获取操作日志 1
                            sendMessage(connection, 120022, getDataMap(new Object[]{}));
                            break;
                        case "guild_GetBoss"://获取boss列表 0
                            sendMessage(connection, 120023, getDataMap(new Object[]{}));
                            break;
                        case "guild_OpenBoss"://开启boss 0
                            sendMessage(connection, 120024, getDataMap(new Object[]{}));
                            break;
                        case "guild_FightBoss"://攻击boss 0
                            sendMessage(connection, 120025, getDataMap(new Object[]{}));
                            break;
                        case "guild_GetRank"://获取排名 0
                            sendMessage(connection, 120026, getDataMap(new Object[]{}));
                            break;
                        case "guild_NotAllJoinReq"://否决所有加入请求
                            sendMessage(connection, 120027, getDataMap(new Object[]{}));
                            break;
                        case "guild_BossRef"://boss刷新 0
                            sendMessage(connection, 120028, getDataMap(new Object[]{}));
                            break;
                        case "guild_OneKeyJoin"://boss刷新 0
                            sendMessage(connection, 120029, getDataMap(new Object[]{}));
                            break;
                        case "guild_DetailInfo": //获取公会详情
                            sendMessage(connection, 120032, getDataMap(new Object[]{}));
                            break;
                        case TestGuildManager.Guild_OnekeyAcceptOrRefuse://一键申请或拒绝
                            sendMessage(connection, 120034, getDataMap(new Object[]{"isAllow", true}));
                            break;
                        case TestGuildManager.Guild_Allocate:
                            sendMessage(connection, 120033, getDataMap(new Object[]{"pid", 4, "gid", 41020005, "num", 1}));
                            break;
                        case TestGuildManager.Mail_List:
                            sendMessage(connection, 130006, getDataMap(new Object[]{}));
                            break;
                        case TestGuildManager.Mail_Pick:
                            sendMessage(connection, 130004, getDataMap(new Object[]{"id", 12}));
                            break;
                        case TestGuildManager.RANK_GET_RANK_LIST:
                            sendMessage(connection, 340003, getDataMap(new Object[]{"type",5 }));
                            break;
                        case TestGuildManager.Mail_Get:
                            sendMessage(connection,130002,getDataMap());
                            break;
                    }
                    break;
                case 120032:
                    System.out.println("获取详情信息");
                    getMessage();
                    break;
                case 120001:
                    System.out.println("创建军团成功");
                    getMessage();
                    break;
                case 120002:
                    System.out.println("加入军团成功");
                    getMessage();
                    break;
                case 120003:
                    System.out.println("查询军团成功");
                    printMessage(getMessage("120003"));
                    break;
                case 120004:
                    System.out.println("同意加入军团成功");
                    getMessage();
                    break;
                case 120005:
                    System.out.println("修改公告成功");
                    break;
                case 120006:
                    System.out.println("修改宣言成功");
                    break;
                case 120007:
                    getMessage();
                    break;
                case 120008:
                    System.out.println("修改名字成功");
                    break;
                case 120009:
                    getMessage();
                    break;
                case 120010:
                    getMessage();
                    break;
                case 120011:
                    getMessage();
                    break;
                case 120012:
                    getMessage();
                    break;

                case 120014:
                    getMessage();
                    break;
                case 120015:
                    getMessage();
                    break;
                case 120016:
                    printMessage(getMessage("120016"));
                    break;
                case 120017:
                    getMessage();
                    getMessage();
                    break;
                case 120018:
                    getMessage();
                    break;
                case 120019:
                    System.out.println("修改微信成功");
                    break;
                case 120021:
                    System.out.println("修改公会是否需要请求成功");
                    break;
                case 120022:
                    getMessage();
                    printMessage(getMessage("120022"));
                    break;
                case 120023:
                    System.out.println("获取boss列表成功");
                    break;
                case 120024:
                    System.out.println("开启boss成功");
                    break;
                case 120025:
                    System.out.println("攻击boss成功");
                    break;
                case 120026:
                    System.out.println("获取排名成功");
                    break;
                case 120027:
                    getMessage();
                    break;
                case 120028:
                    System.out.println("boss刷新成功");
                    break;
                case 120029:
                    getMessage();
                    break;
                case 120030:
                    getMessage();
                    break;
                case 120031:
                    getMessage();
                    break;
                case 120034:
                    getMessage();
                    break;
                case 120033:
                    getMessage();
                    break;
                case 130006:
                    getMessage();
                    break;
                case 130004:
                    getMessage();
                    break;
                case 340003:
                    getMessage();
                    break;
                case 130002:
                    getMessage();
                    break;
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

        connector.setHandler(new TestGuild(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1))));

        connector.start();
    }

}


