package dataStruct.combine;

import java.util.ArrayList;
import java.util.List;

/***
 * 这玩意儿要和List一样有序，像Set一样去重复
 * 最后 没这样的数据结构 但是可以有算法啊
 * 具体需求是重复时位置以最新的为准
 * @author Zeng Haoyu
 * 2018年12月14日 上午11:56:40
 */
public class ListAndSet {

	public static void main(String[] args) {
//		removeThenAdd();
		List<Integer> src = new ArrayList<>();
		Integer integer = new Integer(123);
		src.add(integer);
		src.add(integer);
		src.add(241);
		System.out.println(src);
		src.remove(integer);
		System.out.println(src);
	}

	private static void removeThenAdd() {
		List<Integer> src = new ArrayList<>();
		src.add(21312);
		src.add(241);
		src.add(8);
		src.add(1767654);
		src.add(123123);
		src.add(21312);
		src.add(21312);//这些数据是有顺序的， 已到obj中去
		List<Integer> obj = new ArrayList<>();
		for (Integer integer : src) {
			obj.remove((Object)integer);
			obj.add(integer);
		}
		System.out.println(obj);
	}

}
