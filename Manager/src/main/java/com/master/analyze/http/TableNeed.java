package com.master.analyze.http;

import com.alibaba.fastjson.JSON;
import com.master.analyze.cache.GameServerCache;
import com.master.util.HttpUtil;
import com.master.util.TransportClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketCollector;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.BucketMetricValue;

import java.io.IOException;
import java.util.*;

public class TableNeed {
    public static final String LOGIN_SERVER_IP = "127.0.0.1:9080";
    public static final String PAY_SERVER_IP = "http://127.0.0.1:8012/";
    public static final String PAY_ACCOUNT_COUNT_URL = PAY_SERVER_IP + "ueryState/getPayCount";
    public static final String PAY_ARPPU_URL = PAY_SERVER_IP + "queryState/getARPPU";
    public static final String PAY_AMOUNT_URL = PAY_SERVER_IP + "queryState/getPayAmount";
    public static final String Q_REGISTER_COUNT_URL = "http://" + LOGIN_SERVER_IP + "/account/getRegisterAccountCountByTimes";
    public static void main(String[] args) throws Exception{
//        System.out.println(getCountByTime(Q_REGISTER_COUNT_URL,1541660400000L,1541664000000L));
//        System.out.println(getCountByTime(PAY_ACCOUNT_COUNT_URL,1546315200000L,1546318800000L));
//        System.out.println(getCountByTime(PAY_AMOUNT_URL,1546315200000L,1546318800000L));
//        System.out.println(getCountByTime(PAY_ARPPU_URL,1546272000000L, 1548950400000L));
        System.out.println(getInstanceCid("inst"));
    }

    public static String getGameOnLineTime(){
        return "2019-03-02 12:00:00";
    }

    public static List<Integer> getInstanceCid(String param) {
        Map<String,String> pa = new HashMap<>();
        pa.put("param",param);
        String str = HttpUtil.httpPost(HttpPropertiesLoader.Q_INSTATNCE_CIDS_URL, pa);
        List<Integer> list = null;
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            list = (List<Integer>) hashMap.get("ret");

        }
        return list;
    }

    public static String getCountByTime(String url,long start, long end) {
        Map<String, String> map = new HashMap<>();
        map.put("start",String.valueOf(start));
        map.put("end",String.valueOf(end));
        String str = HttpUtil.httpPost(url, map);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            if(hashMap.containsKey("re")) {
                return hashMap.get("re").toString();
            }
        }
        return "0";
    }
    public static double getDoubleCountByTime(String url,long start, long end) {
        Map<String, String> map = new HashMap<>();
        map.put("start",String.valueOf(start));
        map.put("end",String.valueOf(end));
        String str = HttpUtil.httpPost(url, map);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            if(hashMap.containsKey("re")) {
                return Double.valueOf(hashMap.get("re").toString());
            }
        }
        return 0;
    }

    public static Map<String, String> getServersFromServerConf() {
        Map<String,String> serverIds = new HashMap<>();
        String str = HttpUtil.httpPost(HttpPropertiesLoader.SERVER_LIST_URL, null);
        if(null != str) {
            HashMap hashMap = JSON.parseObject(str, HashMap.class);
            List<Map<String,String>> game = (List<Map<String, String>>) hashMap.get("game");
            for (int i = 0; i < game.size(); i++) {
                Map<String, String> stringStringMap = game.get(i);
                String name = stringStringMap.get("serverName");
                String id = stringStringMap.get("serverID");
                serverIds.put(name, id);
            }
        }
        return serverIds;
    }
    private static Set<String> getServersFromEla(String date1, int hour1) throws IOException {
        Set<String> serverIds = new HashSet<>();
        TransportClient client = TransportClientFactory.getInstance().getClient();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder date = QueryBuilders.matchQuery("date", date1);
        boolQueryBuilder.must(date);
        MatchQueryBuilder hour = QueryBuilders.matchQuery("hour", hour1);
        boolQueryBuilder.must(hour);
        TermsAggregationBuilder agg = AggregationBuilders.terms("server_counts").field("server.keyword");
        SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).addAggregation(agg).execute().actionGet();
        Terms server_counts = searchResponse.getAggregations().get("server_counts");
        List<? extends Terms.Bucket> buckets = server_counts.getBuckets();
        for (int i = 0; i < buckets.size(); i++) {
            Object key = buckets.get(i).getKey();
            System.out.println(key);
            serverIds.add(key.toString());
        }
        return serverIds;
    }
}
