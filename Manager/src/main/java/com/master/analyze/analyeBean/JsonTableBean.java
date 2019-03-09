package com.master.analyze.analyeBean;

import com.master.cons.Cons;

import java.util.*;

public class JsonTableBean {
    private String name;
    private List th;
    private List content;
    public JsonTableBean(String name) {
        th = new ArrayList<Map<String,String>>();
        content = new ArrayList<Map<String,String>>();
        this.name = name;
    }
    public void addHead(Map<Integer,String> head) {
        Map<String,String> map = new HashMap<>();
         for(Map.Entry<Integer, String> entry:head.entrySet()){
            map.put(String.valueOf(entry.getKey() + 1),entry.getValue());
         }
         map.put(Cons.ZERO_STR,Cons.JIN_SIGN);
         th.add(map);
    }
    public void addContent(List<Map<Integer,String>> list) {
        for (int i = 0; i < list.size(); i++) {
            Map<String,String> map = new HashMap<>();
            content.add(map);
            for(Map.Entry<Integer, String> entry:list.get(i).entrySet()){
                map.put(String.valueOf(entry.getKey() + 1),entry.getValue());
            }
            map.put(Cons.ZERO_STR,String.valueOf(i + 1));
        }
    }
}
