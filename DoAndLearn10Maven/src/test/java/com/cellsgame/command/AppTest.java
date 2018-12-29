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
        Enter enter = new CmdEnter(appTest);
        appTest.enter = enter;
        System.out.println("-----测试开始  cmd 模式");
        connector.start();
    }

    @Override
    public String getAccount() {
        return null;
    }

    @Override
    public void onReceiveMessage(Connection connection, int cmd, Map map) throws InterruptedException {
      enter.start(connection,cmd,map);
//        sc.close();
    }
}
