package com.master.gm.elaGener.log.ela;

import com.alibaba.fastjson.JSONObject;
import com.master.gm.elaGener.log.ela.bean.GuildMemeberLog;
import com.master.gm.elaGener.oop.Guild;
import com.master.gm.elaGener.oop.Server;
import com.master.gm.elaGener.oop.User;
import com.master.gm.elaGener.oop.action.*;
import com.master.gm.elaGener.utils.TransportClientFactory;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
public class Test {

    public static void testActions(){
        Server server = new Server("2003");
        User user = new User("acc04");
        server.setSer_cre_time(1551888000000L);
        new CreAction(new CreAction.StandCreAdjust()).next(new LoginAction(new LoginAction.StaLoginAdjuster())).
                next(new GoodAddAction(new GoodAddAction.StaGoodAddAdjuster(100001,500))).
                next(new GoodMinusAction(new GoodMinusAction.StaGoodMinusAdjuster(100001,400))).
                next(new GuildCountAction(new GuildCountAction.StaGuildCountAdjustor(30))).
                next(new GuildDonateAction(new GuildDonateAction.StaGuildDonateAdjustor(100))).
                next(new GuildMemberAction(new GuildMemberAction.StaGuildMemberAdjustor(100))).
                next(new GuildFightAction(new GuildFightAction.StaGuildFightAdjustor(100,"牛头山"))).
                next(new LoginOutAction(new LoginOutAction.StaLoginoutAdjuster()))
                .end()
                .action(user,server);
        System.out.println(server);
        System.out.println(user);
    }


    public static TransportClient get(){
        try {
            return TransportClientFactory.getInstance().getClient();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
    public static void main(String[] args) throws Exception {
//        insertByLogBean();
//        deleteIndex("remove");
        testActions();

    }



    public static void deleteIndex(String name){
        TransportClient transportClient = get();

        AcknowledgedResponse acknowledgedResponse = transportClient.admin()
                .indices().prepareDelete(new String[]{name}).execute().actionGet();
        System.out.println(acknowledgedResponse);

    }
    public static void mapCountNestMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("user1", "小明");
        map.put("title2", "Java Engineer");
        map.put("desc3", "web 开发");
        map.put("desc3", 1);
        map.put("desc3", 2);
        map.put("desc3", Arrays.asList("213","213"));
        Map sub = new HashMap();
        sub.put("123","123");
//        map.put("desc3", sub);
        TransportClient client = get();
        IndexResponse response = client.prepareIndex("remove", "remove").setSource(map, XContentType.JSON).get();

        String _index = response.getIndex();
        System.out.println(_index);
    }
    public static void insertByLogBean(){
//        GuildMemeberLog log = new GuildMemeberLog("2006",180);
//        JSONObject data = log.getData();
//        System.out.println(data);
        TransportClient client = get();
//        IndexResponse response = client.prepareIndex("remove", "remove").setSource(data, XContentType.JSON).get();

//        String _index = response.getIndex();
//        System.out.println(_index);
    }
    public static void insertByFastJson(){
        JSONObject json = new JSONObject();
        json.put("user", "小明");
        json.put("title", "Java Engineer");
        json.put("desc", "web 开发");
        JSONObject sub = new JSONObject();
        sub.put("dsd",123);
        json.put("sub",sub);
        TransportClient client = get();
        IndexResponse response = client.prepareIndex("remove", "remove").setSource(json, XContentType.JSON).get();

        String _index = response.getIndex();
        System.out.println(_index);
    }
    public static void insertByXContent() throws Exception{
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("first_name", "gou gou")
                .field("last_name","smith")
                .field("about", "trying out Elasticsearch")
                .field("interests","music,act")
                .endObject();
        TransportClient client = TransportClientFactory.getInstance().getClient();
        IndexResponse response=client.prepareIndex("remove","remove").setSource(builder).get();
        System.out.println(response);
        client.close();
    }
}
