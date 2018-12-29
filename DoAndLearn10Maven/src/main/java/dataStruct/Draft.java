package dataStruct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Draft {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 1000; i++ ){
			list.add(i);
		}
//		for(Integer i: list){
//			if(i % 2 == 0) {
//				list.remove((Object)i);
//			}
//		}
		Iterator<Integer> iterable = list.iterator();
		while(iterable.hasNext()) {
			int next = iterable.next();
			if(next % 2 == 0){
				iterable.remove();
			}
		}
		System.out.println(list);
		System.out.println(list.size());
	}

}
