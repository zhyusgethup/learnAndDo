package test;

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

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yang on 16/6/16.
 */
public class GameClient_new {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameClient_new.class);
    private static AtomicInteger lastClientMessageId = new AtomicInteger(0);
    private static AtomicInteger lastServerMessageId = new AtomicInteger(0);
    private static String AccountId = "030c4330-8b54-1340-86d6-4198c7a935f9";
    private static final String HOST = "192.168.10.160";
    private static final int PORT = 9999;
    public static void main(String[] args) {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();
        addressMap.put("a", new InetSocketAddress(HOST, PORT));
        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());
        connector.setHandler(new MessageHandler(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1))) {

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
                System.out.println("serverMsgId=" + lastServerMessageId.get() + "客户端%%%%%%%%%数据=====cmd :" + cmd + "str :" +str);

                Map<String, Object> map = JSON.parseObject(bytes, HashMap.class, Feature.OrderedField);

                String co = (String)map.get("co");
                if (co.equals("0")) {
                    switch (cmd) {
                        case 10001://系统时间
                            sendMessage(connection, 20099, getEnterGameMsg(AccountId));
                            break;
                        case 20099://创建玩家专业账号
                            initPlayer(map);
                            //  sendMessage(connection, 450001,getSummonInMsg());
                            heroLvup(connection, 10);//450004英雄升级
//
//                            heroStarup(connection);
//                            int tidx = 1;
//                            if (getTeamCount() < tidx) {
//                                addTeam(connection, tidx, getHeroIds(4));//450008:英雄编队
//                            } else {// 走的这儿 默认队伍大于1人;发送cmd 470002 : 副本战斗
//                                instFight(connection, 1/*getFirstTeamIndex()*/, 31000081);
//                            }
//                            changePhysical(connection, 90);//20028 :修改玩家体力
                            break;
                        case 20028: //修改玩家体力
                            updatePhsical(map);
                            break;
                        case 450004://英雄升级

                            break;
                        case 450008://英雄编队
                            updateTeam(map);
                            instFight(connection,/* getFirstTeamIndex()*/ 1, 31000081);//470002
                            break;
                        case 470002://副本战斗
                            // 这里发送的时47003
                            instFightRlt(connection, 1);
                            break;
                        case 470003://战斗结果
                            if (fightTimes ++ < 2) {
                                // instFight(connection, getFirstTeamIndex(), 31000081);
                                instFight(connection,/* getFirstTeamIndex()*/1, 31000081);//470002
                            }
                            break;
                        case 450001:
                            List<Integer> heroIds  = (List<Integer>) map.get("heroes");
                            int heroId = heroIds.get(0);
                            int index = 0;
                            sendMessage(connection,450002,getSummonMsg(heroId,index));
                            break;
                        case 450002:
                            sendMessage(connection,450003,GameUtil.createSimpleMap());
                            break;
                    }
                } else {

                }
            }

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
    }


    public static Map<String,Object> getSummonMsg(int heroId, int index) {
        Map<String, Object> data = GameUtil.createSimpleMap();
        data.put("cid",heroId);
        data.put("idx",index);
        return data;
    }

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
        dataMap.put("rst", rst);//rst = 1 战斗结算发放道具
        dataMap.put("turns", GameUtil.createSimpleMap());//战斗复盘相关
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
    private static Map<String,Object> getSummonInMsg() {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("poolid",1);
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

}
