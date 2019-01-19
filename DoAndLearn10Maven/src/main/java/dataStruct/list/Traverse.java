package dataStruct.list;

import java.util.ArrayList;
import java.util.List;

public class Traverse {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		for(int i = 0;i < list.size(); i++) {
			int j = list.get(i);
			if(j % 2 == 0){
				list.remove((Object)j);
				i--;
			}
		}
		System.out.println(list);
	}

}
