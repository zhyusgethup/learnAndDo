package dataStruct.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Sets;

public class ConcurrentDemo {

	public static void main(String[] args) {
//		final Set conSet = Sets.newConcurrentHashSet();
//		a2();
//		Set<Integer> set = Sets.newConcurrentHashSet();
		Set<Integer> set = new HashSet<>();
		for(int i = 0; i < 10000; i++) {
			set.add(i);
		}
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
		for(int i = 0; i < 3; i++) {
			fixedThreadPool.execute(new T1(set));
		}
		for(int i = 0; i < 3; i++) {
			fixedThreadPool.execute(new T2(set));
		}
		fixedThreadPool.shutdown();
	}
	static class T1 implements Runnable{
		Set<Integer> set;
		T1(Set<Integer> set) {
			this.set = set;
		}
		@Override
		public void run() {
			for(Integer integer :set) {
				System.out.println(integer);
//				try {
//					Thread.sleep(30);
//					int a = integer + 2;
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		}
		
	}
	static class T2 implements Runnable{
		Set<Integer> set;
		T2(Set<Integer> set) {
			this.set = set;
		}
		@Override
		public void run() {
			Iterator<Integer> it = set.iterator();
			while(it.hasNext()) {
				int m = it.next();
				if(m % 3 == 0)
					it.remove();
			}
		}
		
	}

	private static void a2() {
		final Set set = new HashSet<>();
		set.add(1);
		set.add(2);
		set.add(3);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
		for(int i = 0; i < 5000; i++) {
			fixedThreadPool.execute(new ConTask1(set, 1));
		}
		for(int i = 0; i < 5000; i++) {
			fixedThreadPool.execute(new Task(set, 2));
		}
		fixedThreadPool.shutdown();
	}
	
	static class ConTask1 implements Runnable {
		Set set;
		int pid;
		public ConTask1(Set set, int pid) {
			this.set = set;
			this.pid = pid;
		}
		@Override
		public void run() {
			boolean flag = false;
			synchronized (set) {
				if(set.contains(pid)){
					flag = true;
					set.remove(pid);
				}
			}
			if(flag){
				System.out.println("Con1领到奖励了");
			}
		}
		
	}
	
	static class Task implements Runnable {
		Set set;
		int pid;
		public Task(Set set, int pid) {
			this.set = set;
			this.pid = pid;
		}
		@Override
		public void run() {
			if(set.contains(pid) && set.remove(pid)) {
				System.out.println("Task领到奖励了");
			} else {
				System.out.println("Task mei领到奖励");
			}
		}
	}

}
