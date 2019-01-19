package java8.functional;

import java.util.function.BiFunction;

public class BiFunctionDemo {

	public static void main(String[] args) {
		test((p1,p2)-> 1);
	}
	
	private static void test(BiFunction<Integer, Integer, Integer> biFunc) {
		int now = biFunc.apply(3, 4);
		System.out.println(now);
	}

}
