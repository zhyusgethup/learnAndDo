package java8.functional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainF {

	public static int compute4(int a, int b, BiFunction<Integer, Integer, Integer> biFunction,Function<Integer, Integer> function) {
	    return biFunction.andThen(function).apply(a, b);
	}
	static class Ce{
		private int a_id;
		int b_id;
		String name;
		public Ce(int a_id, int b_id, String name) {
			super();
			this.a_id = a_id;
			this.b_id = b_id;
			this.name = name;
		}
		public int getA_id() {
			return a_id;
		}
		public void setA_id(int a_id) {
			this.a_id = a_id;
		}
		public int getB_id() {
			return b_id;
		}
		public void setB_id(int b_id) {
			this.b_id = b_id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
		
	}
	
	public static void main(String[] args) {
		Map<Integer, Ce> map = new HashMap<>();
		map.put(1, new Ce(1, 1, "orange1"));
		map.put(2, new Ce(2, 3, "orange2"));
		map.put(5, new Ce(2, 6, "orange5"));
		map.put(3, new Ce(3, 2, "orange3"));
		map.put(4, new Ce(4, 5, "orange4"));
		 Map<Integer, List<Ce>> maps =map.values().stream().collect(Collectors.groupingBy(Ce::getA_id));
		System.out.println(maps);
		
	}

}
