package com.cellsgame.command.paramHandler;

import java.util.HashMap;
import java.util.Map;

public class MapHandler implements ParamParseHandler {
    private String name = "mapHandler";
    @Override
    public boolean match(Class cl) {
        return cl == Map.class;
    }

    @Override
    public Object parse(String word) {
        if(word.indexOf("{") == 0 && word.lastIndexOf("}") == word.length() - 1) {
            word = word.substring(1,word.length() - 1);
            Map<String, Object> map = new HashMap<>();
            if(word.length() == 0)
                return map;
            String[] groups = word.split(",");
            for(int i = 0; i < groups.length; i++) {
                String group = groups[i];
                String[] pars = group.trim().split(":");
                for(int j = 0; j < pars.length;j += 2) {
                    String parName = pars[j];
                    String parData = pars[j + 1];
                    map.put(parName, DefaultHandler.defaultParse(parData));
                }
            }
            return map;
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        String word = "{}";
        System.out.println(new MapHandler().parse(word));
    }
}
