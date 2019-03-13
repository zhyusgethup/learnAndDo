package com.master.gm.elaGener.gener;

import java.util.concurrent.atomic.AtomicInteger;

public class GuildNameGen {
    public static final String BASE = "公会";
    public static final AtomicInteger COUNT = new AtomicInteger(1);
    public static String getName(){
        return BASE + COUNT.getAndIncrement();
    }
}
