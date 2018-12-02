package smallLogic.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 从Set中随机取出5个不重复的数字
 * @author Zeng Haoyu
 * 2018年11月23日 下午2:48:37
 */
public class GetRandomFromSet {

	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < 6; i++) {
			set.add(i);
		}
		Integer[] integers = new Integer[set.size()];
		integers = set.toArray(integers);
		int[] ints = new int[5];
		Random random = new Random();
		for(int i = 0; i < 5;) {
			int n = random.nextInt(integers.length);
			boolean flag = true;
			for(int j = 0; j < i; j++) {
				if(ints[j] == n) {
					flag = false;
					break;
				}
			}
			if(flag) {
				ints[i] = n;
				i++;
			}
		}
		System.out.println(Arrays.toString(ints));
	}

}
