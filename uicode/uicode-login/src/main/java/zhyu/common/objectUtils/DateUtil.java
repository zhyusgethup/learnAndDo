package zhyu.common.objectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
	static SimpleDateFormat format2 = new SimpleDateFormat("MM");
	static SimpleDateFormat format3 = new SimpleDateFormat("d");
	static SimpleDateFormat format4 = new SimpleDateFormat("HH");
	static SimpleDateFormat format5 = new SimpleDateFormat("mm");
	static SimpleDateFormat format6 = new SimpleDateFormat("ss");
	static SimpleDateFormat format7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date now() {
		return new Date();
	}
	public static String nowStr() {
		return format7.format(new Date());
	}

	public static long getMilliseconds(Date t2, Date t1) {
		long l2 = t2.getTime();
		long l1 = t1.getTime();
		return l2 - l1;
	}

	public static String format(Date d) {
		return format.format(d);
	}
	public static String format(Date d,String fstr) {
		SimpleDateFormat f = new SimpleDateFormat(fstr);
		return f.format(d);
	}

	public static Map getDateMap(Date date) {
		HashMap m = new HashMap();
		m.put("Years", format1.format(date));
		m.put("Month", format2.format(date));
		m.put("Day", format3.format(date));
		m.put("Hours", format4.format(date));
		m.put("Minute", format5.format(date));
		m.put("Seconds", format6.format(date));
		return m;
	}

	public static Date getDate(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
		}
		return d;
	}

	public static boolean isSunday(String dayStr, String formatStr) {
		boolean r = false;
		Date d = getDate(dayStr,formatStr);
		if(d!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			if(c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				r = true;
			}
		}
		return r;
	}
}
