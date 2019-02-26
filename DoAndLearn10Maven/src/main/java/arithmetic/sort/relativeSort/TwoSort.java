package arithmetic.sort.relativeSort;

import java.util.Arrays;
import java.util.Random;

public class TwoSort {

	public static void main(String[] args) {
		String[] names = new String[]{"apple,orange,bunana,icecream,strawberry"};
		int[] arrs = new int[names.length];
		Random r = new Random();
		for (int i = 0; i < names.length; i++) {
			arrs[i] = r.nextInt(10000);
		}
		System.out.println(Arrays.toString(arrs));
		
	}

}
