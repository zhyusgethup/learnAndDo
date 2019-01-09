package copyToFile.analyse.template;

import java.util.HashMap;
import java.util.Map;

/***
 * 解析目标,找到目标的分隔符,如果没有,则返回-1;
 */
public class SeparatorAnalyse {
	public static final char[] separator = new char[]{' ','|',','};
    public char findSeparator(StringBuilder sb) {
    	int total = sb.length();
    	Map<Character, Integer> map = new HashMap<>();
    	if(total < 10000) {
    		for (int i = 0; i < sb.length(); i++) {
				
			}
    	}
        return  (char)-1;
    }
}
