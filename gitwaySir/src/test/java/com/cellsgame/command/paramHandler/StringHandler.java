package com.cellsgame.command.paramHandler;

public class StringHandler implements ParamParseHandler{
    private Class handlerClass = String.class;
    private String name = "StringHandler";
    public boolean match(Class cl) {
        return cl == handlerClass;
    }
    public Object parse(String word){
       return parseString(word);
    }
    public static String parseString(String word) {
        if(word != null) {
            word = word.trim();
            if(word.length() == 0)
                return "";
            if(word.indexOf("\"") == 0 && word.lastIndexOf("\"") == word.length() - 1){
                return word.substring(1,word.length() - 1);
            }
            if(word.indexOf("'") == 0 && word.lastIndexOf("'") == word.length() - 1){
                return word.substring(1,word.length() - 1);
            }
            return word;
        }
        return "";
    }
    @Override
    public String getName() {
        return name;
    }
//
//    public static void main(String[] args) {
//        StringHandler sh = new StringHandler();
//        System.out.println(sh.parse("  "));
//        System.out.println(sh.parse("nihao"));
//        System.out.println((sh.parse("\"nihao\"")));
//        System.out.println((sh.parse("'nihao'")));
//        String word = "\"123123\"";
//    }

}
