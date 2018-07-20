package zhyu.common.objectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {

	
	/**
	 * URL地址正则判断表达�?
	 */
	public static Pattern URL_PATTERN = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-zA-Z_!~*'()-]+\\.)*([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-z]\\.[a-zA-Z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$");
	
	
	public static boolean isUrlMatcher(String url){
		if(url != null){
			Matcher m = URL_PATTERN.matcher(url);
			if(m.find()){
				return true;
			}
		}
		return false;
		
	}
	
/*	public static void main(String[] args) {
		System.out.println("hello");
		String url = "http://127.0.0.1/index.jsp";
		System.out.println(MatcherUtil.isUrlMatcher(null));
	}
	*/
	

}
