package com.cellsgame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.cellsgame.common.util.GameUtil;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.core.Connector;
import com.cellsgame.gateway.message.Message;
import com.cellsgame.gateway.message.MessageHandler;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cellsgame.gateway.utils.Utils.sleep;

/**
 * Created by Yang on 16/6/16.
 */
public class GameClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameClient.class);
    private static AtomicInteger lastClientMessageId = new AtomicInteger(0);
    private static AtomicInteger lastServerMessageId = new AtomicInteger(0);

    private static String AccountId = "030c4330-8b54-4444-86d5-4198c7a935f9";

    private static Map<String, Object> player = GameUtil.createSimpleMap();

    private static Integer fightTimes = 0;
    public static void initPlayer(Map<String, Object> data) {
        for (String key : data.keySet()) {
            player.put(key, data.get(key));
        }
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
            return 0;
        }
        for (String v : teams.keySet()) {
            return Integer.valueOf(v);
        }
        return 0;
    }

    public static Object getTeam(int tidx) {
        Map<String, Object> teams = getTeams();
        return teams.get(String.valueOf(tidx));
    }

    // 获取英雄列表
    public static List<Object> getHeroes() {
        if (player.containsKey("heroes")) {
            return (List<Object>) player.get("heroes");
        }
        return GameUtil.createList();
    }
    // 获取英雄
    public static Object getFirstHero() {
        List<Object> heroes = getHeroes();
        if (heroes.size() > 0) {
            return heroes.get(0);
        }
        return null;
    }

    // 获取道具列表
    public static List<Object> getGoods() {
        if (player.containsKey("goods")) {
            return (List<Object>) player.get("goods");
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
    // 获取道具ID列表
    public static List<Integer> getGoodsIds() {
        List<Integer> ret = GameUtil.createList();
        if (player.containsKey("goods")) {
            List<Map<String, Object>> goodses = (List<Map<String, Object>>)player.get("goods");
            for (Map<String, Object> good: goodses) {
                ret.add((Integer)good.get("id"));
            }
        }
        return ret;
    }

    /**
     * 英雄升级
     * @param connection
     */
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

        connection.sendMessage(newMsg);
    }

    public static void changePhysical(Connection connection, int num) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("num", num);
        sendMessage(connection, 20028, dataMap);
    }

    public static void heroLvup(Connection connection, int nlvl) {
        Map<String, Object> hero = (Map<String, Object>)getFirstHero();
        if (null != hero) {
            Integer hid = (Integer)hero.get("hid");
            Integer cid = (Integer)hero.get("cid");


            Map<String, Object> dataMap = GameUtil.createSimpleMap();
            dataMap.put("hid", hid);
            dataMap.put("nlvl", nlvl);
            sendMessage(connection, 450004, dataMap);
        }
    }

    public static void heroStarup(Connection connection) {
        Map<String, Object> hero = (Map<String, Object>)getFirstHero();
        if (null != hero) {
            Integer hid = (Integer)hero.get("hid");
            Integer cid = (Integer)hero.get("cid");


            Map<String, Object> dataMap = GameUtil.createSimpleMap();
            dataMap.put("hid", hid);
            dataMap.put("goods", GameUtil.createSimpleMap());
            sendMessage(connection, 450005, dataMap);
        }
    }


    public static void addTeam(Connection connection, int tidx, List<Integer> hids) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("teamIndex", tidx);
        for (Object hid: hids) {
            dataMap.put("heroIndex", hids.indexOf(hid));
            dataMap.put("hid", hid);
        }
        sendMessage(connection, 450008, dataMap);
    }

    public static void instFight(Connection connection, int tidx, int lcid) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("teamIndex", tidx);
        dataMap.put("lcid", lcid);
        sendMessage(connection, 470002, dataMap);
    }

    public static void instFightRlt(Connection connection, int rst) {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("rst", rst);
        dataMap.put("turns", GameUtil.createSimpleMap());
        sendMessage(connection, 470003, dataMap);
    }

    private static Map<String, Object> getHeroLvMsg(){
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
//            dataMap.put("sign", "ab769969465ed9f4ccdf6ecde0f8036d845601257091275746ee9b41dcd414c705a32b5ff18bbcc1aff12faea8bf2434990406540a2e5c5d");
        dataMap.put("hid", 20122201);
        Map<Integer, Integer> goodsMap = GameUtil.createSimpleMap();;
        goodsMap.put(1,1);
        goodsMap.put(1,2);
        dataMap.put("goods", goodsMap);

        return dataMap;
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
//        // 家 192.168.199.245
//        addressMap.put("a", new InetSocketAddress("192.168.199.245", 9999));
        // 公司(本机)
        addressMap.put("a", new InetSocketAddress("192.168.10.117", 9999));
//        // 公司(165)
//        addressMap.put("a", new InetSocketAddress("192.168.10.165", 9999));
        // 公司(189)
//        addressMap.put("a", new InetSocketAddress("114.215.137.189", 9999));

        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new CMessageCodecFactory());
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
                System.out.println("客户端%%%%%%%%%数据=================="+str);

                Map<String, Object> map = JSON.parseObject(bytes, HashMap.class, Feature.OrderedField);

                String co = (String)map.get("co");
                if (co.equals("0")) {
                    switch (cmd) {
                        case 10001:
                            sendMessage(connection, 20099, getEnterGameMsg(AccountId));
                            break;
                        case 20099:
                            initPlayer(map);

//                            heroLvup(connection, 10);

//                            heroStarup(connection);
                            int tidx = 1;
                            if (getTeamCount() < tidx) {
                                addTeam(connection, tidx, getHeroIds(4));
                            } else {
                                instFight(connection, getFirstTeamIndex(), 31000081);
                            }
                            changePhysical(connection, 90);
                            break;
                        case 20028:
                            updatePhsical(map);
                            break;
                        case 450004:
                            break;
                        case 450008:
                            updateTeam(map);
                            instFight(connection, getFirstTeamIndex(), 31000081);
                            break;
                        case 470002:
                            instFightRlt(connection, 1);
                            break;
                        case 470003:
                            if (fightTimes ++ < 2) {
                                instFight(connection, getFirstTeamIndex(), 31000081);
                            }
                            break;
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

}
