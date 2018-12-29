package com.cellsgame.command.paramHandler;

/***
 * 该类禁止注入handler管理器中
 */
public class DefaultHandler implements  ParamParseHandler{
    String name = "defaultHandler";
    @Override
    public boolean match(Class cl) {
        return false;
    }

    @Override
    public Object parse(String word) {
       return defaultParse(word);
    }

    public static Object defaultParse(String word) {
        if(IntegerHandler.isNumeric(word)){
            return Integer.valueOf(word);
        }else {
            return StringHandler.parseString(word);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
