package dataStruct.set;

import java.util.HashSet;
import java.util.Set;

public class SetBasic {

	public static void main(String[] args) {
			removeDemo();
	}
	public static void removeDemo(){
		Set<Integer> src = new HashSet<>();
		Set<Integer> s1 = new HashSet<>();
		Set<Integer> s2 = new HashSet<>();
		for(int i = 0; i < 100; i++) {
			src.add(i);
			if(i % 2 == 0)
				s1.add(i);
			if(i % 5 == 0)
				s2.add(i);
		}
		Set<Integer> copy = new HashSet<>();
		copy.addAll(src);
		copy.removeAll(s1);
		copy.removeAll(s2);
		System.out.println("src:" + src);
		System.out.println("copy:" + copy);
		System.out.println("s1 :" + s1);
		System.out.println("s2 :" + s2);
		copy.removeAll(s2);
	}
}
