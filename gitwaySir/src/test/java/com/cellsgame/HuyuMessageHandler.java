package com.cellsgame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.cellsgame.gateway.core.Connection;
import com.cellsgame.gateway.message.Message;
import com.cellsgame.gateway.message.MessageHandler;
import com.cellsgame.gateway.message.processor.MessageProcessor;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class HuyuMessageHandler extends MessageHandler {
    private AtomicInteger lastClientMessageId = new AtomicInteger(0);
    private AtomicInteger lastServerMessageId = new AtomicInteger(0);

    protected  Integer pid;
    private static final Logger LOGGER = LoggerFactory.getLogger(HuyuMessageHandler.class);
    /**
     * 构建消息处理器。
     *
     * @param service 收到消息之后,将消息分发到线程池的服务
     */
    public HuyuMessageHandler(MessageProcessor service) {
        super(service);
    }

    @Override
    public void handshakeMessage(Connection connection, Object msg) {
        LOGGER.debug("game connection handshake response : {}", msg);
    }

    public abstract String getAccount();
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
    public void connectionClosed(Connection connection) {
        LOGGER.debug("game connection close : {}", connection.getSessionId());
        connection.close();
    }
    protected String getMessage(String cmd){return null;}
    protected Integer getPidFromPlayer(Map<String, Object> player) {
        JSONObject jsonO = (JSONObject) player.get("pInfo");
        return jsonO.getInteger("pid");
    }
    protected Map<String, Object> data;
    @Override
    public void messageArrived(Connection conn, Message msg) throws InterruptedException {
        ByteBuf buf=msg.getContent();
        String str;

        int lcid = msg.getContent().readIntLE();

        lastServerMessageId.set(msg.getContent().readIntLE());
        int cmd = msg.getContent().readIntLE();
        int length = msg.getContent().readableBytes();

        byte[] bytes = new byte[length];
        msg.getContent().readBytes(bytes);
//        str = new String(bytes, 0, length);
//        String ppid = pid == null?"":"[" + pid + "]";
        str = new String(bytes, 0, length);
//        System.out.println("收到信息[" + getAccount() + "]" + ppid + "====="+str);

        data = JSON.parseObject(bytes, HashMap.class, Feature.OrderedField);

        onReceiveMessage(conn, cmd, data);
    }

    protected void printMessage(String msg) {
        String ppid = pid == null?"":"[" + pid + "]";
        System.out.println("收到信息[" + getAccount() + "]" + ppid + ""+msg);
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

    public void sendMessage(Connection connection, int cmd, Map<String, Object>dataMap) {
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
            System.out.println("[" + getAccount() + "]" + lastClientMessageId.get() + "发送信息:{cmd:" + cmd + ",dataMap:" + dataMap + "}   buf=" + body);
        }catch(UnsupportedEncodingException e) {
            System.out.println("发送消息打印错误");
        }

        connection.sendMessage(newMsg);
    }

    public abstract void onReceiveMessage(Connection connection, int cmd, Map map) throws InterruptedException;
}
