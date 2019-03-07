package concurrent.modify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddDemo {

	public static void main(String[] args) {
		final Map<Integer, Object> data = new ConcurrentHashMap<>();
		Map<String, Object> info = new HashMap<>();
//		data.put("info", info);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5000);
		for (int i = 0; i < 20; i++) {
			fixedThreadPool.execute(new T1(data));
			fixedThreadPool.execute(new T2(data));
		}
		fixedThreadPool.shutdown();
	}
	
	static class T1 implements Runnable {
		Map<Integer, Object> data;
		T1(Map<Integer, Object> data) {
			this.data = data;
		}
		@Override
		public void run() {
			  Iterator<Entry<Integer, Object>> i$ = data.entrySet().iterator();

	            while(i$.hasNext()) {
	                Object o1 = i$.next();
	                Entry<Integer, Object> current = (Entry)o1;
	                Integer key = current.getKey();
	                Object value = current.getValue();
	                if(key % 2 == 0)
	                	data.remove(key);
	                System.out.println("key " + key +"   value =" + value);
	            }

		}
	}
	static class T2 implements Runnable {
		Map<Integer, Object> data;
		T2(Map<Integer, Object> data) {
			this.data = data;
		}
		@Override
		public void run() {
			for(int i = 0; i < 12000; i++) {
				data.put(i, i + 2000);
			}
		}
	}
}
