package com.cellsgame.command.paramHandler;

public interface ParamParseHandler {
    public boolean match(Class cl);
    public Object parse(String word);
    public String getName();
}
