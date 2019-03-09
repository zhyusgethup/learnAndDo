package com.master.module;

import com.master.analyze.manager.TableManager;
import com.master.gm.service.analyze.impl.GoodsComputeServiceImpl;
import com.master.gm.service.analyze.impl.DBServiceImpl;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.integration.quartz.NutQuartzCronJobFactory;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import java.util.List;

/**
 * Created by DJL on 2017/3/2.
 *
 * @ClassName GmSetup
 * @Description
 */
public class GmSetup implements Setup{
    @Override
    public void init(NutConfig nutConfig) {
        System.out.println("qurtz初始化");
        Ioc ioc = nutConfig.getIoc();
//        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
//        Daos.createTablesInPackage(dao, "com.master.bean", false);
//        GmDocConfig.initFile();

//        Ioc ioc = Mvcs.ctx().getDefaultIoc();
//        DBServiceImpl dbService = Mvcs.getIoc().get(DBServiceImpl.class);
        // 触发quartz 工厂,将扫描job任务
        ioc.get(NutQuartzCronJobFactory.class);
        //TODO 暂时用一个线程保证 nutz不会死
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               ioc.get(TableManager.class).init();
            }
        });
        thread.start();
    }



    @Override
    public void destroy(NutConfig nutConfig) {
    }
}
