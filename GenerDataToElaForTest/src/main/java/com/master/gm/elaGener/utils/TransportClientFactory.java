package com.master.gm.elaGener.utils;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.Instant;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TransportClientFactory {

    private TransportClientFactory() {
    }

    private static class Inner {
        private static final TransportClientFactory instance = new TransportClientFactory();
    }

    private TransportClient client;

    public static TransportClientFactory getInstance() {
        return Inner.instance;
    }

    public TransportClient getClient() throws Exception{
        if(null == client)
            getInstance().initClient();
        return client;
    }

    public void initClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "my-application")    // 默认的集群名称是elasticsearch，如果不是要指定
                .build();
        TransportClient localhost = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        client = localhost;
    }

}