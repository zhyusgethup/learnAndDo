package arithmetic.sort.sortAndCat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import arithmetic.find.SmallFind;

public class GroupBy {
	static Map<Integer,LinkedList<VO>> map = new HashMap<>();
	public static void main(String[] args) {
		List<VO> arr = new ArrayList<>();
		for(int i = 1; i < 25; i++) {
			for(int j = 1; j < 11; j++) {
				VO vo = new VO(i * 100 + j);
				vo.setTier(i);
				vo.setScore(genScoreByTier(i));
				arr.add(vo);
			}
		}
		Collections.shuffle(arr);
		System.out.println(arr);
		for(int i = 0; i < arr.size(); i++) {
			addAndSort(arr.get(i));
		}
		System.out.println(map);
		int index= r.nextInt(arr.size());
		VO vo = arr.get(index);
		System.out.println(vo);
		LinkedList<VO> lis = map.get(vo.getTier());
		System.out.println(lis);
		
		vo.setScore(vo.getScore() + r.nextInt(100) - 50);
		removeAndSort(vo);
		System.out.println(vo);
		System.out.println(lis);
	}
	public static void removeAndSort(VO vo) {
		int tier = vo.getTier();
		LinkedList list = null;
		if(map.containsKey(tier)) {
			list = map.get(tier);
		}
		list.remove(vo);
		addAndSort(list, vo);
	}
	public static void addAndSort(VO vo) {
		int tier = vo.getTier();
		LinkedList list = null;
		if(map.containsKey(tier)) {
			list = map.get(tier);
		}else {
			list = new LinkedList<>();
			map.put(tier, list);
		}
		addAndSort(list,vo);
	}
	
	private static void addAndSort(LinkedList<VO> list, VO vo) {
		if(list.size() == 0){
			list.add(vo);
			return;
		}
		int index = SmallFind.colletionsBinary(list, vo.getScore());
		int in = index > -1?index:Math.abs(index) - 1;
		list.add(in, vo);
	}

	private static final Random r = new Random();
	private static int genScoreByTier(int tier) {
		int min = 100 * (tier - 1);
		return r.nextInt(100) + min;
	}
	public static class VO {
		private int id;
		private int tier;
		private int score;
		private int totalRank;
		private int tierRank;
		
		public VO(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getTier() {
			return tier;
		}

		public void setTier(int tier) {
			this.tier = tier;
		}

		public int getScore() {
			return score;
		}

		@Override
		public String toString() {
			return "VO [id=" + id + ", tier=" + tier + ", score=" + score + ", totalRank=" + totalRank + ", tierRank="
					+ tierRank + "]";
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getTotalRank() {
			return totalRank;
		}

		public void setTotalRank(int totalRank) {
			this.totalRank = totalRank;
		}

		public int getTierRank() {
			return tierRank;
		}

		public void setTierRank(int tierRank) {
			this.tierRank = tierRank;
		}
		
	}
}
