package com.master.gm.service.analyze.impl;

import com.master.analyze.analyeBean.CGoodsBean;
import com.master.analyze.analyeBean.GoodCidBean;
import com.master.analyze.analyeBean.Row;
import com.master.analyze.cache.ServiceCache;
import com.master.gm.BaseService;
import com.master.gm.service.analyze.GoodsComputeService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.ioc.loader.json.JsonLoader;

import java.util.*;
@IocBean
public class GoodsComputeServiceImpl extends BaseService implements GoodsComputeService{
//    private static GoodsComputeService instance;
//    public static GoodsComputeService getInstance(){
//        if(null != instance) {
//            return instance;
//        }
//        return new GoodsComputeServiceImpl();
//    }
    public GoodsComputeServiceImpl(){
        init();
    }
    public void init(){
//        dao = MyIoc.dao;
////        System.out.println("goodsComputeService Init Dao =" + dao);
//        if(instance == null)
//            instance =this;
    }
    public List<CGoodsBean> getBeans(long start, long end) {
/*        List<CGoodsBean> query = dao.query(CGoodsBean.class, Cnd.where("start", "=", start).
                where("end", "=", end));*/
        String sqlStr = "select * from forgoodscompute as b where not exists(select 1 from forgoodscompute where acc= b.acc and b.end<end);";
        Sql sql = Sqls.create(sqlStr);
        sql.setEntity(dao.getEntity(CGoodsBean.class));
        sql.setCallback(Sqls.callback.entities());
        dao.execute(sql);
        List<CGoodsBean> query = sql.getList(CGoodsBean.class);
        return query;
    }

    public void combineAndUpdate(List<CGoodsBean> beans,long start, long end) {
        for (int i = 0; i < beans.size(); i++) {
            CGoodsBean bean = beans.get(i);
            ServiceCache.fillBeanIn(bean,start,end);
        }
        ServiceCache.computeAll();
        dao.update(beans,"^hisCount|curCount|start|end$");
    }

    public void generHeadAndData(Map<String,String> data) {
        //TODO 自动索引
        if(null == data)
            data = new HashMap<>();
        int index = 2;
        int ra = 8;//name:ckey{:产出 pr;消耗:co;比值:ra;剩余:cu;平均:av;大集:agL;中集:agM;小集:agS;} + cid
        List<Integer> cids = new ArrayList<>(ServiceCache.cids);
//        Map<String, Integer> cIndexes = ServiceCache.cIndexes;
        for (int i = 0; i < cids.size(); i++) {
            int cIndex = index + i * ra;
            Integer cid = cids.get(i);
            data.put("h" + Row.SP + "co" + cid + Row.SP + (cIndex + GoodCidBean.COST_INDEX),cid + "消耗");
            data.put("h" + Row.SP + "pr" + cid + Row.SP + (cIndex + GoodCidBean.PRODUCT_INDEX),cid + "产出");
            data.put("h" + Row.SP + "cu" + cid + Row.SP + (cIndex + GoodCidBean.CUR_INDEX),cid + "剩余");
            data.put("h" + Row.SP + "ra" + cid + Row.SP + (cIndex + GoodCidBean.RA_INDEX),cid + "比值");
            data.put("h" + Row.SP + "av" + cid + Row.SP + (cIndex + GoodCidBean.AVG_INDEX),cid + "平均");
            data.put("h" + Row.SP + "agL" + cid + Row.SP + (cIndex + GoodCidBean.AGL_INDEX),cid + "大集");
            data.put("h" + Row.SP + "agM" + cid + Row.SP + (cIndex + GoodCidBean.AGM_INDEX),cid + "中集");
            data.put("h" + Row.SP + "agS" + cid + Row.SP + (cIndex + GoodCidBean.AGS_INDEX),cid + "小集");
        }
       for(GoodCidBean cidBean:ServiceCache.cidBeans.values()) {
           cidBean.generData(data);
       }
    }

    public void replaceAndSave(long start, long end) {
        List<CGoodsBean> cGoodsBeans = ServiceCache.replaceAll(start, end);
        if(cGoodsBeans.size() > 0)
        dao.insert(cGoodsBeans);
    }

    public static void main(String[] args) {
        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
        Dao dao = ioc.get(Dao.class);
        String sqlStr = "select * from forgoodscompute as b where not exists(select 1 from forgoodscompute where acc= b.acc and b.end<end);";
        Sql sql = Sqls.create(sqlStr);
        sql.setEntity(dao.getEntity(CGoodsBean.class));
        sql.setCallback(Sqls.callback.entities());
        dao.execute(sql);
        System.out.println(sql.getList(CGoodsBean.class));
    }
}
