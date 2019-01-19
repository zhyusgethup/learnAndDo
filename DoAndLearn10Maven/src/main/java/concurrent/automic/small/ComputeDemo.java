package concurrent.automic.small;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ComputeDemo {

	public static void main(String[] args) {
		Map<Integer, Integer> map = new ConcurrentHashMap<>();
		map.put(1,1);
		map.put(2,2);
		map.put(3,3);
		map.put(4,4);
		map.put(5,5);
		computeIfPresent(map);
		putIfPresent(map);
		System.out.println(map);
	}
	public static void computeIfPresent(Map map) {
		map.computeIfPresent(1, (k,v)->{
			return null;
		});
	}
	public static void putIfPresent(Map map) {
		map.putIfAbsent(2, 1);
		map.putIfAbsent(6, 6);
	}
}
