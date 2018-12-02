package dataStruct.map;

import java.util.HashMap;
import java.util.Map;

public class PutAll {

	public static void main(String[] args) {
		Map<Integer, Integer> m1 = new HashMap<>();
		for(int i = 0; i < 10; i++){
			m1.put(i, i);
		}
		Map<Integer, Integer> m2 = new HashMap<>();
		m2.putAll(m1);
		m1.put(2, 4);
		System.out.println(m1);
		System.out.println(m2);
		int a = 13993+178+557+571+4038+158+929+244;
		System.out.println(a);
	}

}
