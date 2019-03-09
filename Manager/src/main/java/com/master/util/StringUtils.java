package com.master.util;

public class StringUtils {
    public static final  String ZERO = "0";
    public static String replaceBlackTo0(String string){
        if(null == string || string.trim().equals("")){
            return ZERO;
        }
        return string;
    }
}
