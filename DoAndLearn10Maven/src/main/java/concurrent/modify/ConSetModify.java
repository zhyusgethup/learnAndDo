package concurrent.modify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Sets;
/***
 * Set的并发安全考虑google的Sets.newConcurrentHashSet();
 * @author Zeng Haoyu
 * 2019年2月27日 下午4:25:36
 */
public class ConSetModify {
	private static Map<Integer,Boolean > map = new HashMap<>();
    private static Set<Integer> info = Sets.newConcurrentHashSet();

	public static void main(String[] args) {
//		data.put("info", info);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5000);
		for (int i = 0; i < 1; i++) {
			fixedThreadPool.execute(new Add());
			fixedThreadPool.execute(new Remove());
//			fixedThreadPool.execute(new Clear());
		}
		fixedThreadPool.shutdown();
	}
	
	static class Remove implements Runnable {
		@Override
		public void run() {
			  Iterator<Integer> i$ = info.iterator();

	            while(i$.hasNext()) {
	                Integer o1 = i$.next();
	                if(o1 % 2 == 0)
	                	i$.remove();
//	                	i$.remove();
	                System.out.println("key " + o1);
	            }

		}
	}
	static class Add implements Runnable {
		@Override
		public void run() {
			for(int i = 0; i < 12000; i++) {
				info.add(i);
			}
		}
	}
	static class Clear implements Runnable {
		@Override
		public void run() {
			for(int i = 0; i < 12000; i++) {
				info.clear();
			}
		}
	}
}
