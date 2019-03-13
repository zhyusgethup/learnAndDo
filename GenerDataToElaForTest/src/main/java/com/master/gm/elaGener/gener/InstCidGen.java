package com.master.gm.elaGener.gener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InstCidGen {
    public static final int Inst_BASE = 3_000_000;
    public static final int NUM = 200;
    public static final List<Integer> GOOD_IDS = genGoodID();
    public static final Random r = new Random();
    private static List<Integer> genGoodID(){
        List<Integer> list = new ArrayList<>();
        for (int i = Inst_BASE; i < Inst_BASE + NUM; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<Integer> getGoodIds(){
        return GOOD_IDS;
    }

    public static int getGoodID(){
        return GOOD_IDS.get(r.nextInt(NUM));
    }
}
