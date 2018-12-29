package arithmetic.sort.sortAndCat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 本类收录数量很小的排序
 * @author Zeng Haoyu
 * 2018年12月24日 下午9:07:27
 */
public class SmallSortAndCat {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		System.out.println(time);
		System.out.println(time/1000);
		String date = String.valueOf(time);
		System.out.println(date.length());
//		sortMapAndCatTop();
	}
	/**
	 * 排序map取出前3名
	 */
	public static void sortMapAndCatTop() {
		   Map<Integer, Integer> topThrees = new HashMap<>();
		   Random random = new Random();
		   for(int i = 0; i < 20; i++) {
			   topThrees.put(i, random.nextInt(100) + 1);
		   }
           Set<Map.Entry<Integer, Integer>> entries = topThrees.entrySet();
           List<Map.Entry<Integer,Integer>> li = new ArrayList<>(entries);
           Collections.sort(li,Comparator.comparingInt( x -> x.getValue()));
           System.out.println(li);
           System.out.println(topThrees);
	}
	
}
