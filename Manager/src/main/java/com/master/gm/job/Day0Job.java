package com.master.gm.job;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.master.analyze.analyeBean.AbstractTable;
import com.master.analyze.analyeBean.TimeManager;
import com.master.analyze.manager.TableManager;
import com.master.analyze.table.MyTable;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@IocBean
public class Day0Job implements Job {

    private static final Logger log = LoggerFactory.getLogger(Day0Job.class);
    @Inject
    private TableManager manager;
    @Inject
    TimeManager timeManager;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<AbstractTable> tableList = manager.getTableList();
        System.out.println("day 0 job");
        timeManager.flush();
        List< StringBuilder> list = new ArrayList<>(20);
        for (int i = 0; i < tableList.size(); i++) {
            AbstractTable myTable = tableList.get(i);
            if (!myTable.getTableStr().equals("com")) {
                list.add(myTable.fetchFromLogsAndCheckClear(manager.getService()));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            log.info(list.get(i).toString());
        }
    }
}