package com.cellsgame.command;

import com.cellsgame.HuyuMessageHandler;
//import com.cellsgame.command.enter.CmdEnter;
import com.cellsgame.command.enter.CmdEnter;
import com.cellsgame.command.enter.Enter;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.core.Connector;
import com.cellsgame.gateway.message.processor.JavaThreadMessageProcessor;
import com.cellsgame.gateway.message.processor.MessageProcessor;
import com.google.common.collect.Maps;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class AppTest extends HuyuMessageHandler {

    //    private BlockingQueue<AccessInterface> queue;
//    private CmdEnter enter;
    private Enter enter;
    public AppTest(MessageProcessor service) {
        super(service);
//        queue = new LinkedBlockingQueue<>(1);
//        enter = new CmdEnter();
    }
    private Connection c;
    public static void main(String[] args) throws InterruptedException {
        Map<String, InetSocketAddress> addressMap = Maps.newHashMap();
        String ServerUrl = "192.168.10.160";
        int port = 9999;
        addressMap.put("a", new InetSocketAddress(ServerUrl, port));
        final Connector connector = new Connector("game client connector", addressMap);
        connector.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        connector.setCodecFactory(new com.cellsgame.CMessageCodecFactory());
        AppTest appTest = new AppTest(new JavaThreadMessageProcessor(Executors.newFixedThreadPool(1)));
        connector.setHandler(appTest);
        final Enter enter = new CmdEnter(appTest);
        appTest.enter = enter;
        System.out.println("-----测试开始  cmd 模式");
        connector.start();
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    while (null == appTest.getC()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    enter.start(appTest.getC(), appTest.getCmd(), appTest.getMap());
                }
            }
        });
        sendThread.start();
    }

    @Override
    public String getAccount() {
        return null;
    }
    private int cmd;
    private Map map;

    public Connection getC() {
        return c;
    }

    public int getCmd() {
        return cmd;
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) throws InterruptedException {
        System.out.println(map);
        c = connection;
        this.cmd = cmd;
        this.map = map;
//      enter.start(connection,cmd,map);
//        sc.close();
    }
}
