package concurrent.automic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class CompareAndSetDemo {

	public static void main(String[] args) {
		Count c = new Count();
		c.num = 123;
		boolean re = c.state.compareAndSet(c, 123, 5);
		System.out.println(re);
		System.out.println(c.num);

		Map<Integer, String> map = new HashMap<>();
		System.out.println(map.put(1,"1"));
		System.out.println(map.put(1,"2"));
		System.out.println(map.put(2,"3"));
	}

}
class Count{
	AtomicIntegerFieldUpdater<Count> state = AtomicIntegerFieldUpdater.newUpdater
			(Count.class, "num");
	volatile int num;
}