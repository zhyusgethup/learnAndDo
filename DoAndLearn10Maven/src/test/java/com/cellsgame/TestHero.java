package com.cellsgame;

import com.cellsgame.common.util.GameUtil;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.core.Connector;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;
import com.cellsgame.gateway.message.processor.MessageProcessor;
import com.google.common.collect.Maps;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by Yang on 16/6/16.
 */
public class TestHero extends HuyuMessageHandler {
    private static String ServerUrl = "192.168.10.117";
    private static Integer ServerPort = 9999;

    private static String AccountId = "030c4330-8b54-1111-86d5-00017";

    private static Map<String, Object> player = GameUtil.createSimpleMap();

    /**
     * 构建消息处理器。
     *
     * @param service 收到消息之后,将消息分发到线程池的服务
     */
    public TestHero(MessageProcessor service) {
        super(service);
    }

    @Override
    public String getAccount() {
        return null;
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
    // 获取英雄
    public static Object getFirstHero() {
        List<Object> heroes = getHeroes();
        if (heroes.size() > 0) {
            return heroes.get(0);
        }
        return null;
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


    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) {
        String co = (String)map.get("co");
        if (co.equals("0")) {
            switch (cmd) {
                case 10001:
                    sendMessage(connection, 20099, getEnterGameMsg(AccountId));
                    break;
                case 20099:
                    initPlayer(map);
                    heroLvup(connection, 20);
                    break;
                case 20028:
                    break;
                case 450004:
                    break;
            }
        } else {

        }
    }

    public void heroLvup(Connection connection, int nlvl) {
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

    public void heroStarup(Connection connection) {
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

    private Map<String, Object> getHeroLvMsg(){
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
//            dataMap.put("sign", "ab769969465ed9f4ccdf6ecde0f8036d845601257091275746ee9b41dcd414c705a32b5ff18bbcc1aff12faea8bf2434990406540a2e5c5d");
        dataMap.put("hid", 20122201);
        Map<Integer, Integer> goodsMap = GameUtil.createSimpleMap();;
        goodsMap.put(1,1);
        goodsMap.put(1,2);
        dataMap.put("goods", goodsMap);

        return dataMap;
    }
     private Map<String,Object> getSummonInMsg() {
        Map<String, Object> dataMap = GameUtil.createSimpleMap();
        dataMap.put("poolid",1);
        return dataMap;
     }

    private Map<String, Object> getEnterGameMsg(String accountId) {

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
        connector.setHandler(new TestHero(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1))));


        connector.start();
    }
}
