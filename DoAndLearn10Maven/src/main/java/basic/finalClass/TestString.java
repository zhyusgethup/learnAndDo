package basic.finalClass;

import java.util.HashMap;
import java.util.Map;

public class TestString {
	
	public static final String s0 = "123";

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		String s1 = "123";
		String s2 = new String("123");
		String s4 = new String("123");
		String s3 = "123";
		System.out.println(s4 == s2);
	}

}
