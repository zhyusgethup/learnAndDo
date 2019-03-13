package com.master.gm.elaGener.gener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccGen {
    public static List<String> getAcc(int num) {
        if(num <= 0)
            return new ArrayList<>();
        List<String> list = new ArrayList();
        for (int i = 0; i < num; i++) {
            list.add(UUID.randomUUID().toString());
        }
        return list;
    }
    public static String getAcc() {
        return  UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(getAcc(20));
    }
}
