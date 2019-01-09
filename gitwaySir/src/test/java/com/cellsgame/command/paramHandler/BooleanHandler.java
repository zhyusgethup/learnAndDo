package com.cellsgame.command.paramHandler;

public class BooleanHandler implements  ParamParseHandler{
    @Override
    public boolean match(Class cl) {
        return cl == Boolean.class;
    }

    @Override
    public Object parse(String word) {
        if(word.equals("0")){
            return false;
        }else if(word.equals("1")){
            return true;
        }else if(word.equals("true")) {
            return true;
        }else if(word.equals("false")) {
            return false;
        }
        throw new RuntimeException("值没法被转换");
    }

    @Override
    public String getName() {
        return "booleanHandler";
    }
}
