package com.master.analyze.analyeBean;

import org.nutz.ioc.loader.annotation.IocBean;

@IocBean
public class TimeManager {
    private TimeEnum[] times;
    public TimeManager(){
        times = TimeEnum.values();
    }
    public void flush(){
        long now = System.currentTimeMillis();
        for (int i = 0; i < times.length; i++) {
            times[i].flush(now);
        }
    }
}
