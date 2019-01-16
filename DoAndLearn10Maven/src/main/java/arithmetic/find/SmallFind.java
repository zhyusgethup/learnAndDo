package arithmetic.find;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import arithmetic.sort.sortAndCat.GroupBy.VO;

public class SmallFind {
	private static Random r = new Random();
	public static void main(String[] args) {
		findInList();
	}
	public static void findInList() {
//		List list = new CopyOnWriteArrayList();
		List list = new ArrayList();
		int last = 0;
		for(int i = 0; i < 10000; i++) {
			VO vo = new VO(i);
			vo.setScore(last + r.nextInt(7) + 1);
			list.add(vo);
			last = vo.getScore();
		}
		Map<Integer, Long> map = new HashMap<>();
		getFindCounting(map, list,1);
		System.out.println("结果统计:" + map);
		long sum = 0;
		for (Integer i:map.keySet()) {
			sum += map.get(i);
		}
		System.out.println("平均花费时间: " + sum/map.size());
	}
	private static void getFindCounting(Map<Integer, Long> map, List list, int times) {
		for(int i = 0; i < times; i++ ){
			long start = System.nanoTime();
			int src = r.nextInt(70000);
			int index = colletionsBinary(list,src);
			long end = System.nanoTime();
			long cost = end - start;
//			System.out.println("花费时间" + cost);
//			System.out.println("search : "+  src + "  find index :" + index);
			index = Math.abs(index);
			System.out.println(index);
//			for(int j = index - 10; j < index + 11; j++) {
//				System.out.println(list.get(j));
//			}
			map.put(src,cost);
		}
	}
	public static int colletionsBinary(List list, int i) {
		VO vo = new VO(-1);
		vo.setScore(i);
		int index = Collections.binarySearch(list,vo,Comparator.comparingInt(VO::getScore));
		return index;
	}
}
