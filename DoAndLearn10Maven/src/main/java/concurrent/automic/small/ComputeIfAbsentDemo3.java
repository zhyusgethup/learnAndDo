package concurrent.automic.small;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.MacSpi;

/***
 * 利用computeIfAbsent 做出类似auto的原子更改状态的结构
 * @author Zeng Haoyu
 * 2018年12月18日 下午2:55:21
 */
public class ComputeIfAbsentDemo3 {
	static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
	public static void main(String[] args) {
		System.out.println(tryTroopEdit(1));
	}
	
	/***
	 * 
	 * @param src  
	 * @param type 希望被编辑的队伍type
	 * @return
	 */
	 private static boolean tryTroopEdit(int shouldBeEdit) {
	        int purpose = 1;
	        int state = 2;
	        if(map.containsValue(shouldBeEdit)) {
	            map.computeIfPresent(shouldBeEdit, (k,v) -> {
	               if(v == state) {
	                   return purpose;
	               }
	               return v;
	            });
	        }else {
	            map.computeIfAbsent(shouldBeEdit,(k) -> {
	                return purpose;
	            });
	        }
	        if(map.get(shouldBeEdit) == purpose)
	            return true;
	        return false;
	    }
}
