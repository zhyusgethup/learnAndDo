package zhyu.common.objectUtils;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Administrator
 *
 */
public class Assert extends org.springframework.util.Assert {
	public static void notBlank(String s) {
		notBlank(s, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	public static void notBlank(String s, String message) {
		if (StringUtils.isBlank(s)) {
			throw new IllegalArgumentException(message);
		}
	}
}
