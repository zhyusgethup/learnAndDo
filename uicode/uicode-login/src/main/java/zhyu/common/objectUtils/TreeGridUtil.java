package zhyu.common.objectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树-表格格式转换工具<br>
 * 用法: <br>
 * sql格式: select level, id, pid, name, desc from table
 * 
 * @author liujw
 * 
 */
public class TreeGridUtil {

	private static List<MapEntry<String, Object>> list;

	public static List<MapEntry<String, Object>> toTreeFormatList(List<MapEntry<String, Object>> originalList) {
		list = originalList;
		List<MapEntry<String, Object>> rslist = new ArrayList<MapEntry<String, Object>>();
		for (MapEntry<String, Object> rowmap : list) {
			String level = rowmap.getString("level");
			String id = rowmap.getString("id");
			if ("1".equals(level)) {
				List<MapEntry<String, Object>> childrenList = findChildren(id);
				if (childrenList != null && childrenList.size() > 0) {
					rowmap.put("children", childrenList);
				}
				rslist.add(rowmap);
			}
		}
		list = null;
		return rslist;
	}

	private static List<MapEntry<String, Object>> findChildren(String pid) {
		boolean hasChildren = false;
		List<MapEntry<String, Object>> rslist = new ArrayList<MapEntry<String, Object>>();
		for (MapEntry<String, Object> rowmap : list) {
			if (pid.equals(rowmap.getString("pid"))) {
				hasChildren = true;
				String id = rowmap.getString("id");
				List<MapEntry<String, Object>> childrenList = findChildren(id);
				if (childrenList != null && childrenList.size() > 0) {
					rowmap.put("children", childrenList);
				}
				rslist.add(rowmap);
			}
		}
		if (!hasChildren) {
			return null;
		}
		return rslist;
	}

}
