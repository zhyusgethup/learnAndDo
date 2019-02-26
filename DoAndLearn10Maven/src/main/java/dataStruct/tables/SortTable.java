package dataStruct.tables;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

public class SortTable {

	public static TreeBasedTable<Integer, Integer, String> getTreeTable() {
		TreeBasedTable<Integer, Integer, String> table = TreeBasedTable.create((o1, o2) -> o1 - o2,
				(o1, o2) -> o1 - o2);
		table.put(0, 0, "姓名");
		table.put(0, 1, "年龄");
		table.put(0, 2, "兴趣");
		table.put(1, 0, "张三");
		table.put(1, 1, "10");
		table.put(1, 2, "体育");
		table.put(2, 0, "李四");
		table.put(2, 1, "11");
		table.put(2, 2, "运动");
		table.put(3, 0, "王五");
		table.put(3, 1, "20");
		table.put(3, 2, "打球");
		return table;
	}
	public static void main(String[] args) {
		TreeBasedTable<Integer, Integer, String> table = getTreeTable();
		SortedMap<Integer, String> row = table.row(0);
		System.out.println(row);
		for (Entry<Integer, String> entry : row.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String[] aStrings = changeSortMapToArray(row);
		System.out.println(Arrays.toString(aStrings));
		System.out.println(table.column(0).getClass());
	}
	
	 private static String[] changeSortMapToArray(SortedMap<Integer, String> map) {
	        if(null == map || map.size() == 0)
	            return null;
	        String[] array = new String[map.lastKey() + 1];
	        for (Map.Entry<Integer, String> entry : map.entrySet()) {
	            array[entry.getKey()]  = entry.getValue();
	        }
	        return array;
	 }
}
