package com.master.gm.service.analyze.impl;

import com.master.analyze.analyeBean.*;
import com.master.analyze.cache.ServiceCache;
import com.master.gm.service.analyze.DBService;
import com.master.gm.service.analyze.GoodsComputeService;
import com.master.gm.service.analyze.LoginLogoutService;
import com.master.util.MathUtil;
import com.master.util.TimeUtil;
import com.master.util.TransportClientFactory;
import lombok.Getter;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.InternalSimpleValue;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.avg.AvgBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.stats.InternalStatsBucket;
import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.stats.StatsBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.sum.SumBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketselector.BucketSelectorPipelineAggregationBuilder;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@IocBean
public class LoginLogoutServiceImpl implements LoginLogoutService {
    //    private static LoginLogoutServiceImpl instance;
    private Logger log = LoggerFactory.getLogger(LoginLogoutServiceImpl.class);
    @Inject
    @Getter
    private GoodsComputeService goodsComputeService;
    @Inject
    @Getter
    private DBService dBService;

    /*    public static LoginLogoutServiceImpl getInstance(){
            if(null == instance){
                return new LoginLogoutServiceImpl();
            }
            return instance;
        }*/
//    public LoginLogoutServiceImpl(){
////        goodsComputeService =GoodsComputeService.getInstance();
//        if(null != instance){
//        init();
//    }
//}
//    public void init(){
//        instance = this;
//    }
    public static void main(String[] args) throws IOException {
        LoginLogoutServiceImpl s = new LoginLogoutServiceImpl();
//        getActivePlayerCountSimple();
//        getAvgPlayersOnlineTimeSimple()msg["uid"].toString();
//        getOnlineCountByMinuteSimple(3);
//        String s = ComputerTableBean.convertThree(323.344214325);
//        System.out.println(s);
//        Map<String, String> avgOnlineTimeByHourDate = new LoginLogoutServiceImpl().getAvgOnlineTimeByHourDate(1548950400000L, 1549036800000L, 11, new HashMap<>());
//        System.out.println(avgOnlineTimeByHourDate);
//            getActiveAccountCountByTimeAndServer(1549382400000L,1549987200000L,1);
//        Map<String, String> newRegesterAccountByTime = new LoginLogoutServiceImpl().getNewRegesterAccountByTime(1551628800000L, 1551715200000L, -1);
//        System.out.println(newRegesterAccountByTime);
        System.out.println(s.getRetentionByTwoMinusValuesIgloreServer(0, 1));

//        getRetentionByTwoMinusValues(0,2);
//        getPonitLeaveTotalOrTargetByServerDatePoint("1","2019-01-30",1,0);
//        Map<String, String> u_type = getUserParticipation2(1548777600000L, 1548864000000L, 11, "u_type", 1);
//        System.out.println(u_type);
//        Map<String, String> guildBuild = getGuildBuild(1548777600000L, 1548864000000L);
//        System.out.println(guildBuild);
//        Map<String, String> guildCount = getGuildCount(1548777600000L, 1548864000000L,"g_count");
//        System.out.println(guildCount);
//        Map<String, String> guildActUserRatio = getGuildActUserRatio(1549382400000L, 1549987200000L, 1548777600000L, 1548864000000L);
//        System.out.println(guildActUserRatio);

//        getAcitivityUserJoinCountInfo(1548777600000L, 1548864000000L, TableNeed.getInstanceCid("act"));
//        System.out.println(userParticipation);
//        getSumByServerDateLogTypeParam("1", "2019-01-30",13,"g_type",4,"build");

//        System.out.println(getOnlinePlayerCountByTimeTime(1548950400000L,1549036800000L,0L,60000L));
//        Map<String, Map<String, Map<Integer, Long>>> map = new HashMap<>();
//         getInfoForBreakPoint(1548777600000L, 1548864000000L, 0,map);
//        System.out.println(map);
//        getNewPlayerBreakPointInfo(1548777600000L, 1548864000000L);
//        getInstBreakPointInfo(TableNeed.getInstanceCid(),1548777600000L, 1548864000000L);
//        getGoodsInfo(1548777600000L,1548864000000L);
//        System.out.println(goodsCidTotalByServerDate);
    }


    public Map<String, String> getGoodsInfo(long start, long end) {
        getGoodsCidTotalByServerDate(start, end);
        //获取计算表中的数据
        List<CGoodsBean> beans = goodsComputeService.getBeans(start - TimeUtil.DAY_MIL, end - TimeUtil.DAY_MIL);
        if (beans.size() == 0) {
            //如果mysql中没有历史数据,那么对bean中的历史数据进行替换处理,并且保存历史数据到mysql
            goodsComputeService.replaceAndSave(start, end);
        } else {
            //如果有那么,合并且更新mysql中历史数据
            goodsComputeService.combineAndUpdate(beans, start, end);
        }
        Map<String, String> data = new HashMap<>();
        //这里在查询数据为空时不会生成和替换可变列
        goodsComputeService.generHeadAndData(data);
//        System.out.println(data);
        //清理资源
        ServiceCache.clear();
        return data;
    }

    /***
     * use
     * 获取道具的增加量和消耗量,isAdded=true是增加
     * @return
     */
    public void getGoodsCidTotalByServerDate(long start, long end) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("logType", 17));
            boolQueryBuilder.must(QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end));

            SumAggregationBuilder add_a = AggregationBuilders.sum("add").field("jsonData.add");
            SumAggregationBuilder minus_a = AggregationBuilders.sum("minus").field("jsonData.minus");
            TermsAggregationBuilder acc_a = AggregationBuilders.terms("acc").field("jsonData.acc.keyword").size(20000).subAggregation(add_a).subAggregation(minus_a);
            SumBucketPipelineAggregationBuilder minuses_a = PipelineAggregatorBuilders.sumBucket("minuses", "acc>minus");
            SumBucketPipelineAggregationBuilder addes_a = PipelineAggregatorBuilders.sumBucket("addes", "acc>add");
            TermsAggregationBuilder cid_a = AggregationBuilders.terms("cid").field("jsonData.cid").size(20000).
                    subAggregation(acc_a).subAggregation(minuses_a).subAggregation(addes_a);
//            SumBucketPipelineAggregationBuilder total_minus_a = PipelineAggregatorBuilders.sumBucket("total_minus", "cid>minuses");
//            SumBucketPipelineAggregationBuilder total_add_a = PipelineAggregatorBuilders.sumBucket("total_add", "cid>addes");
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").size(200).
                    subAggregation(cid_a)/*.subAggregation(total_add_a).subAggregation(total_minus_a)*/;

            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(server_a).execute().actionGet();
            System.out.println(searchResponse);//TODO 待会这里要处理下
            StringTerms server_r = searchResponse.getAggregations().get("server");
            List<StringTerms.Bucket> buckets_server = server_r.getBuckets();

            for (int i = 0; i < buckets_server.size(); i++) {
                StringTerms.Bucket bucket_server = buckets_server.get(i);
                String serverName = bucket_server.getKeyAsString();
//                GoodServerBean serverBean = new GoodServerBean(serverName,start,end);
//                InternalSimpleValue total_add_r = bucket_server.getAggregations().get("total_add");
//                InternalSimpleValue total_minus_r = bucket_server.getAggregations().get("total_minus");
//                serverBean.setProduct(total_add_r.getValue());
//                serverBean.setCost(total_minus_r.getValue());
//                ServiceCache.goodServerBeans.put(serverName,serverBean);
                LongTerms cid_r = bucket_server.getAggregations().get("cid");
                List<LongTerms.Bucket> buckets_cid = cid_r.getBuckets();
                Map<Integer, GoodCidBean> cidBeans = ServiceCache.cidBeans;
                Set<Integer> cids = ServiceCache.cids;
                for (int j = 0; j < buckets_cid.size(); j++) {
                    LongTerms.Bucket bucket_cid = buckets_cid.get(i);
                    int cid = bucket_cid.getKeyAsNumber().intValue();
                    GoodCidBean cidBean = new GoodCidBean(cid);
                    cidBeans.put(cid, cidBean);
                    cids.add(cid);
//                    serverBean.addCidBean(cidBean);
                    InternalSimpleValue addes_r = bucket_cid.getAggregations().get("addes");
                    InternalSimpleValue minuses_r = bucket_cid.getAggregations().get("minuses");

                    cidBean.getTopData().put(serverName, GoodCidBean.PRODUCT, addes_r.getValue());
//                    cidBean.setProduct(addes_r.getValue());
//                    cidBean.setCost(minuses_r.getValue());
                    cidBean.getTopData().put(serverName, GoodCidBean.COST, minuses_r.getValue());
//                    cidBean.setServer(serverName);
                    Aggregation acc1 = searchResponse.getAggregations().get("acc");
                    if (!(acc1 instanceof StringTerms))
                        return;
                    StringTerms acc_r = (StringTerms) acc1;
                    List<StringTerms.Bucket> buckets_acc = acc_r.getBuckets();
                    for (int k = 0; k < buckets_acc.size(); k++) {
                        StringTerms.Bucket bucket_acc = buckets_acc.get(k);
                        InternalSum add_r = bucket_acc.getAggregations().get("add");
                        InternalSum minus_r = bucket_acc.getAggregations().get("minus");
                        String acc = bucket_acc.getKeyAsString();
                        GoodUserBean userBean = new GoodUserBean(acc);
                        userBean.setCost(minus_r.getValue());
                        userBean.setProduct(add_r.getValue());
                        userBean.setServerName(serverName);
                        userBean.setCid(cid);
//                        cidBean.addUser(userBean);
                        cidBean.getBottomData().put(serverName, acc, userBean);
                    }
                }
            }
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取建设度
     * @param start
     * @param end
     * @return
     */
    public Map<String, String> getGuildBuild(long start, long end) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 13);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder range_q = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.must(range_q);
            TermQueryBuilder param_q = QueryBuilders.termQuery("jsonData.g_type", 4);
            boolQueryBuilder.must(param_q);

            SumAggregationBuilder sum_a = AggregationBuilders.sum("sum").field("jsonData.build");
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").subAggregation(sum_a);
            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(server_a).execute().actionGet();
            StringTerms terms = searchResponse.getAggregations().get("server");
            List<StringTerms.Bucket> buckets = terms.getBuckets();
            Map<String, String> data = new HashMap<>();
            double total = 0;
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                InternalSum sum = bucket.getAggregations().get("sum");
                double docCount = sum.getValue();
                total += docCount;
                data.put(bucket.getKeyAsString(), MathUtil.saveTwoDecimals(docCount));
            }
            data.put(Row.TOTAL_COUNT, MathUtil.saveTwoDecimals(total));
            return data;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 获取公会数量 或 总人数
     * @param start
     * @param end
     * @return
     */
    public Map<String, String> getGuildCount(long start, long end, String param) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 13);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder range_q = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.must(range_q);
            TermQueryBuilder param_q = QueryBuilders.termQuery("jsonData.g_type", 5);
            boolQueryBuilder.must(param_q);

            MaxAggregationBuilder max_a = AggregationBuilders.max("max").field("jsonData." + param);
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").subAggregation(max_a);
            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(server_a).execute().actionGet();
            Aggregation server = searchResponse.getAggregations().get("server");
            if (!(server instanceof StringTerms))
                return null;
            StringTerms server_r = (StringTerms) server;
            List<StringTerms.Bucket> buckets = server_r.getBuckets();
            Map<String, String> data = new HashMap<>();
            double total = 0;
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                InternalMax max = bucket.getAggregations().get("max");
                double docCount = max.getValue();
                total += docCount;
                data.put(bucket.getKeyAsString(), MathUtil.saveTwoDecimals(docCount));
            }
            data.put(Row.TOTAL_COUNT, MathUtil.saveTwoDecimals(total));
            return data;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 返回每个用户参与项目,角色养成的值 注意,如果值是0,那么不会存在key
     * (11 u_type, 12 r_type, 13 g_type)
     * @return
     */
    public Map<String, String> getUserParticipation2(long start, long end, int logType, String param, int paramType) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", logType);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder range_q = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.must(range_q);
            TermQueryBuilder param_q = QueryBuilders.termQuery("jsonData." + param, paramType);
            boolQueryBuilder.must(param_q);

            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword");
            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(server_a).execute().actionGet();
            StringTerms terms = searchResponse.getAggregations().get("server");
            List<StringTerms.Bucket> buckets = terms.getBuckets();
            Map<String, String> data = new HashMap<>();
            long total = 0;
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                long docCount = bucket.getDocCount();
                total += docCount;
                data.put(bucket.getKeyAsString(), String.valueOf(docCount));
            }
            data.put(Row.TOTAL_COUNT, String.valueOf(total));
            return data;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*   *//***
     * 返回每个用户参与项目,角色养成的值 注意,如果值是0,那么不会存在key
     * (11 u_type, 12 r_type, 13 g_type)
     * @return
     *//*
    public  Map<String, String> getUserParticipation(long start, long end, int logType, String param) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", logType);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder range_q = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.must(range_q);

            TermsAggregationBuilder count_a = AggregationBuilders.terms("count").size(100).field("jsonData." + param);
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").subAggregation(count_a);
            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(server_a).execute().actionGet();
            StringTerms terms = searchResponse.getAggregations().get("server");
            List<StringTerms.Bucket> buckets = terms.getBuckets();
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                Aggregation count = bucket.getAggregations().get("count");
                System.out.println(count);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

//    public  Map<String, String >

    /***
     * 获取用户参与活动相关数据
     * @param start
     * @param end
     * @param cids
     * @return
     */
    public Map<String, String> getAcitivityUserJoinCountInfo(long start, long end, List<Integer> cids) {
        Map<String, String> re = new HashMap<>();
        int index = 6;
        for (int i = 0; i < cids.size(); i++) {
            Integer cid = cids.get(i);
            re.put("h" + Row.SP + "a" + cid + Row.SP + (i + index), String.valueOf(cid));
            Map<String, String> data = getUserParticipation2(start, end, 11, "u_type", cids.get(i));
            if (null == data) {
                continue;
            }
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String rowKey = entry.getKey();
                String value = entry.getValue();
                re.put("d" + Row.SP + "a" + cid + Row.SP + rowKey, value);
            }
        }
//        System.out.println(re);
        return re;
    }

    /***
     * 获取副本流失
     * @param insts
     * @param start
     * @param end
     * @return
     */
    public Map<String, String> getInstBreakPointInfo(List<Integer> insts, long start, long end) {
        if (null == insts || insts.size() == 0)
            return null;
        Map<String, Map<String, Map<Integer, Long>>> data = new HashMap<>();
        Map<String, String> re = new HashMap<>();
        Set<Map.Entry<String, Map<String, Map<Integer, Long>>>> entries = data.entrySet();
        //TODO 以后列位置要写活
        int index = 9;
        for (int i = 0; i < insts.size(); i++) {
            Integer ins = insts.get(i);
            getInfoForBreakPoint(start, end, ins, "nearInst", data);
            re.put("h" + Row.SP + "p" + ins + Row.SP + (i + index), String.valueOf(ins));
        }
        /**
         *     * 10点流出 是 1 - (l:0,p:10)/(l:1,p:0)
         *      * 20点流出 是 1 - (l:0,p:20)/(l:1,p:10)
         *      * 30点流出 是 1 - (l:0,p:30)/(l:1,p:20)
         *      * 40点流出 是 1 - (l:0,p:40)/(l:1,p:30)
         */
//        System.out.println(data);
        for (Map.Entry<String, Map<String, Map<Integer, Long>>> entry : entries) {
            String key_server = entry.getKey();
            Map<String, Map<Integer, Long>> d = entry.getValue();
            Map<Integer, Long> d0 = d.get("0");
            Map<Integer, Long> d1 = d.get("1");
            if (null == d0 || null == d1) {
                continue;
            }
            for (int i = 0; i < insts.size() - 1; i++) {
//                System.out.println("server:" + key_server + " d0:" + d0.get(i+10) + " " + i);
//                System.out.println("server:" + key_server + " d1:" + d1.get(i));
                if (null == d1.get(i) || null == d0.get(i + 1))
                    continue;
                if (d1.get(i) == 0) {
//                    re.put("d" + Row.SP + "p" + (i + 1) + Row.SP + key_server, Row.SP);
                } else {
                    double v = (double) d0.get(i + 1) / (double) d1.get(i);
                    re.put("d" + Row.SP + "p" + (i + 1) + Row.SP + key_server, MathUtil.saveTwoDecimals(v));
                }
            }
        }
//        System.out.println(re);
        return re;
    }

    /***
     * 获取新手引导流失数据
     * @param start
     * @param end
     * @return
     */
    public Map<String, String> getNewPlayerBreakPointInfo(long start, long end) {
        Map<String, Map<String, Map<Integer, Long>>> data = new HashMap<>();
        for (int i = 0; i < 291; i += 10) {
            getInfoForBreakPoint(start, end, i, "point", data);
        }
        /**
         *     * 10点流出 是 1 - (l:0,p:10)/(l:1,p:0)
         *      * 20点流出 是 1 - (l:0,p:20)/(l:1,p:10)
         *      * 30点流出 是 1 - (l:0,p:30)/(l:1,p:20)
         *      * 40点流出 是 1 - (l:0,p:40)/(l:1,p:30)
         */
        Map<String, String> re = new HashMap<>();
        Set<Map.Entry<String, Map<String, Map<Integer, Long>>>> entries = data.entrySet();
        int index = 2;
        for (int i = 10, j = index; i < 291; i += 10, j++) {
            re.put("h" + Row.SP + "p" + i + Row.SP + j, String.valueOf(i));
        }
        System.out.println(data);
        for (Map.Entry<String, Map<String, Map<Integer, Long>>> entry : entries) {
            String key_server = entry.getKey();
            Map<String, Map<Integer, Long>> d = entry.getValue();
            Map<Integer, Long> d0 = d.get("0");
            Map<Integer, Long> d1 = d.get("1");
            if (null == d0 || null == d1) {
                continue;
            }
            for (int i = 0; i < 281; i += 10) {
//                System.out.println("server:" + key_server + " d0:" + d0.get(i+10) + " " + i);
//                System.out.println("server:" + key_server + " d1:" + d1.get(i));
                if (null == d1.get(i) || null == d0.get(i + 10))
                    continue;
                if (d1.get(i) == 0) {
//                    re.put("d" + Row.SP + "p" + (i + 10) + Row.SP + key_server, Row.SP);
                } else {
                    double v = (double) d0.get(i + 10) / (double) d1.get(i);
                    re.put("d" + Row.SP + "p" + (i + 10) + Row.SP + key_server, String.valueOf(v));
                }

            }
        }
        return re;
    }

    /***
     * 获取玩家流失相关数据
     * @param start
     * @param end
     * @param maxPoint
     * @param param
     * @param data
     */
    public void getInfoForBreakPoint(long start, long end, int maxPoint, String param, Map<String, Map<String, Map<Integer, Long>>> data) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 2);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder range_q = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.must(range_q);

            MaxAggregationBuilder max_a = AggregationBuilders.max("max").field("jsonData." + param);
            String inlineScript = "params.value == params.pa";
            Map<String, Object> pp = new HashMap<>();
            pp.put("pa", maxPoint);
            Script script = new Script(ScriptType.INLINE, "painless", inlineScript, pp);
            Map<String, String> bucket_path = new HashMap<>();
            bucket_path.put("value", "max");
            BucketSelectorPipelineAggregationBuilder filter1_a = PipelineAggregatorBuilders.
                    bucketSelector("filter1", bucket_path, script);

            TermsAggregationBuilder acc_a = AggregationBuilders.terms("acc").field("jsonData.acc.keyword").size(20000).
                    subAggregation(max_a).subAggregation(filter1_a);
            StatsBucketPipelineAggregationBuilder count_a = PipelineAggregatorBuilders.statsBucket("count", "acc>max");
            TermsAggregationBuilder type_a = AggregationBuilders.terms("type").field("jsonData.type").size(2).subAggregation(acc_a).subAggregation(count_a);
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").size(200).subAggregation(type_a);

            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(server_a).execute().actionGet();
            Aggregation server = searchResponse.getAggregations().get("server");
            if (!(server instanceof StringTerms))
                return;
            StringTerms server_r = (StringTerms) server;
            List<StringTerms.Bucket> buckets = server_r.getBuckets();
            Map<String, Map<Integer, Long>> total_serverData = null;
            if (data.containsKey(Row.TOTAL_COUNT)) {
                total_serverData = data.get(Row.TOTAL_COUNT);
            } else {
                total_serverData = new HashMap<>();
                data.put(Row.TOTAL_COUNT, total_serverData);
            }
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                Map<String, Map<Integer, Long>> server_data = null;
                if (data.containsKey(bucket.getKeyAsString())) {
                    server_data = data.get(bucket.getKeyAsString());
                } else {
                    server_data = new HashMap<>();
                    data.put(bucket.getKeyAsString(), server_data);
                }
                LongTerms type_r = bucket.getAggregations().get("type");
                List<LongTerms.Bucket> buckets_t = type_r.getBuckets();
                for (int j = 0; j < buckets_t.size(); j++) {
                    LongTerms.Bucket bucket_t = buckets_t.get(j);
                    Map<Integer, Long> t_type_data = null;
                    if (total_serverData.containsKey(bucket_t.getKeyAsString())) {
                        t_type_data = total_serverData.get(bucket_t.getKeyAsString());
                    } else {
                        t_type_data = new HashMap<>();
                        total_serverData.put(bucket_t.getKeyAsString(), t_type_data);
                    }
                    Map<Integer, Long> type_data = null;
                    if (server_data.containsKey(bucket_t.getKeyAsString())) {
                        type_data = server_data.get(bucket_t.getKeyAsString());
                    } else {
                        type_data = new HashMap<>();
                        server_data.put(bucket_t.getKeyAsString(), type_data);
                    }
                    InternalStatsBucket count_r = bucket_t.getAggregations().get("count");
                    long count_v = count_r.getCount();
                    type_data.put(maxPoint, count_v);
                    if (t_type_data.containsKey(maxPoint)) {
                        t_type_data.put(maxPoint, t_type_data.get(maxPoint) + count_v);
                    } else {
                        t_type_data.put(maxPoint, count_v);
                    }
                }
            }
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*   *//****
     * use 0.9
     * 获取断流流失总目标人数或通过人数
     * 1点流出 是 1 - (l:0,p:1)/(l:1,p:0)
     * 2点流出 是 1 - (l:0,p:2)/(l:1,p:1)
     * 3点流出 是 1 - (l:0,p:3)/(l:1,p:2)
     * 4点流出 是 1 - (l:0,p:4)/(l:1,p:3)
     * @param serverName 服务器名称
     * @param date 日期 yyyy-MM-dd 字符串
     * @param logType 登陆或登出类型
     * @param point 断点
     * @return 值
     *//*
    public  long getPonitLeaveTotalOrTargetByServerDatePoint(String serverName, String date, int logType, int point) {
        long re = 0;
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 2);
            boolQueryBuilder.must(type_q);
            MatchQueryBuilder logType_q = QueryBuilders.matchQuery("jsonData.type", logType);
            boolQueryBuilder.must(logType_q);
            MatchQueryBuilder date_q = QueryBuilders.matchQuery("date", date);
            boolQueryBuilder.must(date_q);
            MatchQueryBuilder server_q = QueryBuilders.matchQuery("server", serverName);
            boolQueryBuilder.must(server_q);

            TermsAggregationBuilder acc_a = AggregationBuilders.terms("acc").field("jsonData.acc.keyword").size(20000);
            MaxAggregationBuilder max_a = AggregationBuilders.max("max").field("jsonData.point");
            Script script = new Script("params.value == " + point);
            Map<String, String> bucket_path = new HashMap<>();
            bucket_path.put("value", "max");
            acc_a.subAggregation(max_a);
            BucketSelectorPipelineAggregationBuilder filter1_a = PipelineAggregatorBuilders.
                    bucketSelector("filter1", bucket_path, script);
            acc_a.subAggregation(filter1_a);
            StatsBucketPipelineAggregationBuilder count_a = PipelineAggregatorBuilders.statsBucket("count", "acc>max");

            SearchResponse searchResponse = client.prepareSearch().setSize(0).setQuery(boolQueryBuilder).addAggregation(acc_a).addAggregation(count_a).execute().actionGet();
            System.out.println(searchResponse);
            InternalStatsBucket count_r = searchResponse.getAggregations().get("count");
//            System.out.println(count_r.getCount());
            return count_r.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }*/

    /***
     * use
     * 根据角色创建时间与服务器开服时间的差值和登陆日期与角色创建时间的
     * 差值计算满足条件的总数和存留数
     * @param cre 角色创建时间和服务器开服时间相差的天数
     * @param lo 登陆时间和角色创建时间相差的天数
     * @return int[0] 满足角色创建时间和服务器开服时间相差天数的去重账号数
     *          int[1] 在int[0]基础上满足登陆日期与角色创建时间的去重账号数,即存留
     *
     *          0-1 次留 0-2 2留 0-3 3留 0-4:4留
     */
    public String getRetentionByTwoMinusValuesIgloreServer(int cre, int lo) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 2);
            boolQueryBuilder.must(type_q);
            String scriptStr1 = "doc['jsonData.role_create'].value.toInstant().toEpochMilli() - doc['jsonData.ser_create'].value.toInstant()." +
                    "toEpochMilli() == 86400000 * " + cre;
            Script script = new Script(scriptStr1);
            boolQueryBuilder.must(QueryBuilders.scriptQuery(script));

            CardinalityAggregationBuilder total_a = AggregationBuilders.cardinality("total").field("jsonData.acc.keyword");
            String scriptStr2 = "doc['date'].value.toInstant().toEpochMilli() - doc['jsonData.ser_create'].value.toInstant()" +
                    ".toEpochMilli() == 86400000 * " + lo;
            FilterAggregationBuilder count_a = AggregationBuilders.filter("count", QueryBuilders.scriptQuery(new Script(scriptStr2)));
            CardinalityAggregationBuilder va_a = AggregationBuilders.cardinality("va").field("jsonData.acc.keyword");
            count_a.subAggregation(va_a);

            SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).setSize(0).addAggregation(total_a).addAggregation(count_a).execute().actionGet();
                InternalCardinality total_r = searchResponse.getAggregations().get("total");
                long t = total_r.getValue();
                InternalFilter count_r = searchResponse.getAggregations().get("count");
                InternalCardinality va_r = count_r.getAggregations().get("va");
                long c = va_r.getValue();
                if (t != 0) {
                    return MathUtil.saveTwoDecimals((double) c / (double) t * 100) + "%";
                } else {
                    return " ";
                }
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * use
     * 根据角色创建时间与服务器开服时间的差值和登陆日期与角色创建时间的
     * 差值计算满足条件的总数和存留数
     * @param cre 角色创建时间和服务器开服时间相差的天数
     * @param lo 登陆时间和角色创建时间相差的天数
     * @return int[0] 满足角色创建时间和服务器开服时间相差天数的去重账号数
     *          int[1] 在int[0]基础上满足登陆日期与角色创建时间的去重账号数,即存留
     *
     *          0-1 次留 0-2 2留 0-3 3留 0-4:4留
     */
    public Map<String, String> getRetentionByTwoMinusValues(int cre, int lo) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 2);
            boolQueryBuilder.must(type_q);
            String scriptStr1 = "doc['jsonData.role_create'].value.toInstant().toEpochMilli() - doc['jsonData.ser_create'].value.toInstant()." +
                    "toEpochMilli() == " + TimeEnum.RENT_TIME + " * " + cre;
            Script script = new Script(scriptStr1);
            boolQueryBuilder.must(QueryBuilders.scriptQuery(script));

            CardinalityAggregationBuilder total_a = AggregationBuilders.cardinality("total").field("jsonData.acc.keyword");
            String scriptStr2 = "doc['date'].value.toInstant().toEpochMilli() - doc['jsonData.ser_create'].value.toInstant()" +
                    ".toEpochMilli() == " + TimeEnum.RENT_TIME + "* " + lo;
            FilterAggregationBuilder count_a = AggregationBuilders.filter("count", QueryBuilders.scriptQuery(new Script(scriptStr2)));
            CardinalityAggregationBuilder va_a = AggregationBuilders.cardinality("va").field("jsonData.acc.keyword");
            count_a.subAggregation(va_a);
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").subAggregation(total_a).subAggregation(count_a).size(200);
            SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).setSize(0).addAggregation(server_a).execute().actionGet();
            Aggregation server = searchResponse.getAggregations().get("server");
            if (!(server instanceof StringTerms))
                return null;
            StringTerms server_r = (StringTerms) server;
            List<StringTerms.Bucket> buckets = server_r.getBuckets();
            Map<String, String> data = new HashMap<>();
            double t_t = 0.0;
            double c_t = 0.0;
//            System.out.println(server_r);
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                String key = bucket.getKeyAsString();
                InternalCardinality total_r = bucket.getAggregations().get("total");
                long t = total_r.getValue();
                InternalFilter count_r = bucket.getAggregations().get("count");
                InternalCardinality va_r = count_r.getAggregations().get("va");
                long c = va_r.getValue();

                if (t != 0) {
                    data.put(key, MathUtil.saveTwoDecimals((double) c / (double) t * 100) + "%");
                } else {
                    data.put(key, "");
                }
                t_t += t;
                c_t += c;
            }
            if (t_t != 0) {
                data.put(Row.TOTAL_COUNT, MathUtil.saveTwoDecimals(c_t / t_t * 100) + "%");
            } else {
                data.put(Row.TOTAL_COUNT, "");
            }
            return data;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * use 1.0
     * @return 获取新注册用户
     */
    public Map<String, String> getNewRegesterAccountByTime(long startTime, long endTime) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType", 1);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder filter1 = QueryBuilders.rangeQuery("@timestamp").gte(startTime).lt(endTime);
            boolQueryBuilder.filter(filter1);
//            MatchQueryBuilder date_q = QueryBuilders.matchQuery("date", dateStr);
//            boolQueryBuilder.must(date_q);
//            MatchQueryBuilder dtype_q = QueryBuilders.matchQuery("jsonData.type", 0);
//            boolQueryBuilder.must(dtype_q);


            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").size(100);
            CardinalityAggregationBuilder acc_a = AggregationBuilders.cardinality("acc").field("jsonData.actId.keyword");
            server_a.subAggregation(acc_a);
            SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).setSize(0).addAggregation(server_a).execute().actionGet();
            Aggregation server = searchResponse.getAggregations().get("server");
            if (!(server instanceof StringTerms))
                return null;
            StringTerms re = (StringTerms) server;
            List<StringTerms.Bucket> buckets = re.getBuckets();
            long t = 0;
            Map<String, String> res = new HashMap<>();
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                InternalCardinality acc = bucket.getAggregations().get("acc");
                long s = acc.getValue();
                t += s;
                res.put(bucket.getKeyAsString(), String.valueOf(s));
            }
            res.put(Row.TOTAL_COUNT, String.valueOf(t));
            return res;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     *use1.0
     * 根据玩家在线时长和日期统计
     * @param start
     * @param end
     * @param timeStart 在线时段开始时长(不包括该时间点)
     * @param timeEnd 在线时段结束时长(包括该时间点)
     * @return
     */
    public Map<String, String> getOnlinePlayerCountByTimeTime(long start, long end, long timeStart, long timeEnd) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            RangeQueryBuilder range_q = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            TermQueryBuilder logType_q = QueryBuilders.termQuery("logType", 2);
            TermQueryBuilder type_q = QueryBuilders.termQuery("jsonData.type", 0);
            BoolQueryBuilder bool = QueryBuilders.boolQuery().must(range_q).must(logType_q).must(type_q);

            SumAggregationBuilder sum_a = AggregationBuilders.sum("sum").field("jsonData.onTime");
            TermsAggregationBuilder acc_a = AggregationBuilders.terms("acc").field("jsonData.acc.keyword").subAggregation(sum_a);
            AvgBucketPipelineAggregationBuilder avg_sum_onTime_a = PipelineAggregatorBuilders.avgBucket("avg_sum_onTime", "acc>sum");
            Map<String, String> paths = new HashMap<>();
            paths.put("value", "avg_sum_onTime");
            Script script = new Script("params.value>" + timeStart + "&&params.value<=" + timeEnd);
            BucketSelectorPipelineAggregationBuilder filter_a = PipelineAggregatorBuilders.bucketSelector("filter", paths, script);
            StatsBucketPipelineAggregationBuilder count_a = PipelineAggregatorBuilders.statsBucket("count", "acc>sum");
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").size(200).subAggregation(acc_a).
                    subAggregation(avg_sum_onTime_a).subAggregation(filter_a).subAggregation(count_a);

            SearchResponse searchResponse = client.prepareSearch().setQuery(bool).setSize(0).addAggregation(server_a).execute().actionGet();
//            System.out.println(searchResponse);
            Aggregation server = searchResponse.getAggregations().get("server");
            if (!(server instanceof StringTerms))
                return null;
            StringTerms server_r = (StringTerms) server;
            List<StringTerms.Bucket> buckets = server_r.getBuckets();
            Map<String, String> res = new HashMap<>();
            long t = 0L;
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                InternalStatsBucket stats = bucket.getAggregations().get("count");
                long value = stats.getCount();
                t += value;
                res.put(bucket.getKeyAsString(), String.valueOf(value));
            }
            res.put(Row.TOTAL_COUNT, String.valueOf(t));
            return res;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 获取公会活跃玩家比
     * @return
     */
    public Map<String, String> getGuildActUserRatio(long point_s, long point_e, long start, long end) {
        Map<String, String> g_mem_count = getGuildCount(point_s, point_e, "gMemCount");
        Map<String, String> act_count = getActiveAccountCountByTimeAndServer(start, end, 1, true);
        Map<String, String> data = new HashMap<>();
        for (Map.Entry<String, String> entry : g_mem_count.entrySet()) {
            String rKey = entry.getKey();
            double tol = Double.valueOf(entry.getValue());
            if (0 == tol || !act_count.containsKey(rKey)) {
                data.put(rKey, "0.00");
            } else {
                long act = Long.valueOf(act_count.get(rKey));
                data.put(rKey, MathUtil.saveTwoDecimals(act / tol));
            }
        }
        return data;
    }

    /**
     * use
     * 统计每个服务器的活跃账号数目  指定时间范围类登陆过指定天数即以上
     *
     * @param start
     * @param end
     * @return
     */
    public Map<String, String> getActiveAccountCountByTimeAndServer(long start, long end, int num, boolean withGuild) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            MatchQueryBuilder type_q = QueryBuilders.matchQuery("logType.keyword", 2);
            boolQueryBuilder.must(type_q);
            RangeQueryBuilder filter1 = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.filter(filter1);
            if (withGuild) {
                boolQueryBuilder.must(QueryBuilders.termQuery("jsonData.inGuild", 1));
            }
            TermsAggregationBuilder acc = AggregationBuilders.terms("acc").field("jsonData.acc.keyword").size(10000);
            CardinalityAggregationBuilder count1 = AggregationBuilders.cardinality("count1").field("date");
            acc.subAggregation(count1);
            Script script = new Script("params.value > " + (num - 1));
            Map<String, String> bucket_path = new HashMap<>();
            bucket_path.put("value", "count1");
            BucketSelectorPipelineAggregationBuilder selector1 = PipelineAggregatorBuilders.
                    bucketSelector("selector1", bucket_path, script);
            acc.subAggregation(selector1);
            StatsBucketPipelineAggregationBuilder re = PipelineAggregatorBuilders.statsBucket("re", "acc>count1");
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").size(200).subAggregation(acc).subAggregation(re);

            SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).setSize(0).addAggregation(server_a).execute().actionGet();
            System.out.println(searchResponse);
            Aggregation server = searchResponse.getAggregations().get("server");
            if(null == server){
                log.error("activity acc search start :{},  end :{},   num:{},  withGuild:{}", start, end, num , withGuild);
                return null;
            }
            if (!(server instanceof StringTerms))
                return null;
            StringTerms server_r = (StringTerms) server;
            List<StringTerms.Bucket> buckets = server_r.getBuckets();
            Map<String, String> res = new HashMap<>();
            long t = 0L;
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                InternalStatsBucket stats = bucket.getAggregations().get("re");
                long value = stats.getCount();
                t += value;
                res.put(bucket.getKeyAsString(), String.valueOf(value));
            }
            res.put(Row.TOTAL_COUNT, String.valueOf(t));
//            long count = re_r.getCount();
//            return count;
            return res;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * use
     * 根据在日期，小时数获取该小时类的平均在线时长，以玩家的账号为基本统计单元
     * longCache 参数的存在只是突然有个想法,有的数据需要别的列的数据来关联.获取可以存在上下文当中
     * 留下这个参数作为一种方案的提醒
     *
     * @return
     */
    public Map<String, String> getAvgOnlineTimeByHourDate(long start, long end, Map<String, Object> longCache) {
        TransportClient client = null;
        try {
            client = TransportClientFactory.getInstance().getClient();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            RangeQueryBuilder filter1 = QueryBuilders.rangeQuery("@timestamp").gte(start).lt(end);
            boolQueryBuilder.filter(filter1);

            TermsAggregationBuilder acc_a = AggregationBuilders.terms("acc").field("jsonData.acc.keyword").size(20000);
            SumAggregationBuilder sum_a = AggregationBuilders.sum("sum").field("jsonData.onTime");
            acc_a.subAggregation(sum_a);
            StatsBucketPipelineAggregationBuilder avg = PipelineAggregatorBuilders.statsBucket("avg_sum_onTime", "acc> sum");
            TermsAggregationBuilder server_a = AggregationBuilders.terms("server").field("server.keyword").subAggregation(acc_a).subAggregation(avg);

            SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).setSize(0).addAggregation(server_a).execute().actionGet();
            Aggregation server = searchResponse.getAggregations().get("server");
            if (!(server instanceof StringTerms))
                return null;
            StringTerms server_r = (StringTerms) server;
            List<StringTerms.Bucket> buckets = server_r.getBuckets();
            Map<String, String> data = new HashMap<>();
            long t_c = 0;
            double t_s = 0;
            for (int i = 0; i < buckets.size(); i++) {
                StringTerms.Bucket bucket = buckets.get(i);
                InternalStatsBucket s_avg_sum_onTime = bucket.getAggregations().get("avg_sum_onTime");
                t_c += s_avg_sum_onTime.getCount();
                t_s += s_avg_sum_onTime.getSum();
                if(0 != s_avg_sum_onTime.getCount()) {
                    data.put(bucket.getKeyAsString(), MathUtil.saveDoubleMilAsMinute(s_avg_sum_onTime.getAvg()));
                }else {
                    data.put(Row.TOTAL_COUNT, "0");
                }
            }
            if (t_c != 0) {
                data.put(Row.TOTAL_COUNT, MathUtil.saveDoubleMilAsMinute(t_s / t_c));
            } else {
                data.put(Row.TOTAL_COUNT, "0");
            }
            return data;
        } catch (NoNodeAvailableException e) {
            throw new AnalyeException("elasticsearch 联系不上");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

/*   *//**
 * 更具分钟数获取在线超过该时长的登陆登出数（不是玩家个数）
 *
 * @param minute
 * @return 获取玩家平均在线时长 这个是根据每次登陆登出来计算的
 * @return
 * @throws IOException
 * <p>
 * 已废除
 * 获取获取活跃玩家数据 仅从日志中获取，当前在线玩家不被包括
 * @return
 * @throws IOException
 * /*
 * 根据id获取
 * @throws IOException
 *//*
    public  long getOnlineCountByMinuteSimple(int minute) throws IOException {
        RangeAggregationBuilder range = AggregationBuilders.range("agg").field("jsonData.onTime");
        // TODO这里之后要进行分钟和毫秒的转换
//        range.addUnboundedFrom(minute * 1000 * 60);
        range.addUnboundedFrom(7000);
        TransportClient client = TransportClientFactory.getInstance().getClient();
        SearchResponse searchResponse = client.prepareSearch().addAggregation(range).execute().actionGet();
        Range r = searchResponse.getAggregations().get("agg");
        List<? extends Range.Bucket> buckets = r.getBuckets();
        for (Range.Bucket bt : buckets) {
            long docCount = bt.getDocCount();
            System.out.println(docCount);
            return docCount;
        }
        return 0;
    }*/

/*    *//***
 * 获取玩家平均在线时长 这个是根据每次登陆登出来计算的
 * @return
 * @throws IOException
 *//*
    public  double getAvgPlayersOnlineTimeSimple() throws IOException {
        TransportClient client = TransportClientFactory.getInstance().getClient();
        StatsAggregationBuilder agg = AggregationBuilders.stats("agg").field("jsonData.onTime");
        SearchResponse searchResponse = client.prepareSearch().addAggregation(agg).execute().actionGet();
        Stats stats = searchResponse.getAggregations().get("agg");
        double avg = stats.getAvg();
        System.out.println(avg);
        return avg;
    }*/

/***
 * 已废除
 * 获取获取活跃玩家数据 仅从日志中获取，当前在线玩家不被包括
 * @return
 * @throws IOException
/*     *//*
    public  long getActivePlayerCountSimple() throws IOException {
        TransportClient client = TransportClientFactory.getInstance().getClient();
//        RangeQueryBuilder jsonData = QueryBuilders.rangeQuery("jsonData.onTime");
//        jsonData.gte(7000);
//        //TODO 现在是java遍历bulket找结果，直接在elasticsearch上处理去重没成功
//        SearchResponse searchResponse = client.prepareSearch().setQuery(jsonData).execute().actionGet();
//        long totalHits = searchResponse.getHits().totalHits;
//        System.out.println(totalHits);
        TermsAggregationBuilder server = AggregationBuilders.terms("server");
        server.field("server.keyword");
        TermsAggregationBuilder pid = AggregationBuilders.terms("pid");
        pid.field("jsonData.pid");
        RangeAggregationBuilder range = AggregationBuilders.range("range");
        range.field("jsonData.onTime");
        range.addUnboundedFrom(7000);
        server.subAggregation(pid);
        pid.subAggregation(range);
        SearchResponse searchResponse = client.prepareSearch().addAggregation(server).execute().actionGet();
        Aggregations aggregations = searchResponse.getAggregations();
        Terms server1 = aggregations.get("server");
        List<? extends Terms.Bucket> buckets = server1.getBuckets();
        int size = 0;
        for (int i = 0; i < buckets.size(); i++) {
            Terms pid1 = buckets.get(i).getAggregations().get("pid");
            size += pid1.getBuckets().size();
        }
        System.out.println(size);
//        Aggregation range1 = searchResponse.getAggregations().get("");
//        System.out.println(range1);
        return size;

    }*/

/*    public  TransportClient getType2() throws IOException {
        TransportClient client = TransportClientFactory.getInstance().getClient();
//        MultiSearchRequestBuilder multiSearchRequestBuilder = client.prepareMultiSearch();
//        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("");
//        searchRequestBuilder.
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("logType", 2));
        SearchResponse searchResponse = client.prepareSearch().setQuery(boolQueryBuilder).execute().actionGet();
        for (SearchHit searchHit : searchResponse.getHits()) {
            System.out.println(searchHit.getFields().get("onTime"));
        }
        return client;
    }*/

/*   *//**
 * 根据id获取
 *
 * @throws IOException
 *//*
    public  TransportClient getDoc() throws IOException {
        TransportClient client = TransportClientFactory.getInstance().getClient();
        // prepareGet 参数分别为index、type、id
        GetResponse response = client.prepareGet("clothes", "young", "1").get();
        String id = response.getId();
        String index = response.getIndex();
        Map<String, DocumentField> fields = response.getFields();
        // 返回的source，也就是数据源
        Map<String, Object> source = response.getSource();
        System.out.println("ID:" + id + ",Index:" + index);
        Set<String> fieldKeys = fields.keySet();
        for (String s : fieldKeys) {
            DocumentField documentField = fields.get(s);
            String name = documentField.getName();
            List<Object> values = documentField.getValues();
            System.out.println(name + ":" + values.toString());
        }
        System.out.println("==========");
        Set<String> sourceKeys = source.keySet();
        for (String s : sourceKeys) {
            Object o = source.get(s);
            System.out.println(s + ":" + o);
        }
        return client;
    }*/

