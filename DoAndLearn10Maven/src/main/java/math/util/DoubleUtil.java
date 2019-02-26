package math.util;

import java.text.DecimalFormat;

public class DoubleUtil {
	public static final String RID_ADD = "###.";
	public static final String RID = "###";
	public static void main(String[] args) {
		System.out.println(ridDecimals(266164.55555555556, 0));
	}
	/**
	 * 求小数的有效位数 自带四舍五入
	 * @param d
	 * @param count
	 * @return
	 */
    public static String ridDecimals(double d, int count) {
    	DecimalFormat df0 = null;
    	if(count == 0) {
    		df0 = new DecimalFormat(RID);
    	}else {
    		String st = RID_ADD;
    		for (int i = 0; i < count; i++) {
				st = st + "0";
			}
    		df0 = new DecimalFormat(st);
    	}
		return df0.format(d);
    }
}
