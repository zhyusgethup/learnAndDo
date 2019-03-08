package com.master.gm.elaGener.utils;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

public class ElUtil {
    public static TransportClient get(){
        try {
            return TransportClientFactory.getInstance().getClient();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
    public static void sendMsgToEl(JSONObject obj) {
        TransportClient client = get();
        IndexResponse response = client.prepareIndex("remove", "remove").setSource(obj, XContentType.JSON).get();
        System.out.println(response);
    }
}
