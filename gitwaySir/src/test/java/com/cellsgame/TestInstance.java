package com.cellsgame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.cellsgame.common.util.GameUtil;
import com.cellsgame.common.util.StringUtil;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.core.Connector;
import com.cellsgame.gateway.message.Message;
import com.cellsgame.gateway.message.MessageHandler;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;
import com.cellsgame.gateway.message.processor.MessageProcessor;
import com.cellsgame.manager.TestGuildManager;
import com.cellsgame.manager.TestInstanager;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yang on 16/6/16.
 */
public class TestInstance extends com.cellsgame.HuyuMessageHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestInstance.class);
    private static AtomicInteger lastClientMessageId = new AtomicInteger(0);
    private static AtomicInteger lastServerMessageId = new AtomicInteger(0);



    private static Map<String, Object> player = GameUtil.createSimpleMap();
    private static Map<String, Object> resources = GameUtil.createSimpleMap();
    private static Integer fightTimes = 0;

    private static String ServerUrl = "192.168.10.160";
    private static Integer ServerPort = 9999;
    private static final Integer POOL_ID = 1;
    private String AccountId = "030c4330-8b54-4491-86d5-4198c7a935f9";
    private final static SimpleDateFormat dataFormate = new SimpleDateFormat("hh:mm:ss");
    private String name;
    private Object[] datas;
    private static String jsonStr;
    private String command;

    static {
//        getResourcesFromJsonString("instance_1");
    }

    public String getAccount() {
        if (name != null) {
            return name;
        }
        return AccountId;
    }
    public void getMessage() {
        System.out.println("[" + name + "][" + pid + "]" + data.toString());
    }
    /**
     * 构建消息处理器。
     *
     * @param service 收到消息之后,将消息分发到线程池的服务
     */
    public TestInstance(MessageProcessor service) {
        super(service);
    }

    public TestInstance(MessageProcessor service, String accountId, String command) {
        super(service);
        this.AccountId = accountId;
        this.command = command;
    }

    public TestInstance(String accountId, String command) {
        super(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(10)));
        this.AccountId = accountId;
        this.command = command;
    }

    public TestInstance(String accountId, String command, String name) {
        this(accountId, command);
        this.name = name;
    }

    public TestInstance(String accountId, String command, String name, String... params) {
        this(accountId, command, name);
        this.datas = params;
    }
    private int dirCount;

    public TestInstance(String accountId, String command, String name, int dirCount, Object[] datas) {
        this(accountId, command, name);
        this.datas = datas;
        this.dirCount = dirCount;

    }

    public Map<String, Object> getDataMap() {
        if (datas == null) {
            return GameUtil.createSimpleMap();
        }
        return getDataMap(datas);
    }

    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) {
        String co = (String) map.get("co");
        if (co.equals("0")) {
            switch (cmd) {
                case 10001:
                    sendMessage(connection, 20101, getDataMap(new Object[]{"serverId", 20180828, "accountId", AccountId,"name",name}));
                    break;
                case 20101:
                    initPlayer(map);
                    pid = getPidFromPlayer(player);
                    printMessage(getMessage("20101"));
                    if (command == null) {
                        System.out.println("请输出命令");
                        Scanner sc = new Scanner(System.in);
                        command = sc.next();
                    }
                    switch (command) {
                        case TestInstanager
                                .DIRECT_TO_DIFFICULT://创建军团 1
                            sendMessage(connection, 20028, getDataMap());
                            break;
                        case TestInstanager.RANK_GET_RANK_LIST: //排行观察
                            sendMessage(connection, 340003, getDataMap(new Object[]{"type",4 }));
                            break;
                    }
                    break;
                case 470001:
                    getMessage();
                    fightTimes ++;
                    if (fightTimes < 2) {
                        instFight(connection, /*getFirstTeamIndex()*/ 1, 31000081);
                    }else if(fightTimes < 3) {
                        instFight(connection, /*getFirstTeamIndex()*/ 1, 31000082);
                    }else if(fightTimes < 4) {
                        instFight(connection, /*getFirstTeamIndex()*/ 1, 31000091);
                    }else if(fightTimes < 5) {
                        instFight(connection, /*getFirstTeamIndex()*/ 1, 31000092);
                    }else if(fightTimes < 6) {
                        instFight(connection, /*getFirstTeamIndex()*/ 1, 31000093);
                    }

                    break;
                case 470002:
                    getMessage();
                    instFightRlt(connection, 1,/*resources.get("turns")*/GameUtil.createList());
                    break;
                case 470003:
                    getMessage();
                    if(fightTimes < dirCount)
                        sendMessage(connection, 470001, GameUtil.createSimpleMap());
                    break;
                case 20028:
                    updatePhsical(map);
                    int tidx = 1;
                    if (getTeamCount() < tidx) {
                        addTeam(connection, tidx, getHeroIds(4));
                    } else {
                        //instFight(connection, getFirstTeamIndex(), 31000081);
//                                dataMap.put("num", num);
                        Map<String, Object> data = GameUtil.createSimpleMap();
                        data.put("typ",1);
                        data.put("tIdx",1);
                        data.put("val",31000083);
                        sendMessage(connection, 460001, data);
                    }
                    break;
                case 450004:
                    break;
                case 460001: {
                    getMessage();
                    Map<String, Object> data = GameUtil.createSimpleMap();
                    data.put("rst", GameUtil.createSimpleMap());
                    data.put("fst", 1);
                    data.put("turns", GameUtil.createList());
                    sendMessage(connection, 460002, data);
                }
                case 460002:
                    getMessage();
                    break;
                case 450008: {
                    updateTeam(map);
                    Map<String, Object> data = GameUtil.createSimpleMap();
                    data.put("typ", 1);
                    data.put("tIdx", 1);
                    data.put("val", 31000083);
                    sendMessage(connection, 460001, data);
                }
                    break;
                case 340003:
                    getMessage();
                    break;
            }
        } else {
            System.err.println("[" + name + "]" + map);
            if(cmd == 470002 && errorCount <= 1) {
                errorCount++;
                sendMessage(connection, 470001, getDataMap());
            }
            if(cmd == 470002) {
                fightTimes--;
            }
        }
    }
    private int errorCount;

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

    public static void initPlayer(Map<String, Object> data) {
        for (String key : data.keySet()) {
            player.put(key, data.get(key));
        }
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

    public static void updatePhsical(Map<String, Object> data) {
        player.put("times", data.get("times"));
    }

    public static void updateTeam(Map<String, Object> data) {
        player.put("teams", data.get("teamps"));
    }

    public static Map<String, Object> getTeams() {
        if (player.containsKey("teams")) {
            return (Map<String, Object>) player.get("teams");
        }
        return GameUtil.createSimpleMap();
    }

    public static int getTeamCount() {
        return getTeams().size();
    }

    // 获取队伍
    public static Integer getFirstTeamIndex() {
        Map<String, Object> teams = getTeams();
        if (null == teams) {
            return 1;
        }
        for (String v : teams.keySet()) {
            return Integer.valueOf(v);
        }
        return 0;
    }

    // 获取英雄列表
    public static List<Object> getHeroes() {
        if (player.containsKey("heroes")) {
            return (List<Object>) player.get("heroes");
        }
        return GameUtil.createList();
    }

    // 获取英雄ID列表
    public static List<Integer> getHeroIds(int max) {
        List<Integer> ret = GameUtil.createList();
        if (player.containsKey("heroes")) {
            List<Object> heroes = getHeroes();
            for (Object obj: heroes) {
                Map<String, Object> hero = (Map<String, Object>)obj;
                ret.add((Integer)hero.get("hid"));

                if (ret.size() == max) {
                    break;
                }
            }
        }
        return ret;
    }
  /*  *//**
     * 英雄升级
     * @param connection
     *//*
    public static void sendMessage(Connection connection, int cmd, Map<String, Object>dataMap) {
        byte[] data = JSONObject.toJSONString(dataMap).getBytes(Charset.forName("UTF-8"));

        ByteBuf buf = connection.newBuffer(18 + data.length)
                .writeIntLE(14+data.length)
                .writeBoolean(false)
                .writeBoolean(false)
                .writeIntLE(lastClientMessageId.incrementAndGet())
                .writeIntLE(lastServerMessageId.get())
                .writeIntLE(cmd)
                .writeBytes(data);

        Message newMsg = new Message(buf);
        byte[] req = new byte[buf.readableBytes()];

        try {
            String body = new String(req, "UTF-8");
            System.out.println("sendMsgId:" + lastClientMessageId.get() + " to服务端发送消息{cmd:" + cmd + ",dataMap:" + dataMap + "}   buf=" + body);
        }catch(UnsupportedEncodingException e) {
            System.out.println("发送消息打印错误");
        }
        connection.sendMessage(newMsg);
    }*/

    public void changePhysical(Connection connection, int num) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("num", num);
        sendMessage(connection, 20028, dataMap);
    }

    public  void addTeam(Connection connection, int tidx, List<Integer> hids) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("teamIndex", tidx);
        for (Object hid: hids) {
            dataMap.put("heroIndex", hids.indexOf(hid));
            dataMap.put("hid", hid);
        }
        sendMessage(connection, 450008, dataMap);
    }

    public  void instFight(Connection connection, int tidx, int lcid) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("teamIndex", tidx);
        dataMap.put("lcid", lcid);
        sendMessage(connection, 470002, dataMap);
    }

    public void instFightRlt(Connection connection, int rst,Object map) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("rst", rst);
        dataMap.put("turns", map);
        dataMap.put("fst",1);
        sendMessage(connection, 470003, dataMap);
    }

    private static Map<String, Object> getEnterGameMsg(String accountId) {

            Map<String, Object> dataMap = GameUtil.createSimpleMap();
            dataMap.put("serverId", 20180828);
            dataMap.put("accountId", accountId);

            return dataMap;
    }

    private static Map<String, Object> getCreatePlayer() {
            Map<String, Object> dataMap = GameUtil.createSimpleMap();
            dataMap.put("sign", "ab769969465ed9f4ccdf6ecde0f8036d845601257091275746ee9b41dcd414c705a32b5ff18bbcc1aff12faea8bf2434990406540a2e5c5d");
            dataMap.put("serverId", "20180828");

            return dataMap;
    }

    public static void main(String[] args) {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();
        // 公司(本机)
        addressMap.put("a", new InetSocketAddress("192.168.10.160", 9999));
        // 公司(189)
//        addressMap.put("a", new InetSocketAddress("114.215.137.189", 9999));

        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());
        connector.setHandler(new MessageHandler(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1))) {
            @Override
            public void connectionOpened(Connection connection) {
                LOGGER.debug("game connection open : {}", connection.getSessionId());
                ByteBuf buf = connection.newBuffer(4 + 8);
                buf.writeIntLE(8);
                buf.writeCharSequence("20180828", Charset.forName("UTF-8"));
                // handshake
                connection.sendOriginalMessage(buf);
            }

            @Override
            public void handshakeMessage(Connection connection, Object obj) {
                LOGGER.debug("game connection handshake response : {}", obj);
            }

            @Override
            public void connectionClosed(Connection connection) {
                LOGGER.debug("game connection close : {}", connection.getSessionId());
                connector.stop(null);
            }
            private  int bytesToIntLE(byte[] bytes) {
//        return   b[3] & 0xFF |
//                (b[2] & 0xFF) << 8 |
//                (b[1] & 0xFF) << 16 |
//                (b[0] & 0xFF) << 24;
                int value=0;
                value = ((bytes[3] & 0xff)<<24)|
                        ((bytes[2] & 0xff)<<16)|
                        ((bytes[1] & 0xff)<<8)|
                        (bytes[0] & 0xff);
                return value;


            }
            @Override
            public void messageArrived(Connection connection, Message msg) {
                ByteBuf buf=msg.getContent();
                String str;

                int lcid = msg.getContent().readIntLE();

                lastServerMessageId.set(msg.getContent().readIntLE());
                int cmd = msg.getContent().readIntLE();
                int length = msg.getContent().readableBytes();

                byte[] bytes = new byte[length];
                msg.getContent().readBytes(bytes);
                str = new String(bytes, 0, length);
                System.out.println("serverMsgId:" + lastServerMessageId.get() + "  客户端%%%%%%%%%数据=================="+str);

                Map<String, Object> map = JSON.parseObject(bytes, HashMap.class, Feature.OrderedField);

                String co = (String)map.get("co");
                if (co.equals("0")) {
                    switch (cmd) {



                    }
                } else {

                }
            }

            @Override
            public void writeTimeout(Connection connection) {

            }
            /**
             * 异常捕获
             *
             * @param connection 连接
             * @param throwable  异常
             */
            public void exceptionCaught(Connection connection, Throwable throwable) {
                LOGGER.debug("exception caught : ", throwable);
                connection.close();
            }


            //

        });

        connector.start();
    }

    public static Object convertJsonStringToObj(String jsonStr) {
        Object o = null;
        if(jsonStr.charAt(0) == '{') {
            //o = JSONObject.parseObject(jsonStr);
            o = JSON.parseObject(jsonStr, HashMap.class, Feature.OrderedField);
                    System.out.println(o);
        }
        return o;
    }
    public static void getResourcesFromJsonString(String ... fileNames) {
        Map<String, Object> map = GameUtil.createMap();
        if(fileNames.length > 0 && fileNames != null) {
            for (String name : fileNames) {
                if (!StringUtil.isEmpty(name)) {
                    try {
                        InputStream in = new FileInputStream("D:\\svn\\home\\Server\\game_gateway\\src\\test\\java\\com\\cellsgame\\" + name + ".properties");
                        Properties p = new Properties();
                        p.load(in);
                        Enumeration enum1 = p.propertyNames();//得到配置文件的名字
                        while (enum1.hasMoreElements()) {
                            String strKey = (String) enum1.nextElement();
                            String strValue = p.getProperty(strKey);
                            Object obj = convertJsonStringToObj(strValue);
                            resources.putAll((Map)obj);
              //             obj.entrySet().forEach(x -> resources.put(x.getKey(), x.getValue()));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
