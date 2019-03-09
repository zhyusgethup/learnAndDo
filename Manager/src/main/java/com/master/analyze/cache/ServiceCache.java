package com.master.analyze.cache;

import com.google.common.collect.Table;
import com.master.analyze.analyeBean.CGoodsBean;
import com.master.analyze.analyeBean.GoodCidBean;
import com.master.analyze.analyeBean.GoodServerBean;
import com.master.analyze.analyeBean.GoodUserBean;

import java.util.*;

public class ServiceCache {
    public static Set<Integer> cids;
    public static Map<Integer,GoodCidBean> cidBeans;
    static  {
        cids = new TreeSet<>((o1,o2) -> o1 -o2);
        cidBeans = new HashMap<>();
    }

    public static void clear(){
        cids.clear();
        cidBeans.clear();
    }

    public static void computeAll() {
        for(GoodCidBean cidBean:cidBeans.values()) {
            cidBean.compute();
        }
    }


    public static List<CGoodsBean> replaceAll(long start, long end) {
        List<CGoodsBean> list = new ArrayList<>();
        for (GoodCidBean cidBean:cidBeans.values()) {
            cidBean.replace(start, end);
        }
        return list;
    }

    public static boolean fillBeanIn(CGoodsBean bean, long start, long end) {
//        if (!goodServerBeans.containsKey(bean.getServer()))
//            return false;
//        GoodServerBean serverBean = goodServerBeans.get(bean.getServer());
        GoodCidBean cidBean = cidBeans.get(bean.getCid());
        if(null == cidBean)
            return false;
        GoodUserBean userBean = cidBean.getBottomData().get(bean.getServer(),bean.getAcc());
        if(null == userBean)
            return  false;
        double cur = userBean.getProduct() - userBean.getCost() + bean.getCurCount();
        double his = bean.getHisCount() + userBean.getProduct();
        userBean.setCurCount(cur);
        userBean.setHisCount(his);
        bean.setCurCount((int)cur);
        bean.setHisCount((int)his);
        bean.setStart(start);
        bean.setEnd(end);
        return true;
    }

}
