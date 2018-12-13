package dataStruct.map;

import java.util.Comparator;
import java.util.TreeSet;

public class TreeSetAdd {

	public static void main(String[] args) {
		Pop p1 = new Pop("ϧԵ2018", 1, 1);
		Pop p2 = new Pop("ϧԵ2015", 1, 1);
		Pop p3 = new Pop("ϧԵ2016", 1, 1);
		Pop p4 = new Pop("ϧԵ2017", 1, 1);
		TreeSet<Pop> set = new TreeSet<Pop>(Comparator.comparing(Pop::getFightForce).reversed()
                .thenComparing(Comparator.comparing(Pop::getLevel)));
		set.add(p1);
		set.add(p2);
		set.add(p3);
		set.add(p4);
		System.out.println(set);
	}

}
class Pop {
	private String name;
	private int fightForce;
	private int level;
	public Pop(String name, int fightForce, int level) {
		super();
		this.name = name;
		this.fightForce = fightForce;
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFightForce() {
		return fightForce;
	}
	public void setFightForce(int fightForce) {
		this.fightForce = fightForce;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "Pop [name=" + name + ", fightForce=" + fightForce + ", level=" + level + "]";
	}
	
}
