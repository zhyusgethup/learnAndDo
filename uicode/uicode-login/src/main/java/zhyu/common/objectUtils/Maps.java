package zhyu.common.objectUtils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("all")
public final class Maps extends CollectionUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Maps.class);

	private static MapEntry entry = new MapEntry(20);
	private static LinkedMapEntry linkedMapEntry = new LinkedMapEntry(20);

	public static MapEntry newMap() {
		return (MapEntry) entry.clone();
	}

	public static MapEntry newMapEntry() {
		return (MapEntry) entry.clone();
	}

	public static Map newHashMap() {
		return (MapEntry) entry.clone();
	}

	public static LinkedMapEntry newLinkedHashMap() {
		return (LinkedMapEntry) linkedMapEntry.clone();
	}

	public static LinkedMapEntry newLinkedMapEntry() {
		return (LinkedMapEntry) linkedMapEntry.clone();
	}

	public static TreeMap newTreeMap() {
		return new TreeMap();
	}

	public static EnumMap newEnumMap(Map map) {
		return new EnumMap(map);
	}

	public static WeakHashMap newWeakHashMap() {
		return new WeakHashMap(20);
	}

	public static Hashtable newHashtable() {
		return new Hashtable(20);
	}

	public static IdentityHashMap newIdentityHashMap() {
		return new IdentityHashMap(20);
	}

	public static ConcurrentHashMap newConcurrentHashMap() {
		return new ConcurrentHashMap(20);
	}

	/**
	 * 删除指定Map中的指定Key
	 * 
	 * @param map
	 * @param removeKeys
	 * @return
	 */
	public static <T extends Map> T removeKeys(T map, Object... removeKeys) {
		for (Object removeKey : removeKeys) {
			map.remove(removeKey);
		}
		return map;
	}

	/**
	 * 创建一个新的Map, 包含指定Map中的指定Key
	 * 
	 * @param map
	 * @param removeKeys
	 * @return
	 */
	public static <T extends Map> Map newMapHasKeys(T map, Object... keys) {
		Map newMap = null;
		if (map instanceof HashMap) {
			newMap = (HashMap) ((HashMap) map).clone();
			newMap.clear();
		} else {
			newMap = new HashMap(20);
		}
		for (Object key : keys) {
			newMap.put(key, map.get(key));
		}
		return newMap;
	}

	/**
	 * 指定的key必须在指定的Map中存在，且指定的key的值不能为空或空字符
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isBlankKey(Map map, String key) {
		return !map.containsKey(key) || map.get(key) == null || StringUtils.isBlank(map.get(key).toString());
	}

	/**
	 * 指定的key必须在指定的Map中存在，且指定的key的值不能为空或空字符
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isNotBlankKey(Map map, String key) {
		return map.containsKey(key) && map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString());
	}

	/**
	 * 指定的key必须在指定的Map中存在，且指定的key的值不能为空或空字符
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	public static boolean isBlankKeys(Map map, String... keys) {
		for (String key : keys) {
			if (isBlankKey(map, key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定的key必须在指定的Map中存在，且指定的key的值不能为空或空字符
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	public static boolean isNotBlankKeys(Map map, String... keys) {
		for (String key : keys) {
			if (isNotBlankKey(map, key)) {
				return true;
			}
		}
		return false;
	}

	public static <K, V> MapEntry<K, V> cloneMapEntry(Map<K, V> map) {
		return cloneMapEntry(map, true);
	}

	public static <K, V> MapEntry<K, V> cloneMapEntry(Map<K, V> map, boolean rs) {
		if (!rs && map instanceof MapEntry)
			return (MapEntry<K, V>) ((MapEntry) map).clone();
		MapEntry maps = newMapEntry();
		maps.putAll(map);
		return maps;
	}

	/**
	 * 克隆Map
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V> Map<K, V> cloneMap(Map<K, V> map) {
		try {
			Class<?> clazz = map.getClass();
			Method method = clazz.getDeclaredMethod("clone");
			if (method != null && method.isAccessible()) {
				return (Map<K, V>) method.invoke(map, null);
			} else {
				return new MapEntry<K, V>(map);
			}
		} catch (Exception e) {
			logger.error(e);
			return new MapEntry<K, V>(map);
		}
	}

}
