package com.master.gm.elaGener.gener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerIdGen {
    public static final int SERVER_BASE = 3_000_000;
    public static final int NUM = 200;
    public static final List<String> SERVER_IDS = genGoodID();
    public static final Random r = new Random();
    private static List<String> genGoodID(){
        List<String> list = new ArrayList<>();
        for (int i = SERVER_BASE; i < SERVER_BASE + NUM; i++) {
            list.add(String.valueOf(i + SERVER_BASE));
        }
        return list;
    }

    public static List<String> getServerIds(int num) {
        if(num > NUM)
            return SERVER_IDS;
        return SERVER_IDS.subList(0, num);
    }

    public static List<String> getServerIds(){
        return SERVER_IDS;
    }

    public static String getServerID(){
        return SERVER_IDS.get(r.nextInt(NUM));
    }
}
