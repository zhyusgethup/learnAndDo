package zhyu.common.objectUtils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("all")
public final class Lists extends CollectionUtil{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Lists.class);
	
	private static ArrayList arrayList = new ArrayList(20);
	private static LinkedList linkedList = new LinkedList();
	
	public static List newList() {
		return Collections.EMPTY_LIST;
	}

	public static List newList(int size) {
		return newArraylist(size);
	}
	
	public static List newArraylist() {
		return (List)arrayList.clone();
	}
	
	public static List newArraylist(int size) {
		return new ArrayList(size);
	}
	
	public static List newLinkedlist() {
		return (List)linkedList.clone();
	}
	
	public static List newVector() {
		return newVector(0);
	}
	
	public static List newVector(int size) {
		return new Vector(size);
	}
	
	/**
	 * ������ת���List����.
	 * <p>A <code>null</code> source value will be converted to an
	 * empty List.
	 * @param source the (potentially primitive) array
	 * @return the converted List result
	 * @see ObjectUtil#toObjectArray(Object)
	 */
	public static List arrayToList(Object source) {
		return new ArrayList(Arrays.asList(ObjectUtil.toObjectArray(source)));
	}
	
	/**
	 * 克隆List
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> cloneList(List<T> list) {
		try{
			Class<?> clazz = list.getClass();
			Method method =  clazz.getDeclaredMethod("clone");
			if(method != null && method.isAccessible()){
				return (List<T>) method.invoke(list, null);
			}else{
				return new ArrayList<T>(list);
			}
		}catch (Exception e) {
			logger.error(e);
			return new ArrayList<T>(list);
		}
	}
	
	/**
	 * 根据指定字段升序排序
	 * 
	 * @author <a href="mailto:wangsl@tydic.com">王仕龙</a> 2013-11-19 下午6:31:25
	 * @param list
	 *            排序的数组
	 * @param column
	 *            排序的字段
	 */
	public static void sortByColumn(List<MapEntry> list, final String column) {
		sortByColumn(list, column, true);
	}
	
	/**
	 * 根据指定字段排序
	 * 
	 * @author <a href="mailto:wangsl@tydic.com">王仕龙</a> 2013-11-19 下午6:31:25
	 * @param list
	 *            排序的数组
	 * @param column
	 *            排序的字段
	 * @param isAsc
	 *            是否升序排列
	 */
	public static void sortByColumn(List<MapEntry> list, final String column, final boolean isAsc) {
		Collections.sort(list, new Comparator<MapEntry>() {
			public int compare(MapEntry o1, MapEntry o2) {
				if (o1.containsKey(column) && o2.containsKey(column)) {
					int compare = o1.getString(column).compareTo(o2.getString(column));
					/* 降序排列时,compare=0 返回0，compare=1返回-1，其他返回1 */
					return isAsc ? compare : (compare == 0 ? 0 : compare > 0 ? -1 : 1);
				} else if (!o1.containsKey(column) && !o2.containsKey(column)) {
					return isAsc ? 1 : -1;
				} else {
					if (o1.containsKey(column)) {
						return isAsc ? 1 : -1;
					} else {
						return isAsc ? -1 : 1;
					}
				}
			}
		});
	}
	
}
