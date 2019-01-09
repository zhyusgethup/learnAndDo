package com.cellsgame.command.paramHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListHandler implements  CombineHandler{
    private String name= "listHandler";
    @Override
    public boolean match(Class cl) {
        return cl == List.class;
    }
    @Override
    public Object parse(String word) {
        if(word.indexOf(getBeforeChar()) == 0 && word.lastIndexOf(getEndChar()) == word.length() - 1){
            ArrayList list = new ArrayList();
            word = word.substring(1, word.length() - 1);
            if(word.length() == 0)
                return  list;
            String[] items = DefaultHandler.spit(word);
            if(null == items || items.length == 0)
                return list;
            for(int i = 0; i < items.length; i++) {
                list.add(DefaultHandler.defaultParse(items[i].trim()));
            }
            return list;
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        ListHandler handler = new ListHandler();
        String words = "[123456,21312, 23, [123,434]]";
        Object parse = handler.parse(words);
        System.out.println(parse);
    }

    @Override
    public char getBeforeChar() {
        return '[';
    }

    @Override
    public char getEndChar() {
        return ']';
    }
}
