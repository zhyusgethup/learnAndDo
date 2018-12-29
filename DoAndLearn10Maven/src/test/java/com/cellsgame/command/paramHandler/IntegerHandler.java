package com.cellsgame.command.paramHandler;

public class IntegerHandler implements ParamParseHandler {
    private String name = "IntegerAndIntHandler";

    @Override
    public boolean match(Class cl) {
        return cl == Integer.class || cl == int.class;
    }

    @Override
    public Object parse(String word) {
        if (!isNumeric(word))
            return null;
        return Integer.valueOf(word);
    }

    @Override
    public String getName() {
        return name;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
