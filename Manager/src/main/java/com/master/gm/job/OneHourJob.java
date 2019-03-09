package com.master.gm.job;

import com.master.analyze.analyeBean.TimeManager;
import com.master.analyze.manager.TableManager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@IocBean
public class OneHourJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(OneHourJob.class);
    @Inject
    private TableManager manager;
    @Inject
    TimeManager timeManager;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("oneHourjob");
        timeManager.flush();
        log.info(manager.getTableByTableStr("com").fetchFromLogsAndCheckClear(manager.getService()).toString());

    }
}
