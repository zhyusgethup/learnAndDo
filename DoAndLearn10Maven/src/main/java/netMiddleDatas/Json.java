package netMiddleDatas;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Json {

	public static void main(String[] args) {
		tableToJson();
	}
	
	public static void tableToJson(){
		Table<Integer, Integer, Integer> table = HashBasedTable.create();
		for(int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				table.put(i, j, i * j);
			}
		}
//		System.out.println(table);
		String jsonString = JSON.toJSONString(table);
		int size = table.column(0).size();
		Map<Integer,Map<Integer,Integer>> map = new HashMap<>();
		for (int i = 0; i < size; i++) {
			map.put(i, table.row(i));
		}
		System.out.println(JSON.toJSONString(map));
	}
}
