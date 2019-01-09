package com.cellsgame.command.paramHandler;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 该类禁止注入handler管理器中
 */
public class DefaultHandler implements  ParamParseHandler{
    String name = "defaultHandler";
    private static Table<Character,Character ,CombineHandler> combineHandlers;
    static {
        combineHandlers = HashBasedTable.create() ;
        ListHandler listHandler = new ListHandler();
        combineHandlers.put(listHandler.getBeforeChar(),listHandler.getEndChar(),listHandler);
    }

    public static String[] spit(String word) {
        boolean contains = false;
        for (int i = 0; i < word.length(); i++) {
            if(combineHandlers.rowKeySet().contains(word.charAt(i))){
                contains = true;
                break;
            }
        }
        if(!contains) {
            return word.split(",");
        }
        return null;
    }

    @Override
    public boolean match(Class cl) {
        return false;
    }

    @Override
    public Object parse(String word) {
       return defaultParse(word);
    }

    public static Object defaultParse(String word) {
        CombineHandler ch = null;
        if(IntegerHandler.isNumeric(word)){
            return Integer.valueOf(word);
        }else if((ch = wordIsInCareChars(word)) != null){
            return ch.parse(word);
        }
        else {
            return StringHandler.parseString(word);
        }
    }

    private static CombineHandler wordIsInCareChars(String word) {
            for(int j = 0; j < word.length(); j++) {
                if(combineHandlers.rowKeySet().contains(word.charAt(j))) {
//                    return combineHandlers.row(word.charAt(j)).get;
                }
            }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        String word = "213,23123,4324,[]";
        String[] spit = spit(word);
        System.out.println(Arrays.toString(spit));
    }
}
