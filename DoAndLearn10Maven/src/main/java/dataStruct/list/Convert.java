package dataStruct.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Convert {

	public static void main(String[] args) {
		List<Bob> bobs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Bob bob = new Bob(i);
			bobs.add(bob);
		}
		listToArray(bobs);
	}
	
	public static  void listToArray(List list) {
		Bob[] bobs = new Bob[list.size()];
		list.toArray(bobs);
		System.out.println(Arrays.toString(bobs));
		
	}
	static class Bob {
		private int id;
		Bob(int id) {this.id = id;}
		@Override
		public String toString() {
			return "Bob(" + id + ")";
		}
	}
}
