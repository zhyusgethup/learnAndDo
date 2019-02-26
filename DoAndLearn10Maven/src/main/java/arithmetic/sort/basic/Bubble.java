package arithmetic.sort.basic;

import java.util.Arrays;
import java.util.Random;

public class Bubble {

	public static void main(String[] args) {
		int[] arr = new int[100];
		Random r = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt(10000);
		}
		System.out.println(Arrays.toString(arr));
		sort(arr);
	}
	public static void sort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr.length - 1 - i; j++) {
				if(arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}
}
