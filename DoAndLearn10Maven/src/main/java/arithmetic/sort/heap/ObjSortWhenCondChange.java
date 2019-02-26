package arithmetic.sort.heap;

import java.util.Comparator;
import java.util.TreeSet;
/****
 * obj在容器中排序,没有特殊代码,容器中的顺序不会因为关键的更改而更改
 * @author Zeng Haoyu
 * 2019年2月26日 下午7:27:42
 */
public class ObjSortWhenCondChange {
	 public static TreeSet<HeroVO> sortHeros  = new TreeSet<>(Comparator.comparing(HeroVO::getValue).reversed());
	public static void main(String[] args) {
		sortHeros.add(new HeroVO(1, 300));
		sortHeros.add(new HeroVO(2, 100));
		sortHeros.add(new HeroVO(3, 320));
		HeroVO hvo = new HeroVO(4, 210);
		sortHeros.add(hvo);
		System.out.println(sortHeros);
		hvo.setValue(1000);
		System.out.println(sortHeros);
	}
	static class HeroVO {
		private int cid;
		private int value;
		public HeroVO(int cid, int value) {
			super();
			this.cid = cid;
			this.value = value;
		}
		@Override
		public String toString() {
			return "HeroVO [cid=" + cid + ", value=" + value + "]";
		}
		public int getCid() {
			return cid;
		}
		public void setCid(int cid) {
			this.cid = cid;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
	}

}
