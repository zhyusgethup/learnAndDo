package dataStruct.tables;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.TreeBasedTable;

public class RemoveMap {

	public static void main(String[] args) {
		TreeBasedTable<Integer, Integer, String> treeTable = 
				SortTable.getTreeTable();
//		System.out.println(treeTable);
		SortedMap<Integer, String> map = new TreeMap<>((o1, o2) -> o1 - o2);
		map.put(0, "何6");
		map.put(1, "30");
		map.put(2, "电脑");
		treeTable.row(4).putAll(map);
		SortedMap<Integer, String> map2 = new TreeMap<>((o1, o2) -> o1 - o2);
		map2.put(0, "何7");
		map2.put(1, "31");
		map2.put(2, "武术");
		treeTable.row(5).putAll(map2);
		System.out.println(treeTable);
		List<Integer> list = Arrays.asList(0,2,4);
		remove(list, treeTable);
		System.out.println(treeTable);
		combine(treeTable);
		System.out.println(treeTable);
	}
	public static void combine(TreeBasedTable<Integer, Integer, String> treeTable){
		int last = 5;
		int now = 2;
		for(int i = 0; i <= now; i++) {
			SortedMap<Integer,String> row = treeTable.row(i);
			if(row.isEmpty()){
				for (int j = i; j <= last; j++) {
					SortedMap<Integer, String> row2 = treeTable.row(j);
					if(row2.isEmpty())
						continue;
					treeTable.row(i).putAll(row2);
					row2.clear();
					break;
				}
			}
		}
	}
	
	public static void remove(List<Integer> list,TreeBasedTable<Integer, Integer, String> treeTable){
		for (int i = 0; i < list.size(); i++) {
			treeTable.row(i).clear();
		}
	}
	
	public static void basic(TreeBasedTable<Integer, Integer, String> treeTable){
		treeTable.row(0).clear();
		treeTable.row(1).clear();
		treeTable.row(2).clear();
		System.out.println(treeTable);
		treeTable.row(0).putAll(treeTable.row(3));
		treeTable.row(3).clear();
		treeTable.row(1).putAll(treeTable.row(4));
		treeTable.row(4).clear();
		treeTable.row(2).putAll(treeTable.row(5));
		treeTable.row(5).clear();
		System.out.println(treeTable);
	}

}
