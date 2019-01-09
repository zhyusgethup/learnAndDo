package dataStruct.map;

import java.util.HashMap;
import java.util.Map;

public class PutAll {
	public static void main(String[] args) {
		Map<String, Object> m1 = new HashMap<>();
		for(int i = 0; i < 2; i++){
			Map sun = new HashMap<>();
			m1.put("s" + i, sun);
			for(int j = 0; j < 10; j++) {
				sun.put("j" + j, j);
			}
		}
		Map<String, Object> m2 = new HashMap<>();
		m2.putAll(m1);
		System.out.println(m1);
		System.out.println(m2);
		Map map = (Map) m2.get("s0");
		map.put("j11", 11);
		System.out.println(m2);
		System.out.println(m1);
	}

}
