package dataStruct.date;

import java.util.Comparator;
import java.util.TreeSet;

import com.sun.corba.se.spi.ior.ObjectId;

import utils.ObjectInstance;

public class DateComparactor {

	public static void main(String[] args) {
		long before = System.nanoTime();
		long after = System.nanoTime();
		TreeSet<D> set = new TreeSet<>(new Comparator<D>() {

			@Override
			public int compare(D o1, D o2) {
				return (int) (o1.getTime() - o2.getTime());
			}
		});
		set.add(new D(before, "before"));
		set.add(new D(after, "after"));
		System.out.println(set);
		try {
			D d = new ObjectInstance<D>().gener(D.class);
			System.out.println(d);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}

class D {
	long time;
	String name;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "D [time=" + time + ", name=" + name + "]";
	}

	public D(long time, String name) {
		super();
		this.time = time;
		this.name = name;
	}

}
