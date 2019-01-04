package streamDeal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

/**
 * 测试findAny的Optional 是否是纯随机
 * 发现结果时固定的,可能时最先放进去的那个
 * @author Zeng Haoyu
 * 2019年1月3日 上午10:53:23
 */
public class FindAnyDemo {

	public static void main(String[] args) {
		Map<Integer,A> map = new HashMap<>();
		for(int i = 0; i < 100; i++) {
			A a = new A(i);
			map.put(i, a);
		}
		final int lowerRange = 50;
		final int upperRange = 60;
		OptionalInt optional = map.values().stream().
                filter( cfg ->
                cfg.getRank() >= lowerRange && cfg.getRank() <= upperRange
        ).mapToInt( (cfg) -> cfg.getRank() ).findAny();

        if (optional.isPresent()) {
            System.out.println(optional.getAsInt());
        }
		 List list = map.values().stream().
                 filter( cfg ->
                 cfg.getRank() >= lowerRange && cfg.getRank() <= upperRange
         ).collect(Collectors.toList());
		 Set<Integer> set = random(list, 1);
		 int tid = 0;
		 if(null != set && set.size()!= 0){
             tid = set.iterator().next();
         }
		 System.out.println(tid);
        
	}
	  public static <T> Set<T> random(List<T> pool, int size) {
	        if (pool == null) return Collections.emptySet();
	        //
	        size = Math.max(1, size);
	        // 如果目标数量超过随机池大小
	        if (size >= pool.size()) return new HashSet<>(pool);
	        //
	        Collections.shuffle(pool);
	        //
	        int begin = nextInt(pool.size() - size + 1);
	        //
	        return Sets.newHashSet(pool.subList(begin, begin + size));
	    }
	  public static int nextInt(int excludeMax) {
	        return ThreadLocalRandom.current().nextInt(excludeMax);
	    }

}

class A {
	private int rank;
	A(int rank) {
		this.rank = rank;
	}
	@Override
	public String toString() {
		return String.valueOf(rank);
	}
	public int getRank(){return rank;}
}
