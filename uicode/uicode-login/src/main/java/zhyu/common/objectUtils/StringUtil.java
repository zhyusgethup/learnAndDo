package zhyu.common.objectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StringUtil {

	public static String toSelectStr(List resultList, String key, String value) {
		StringBuilder selectStr = new StringBuilder();
		for (Object object : resultList) {
			Map _obj = (Map) object;
			selectStr.append("<option value='" + _obj.get(key) + "'>" + _obj.get(value) + "</option>");
		}
		return selectStr.toString();
	}

	public static String toString(Object object) {
		if (object != null) {
			return object.toString().trim();
		}
		return null;
	}

	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toLowerCase(firstChar) + tail;
		return str;
	}

	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toUpperCase(firstChar) + tail;
		return str;
	}

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * 字符串不为 null 而且不为 "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim()) ? false : true;
	}

	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}

	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}

	public static String nvl(String string) {
		return nvl(string, "");
	}
	public static String nvl(Object o) {
		return nvl(StringUtil.toString(o), "");
	}

	public static String nvl(String string, String string2) {
		if (StringUtil.isBlank(string)) {
			return string2;
		} else {
			return string;
		}
	}
	
	/*
	 * *
	 * add by xuyangyang 20140812
	 */
    public static String defaultString(String str) {
        return str == null ? "" : str;
    }

	public static Object minusOne(String str) {
		if(str != null && !"".equals(str)){
			str = str.substring(0, str.length()-1);
		}
		return str;
	}

	public static List<String> toList(String str, int len) {
		List<String> result = new ArrayList<String>();
		int end = len;
		String s;
		for (int start = 0; start < str.length();) {
			if (end > str.length()) {
				end = str.length();
			}
			s = str.substring(start, end);
			start = end;
			end = end + len;
			result.add(s);
		}
		return result;
	}
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
}
