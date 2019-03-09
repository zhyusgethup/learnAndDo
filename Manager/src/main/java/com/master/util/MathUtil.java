package com.master.util;

import java.text.DecimalFormat;

public class MathUtil {
    public static final String RID = "###";
    public static final String TWO_RID = "###.##";
    public static final String FOUR_RID = "###.####";
    public static String ridDecimals(double d) {
        DecimalFormat df0 = new DecimalFormat(RID);
        return df0.format(d);
    }
    public static String saveTwoDecimals(double d) {
        DecimalFormat df0 = new DecimalFormat(TWO_RID);
        return df0.format(d);
    }
    public static String saveFourDecimals(double d) {
        DecimalFormat df0 = new DecimalFormat(FOUR_RID);
        return df0.format(d);
    }

    public static String saveDoubleMilAsMinute(Double mil) {
        double v = mil / 1000 / 60;
        if(v < 1.0)
            v = 1.0;
        return saveTwoDecimals(v);
    }
}
