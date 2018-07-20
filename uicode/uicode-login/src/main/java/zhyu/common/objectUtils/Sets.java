package zhyu.common.objectUtils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("all")
public class Sets extends CollectionUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Sets.class);

	private static HashSet set = new HashSet(20);
	private static LinkedHashSet linkedHashSet = new LinkedHashSet(20);
	private static TreeSet treeSet = new TreeSet();

	public static Set newSet() {
		return Collections.EMPTY_SET;
	}

	public static Set newHashSet() {
		return (Set) set.clone();
	}

	public static Set newLinkedHashSet() {
		return (Set) linkedHashSet.clone();
	}

	public static Set newTreeSet() {
		return (Set) treeSet.clone();
	}

	/**
	 * 克隆Set
	 * 
	 * @param set
	 * @return
	 */
	public static <T> Set<T> cloneSet(Set<T> set) {
		try {
			Class<?> clazz = set.getClass();
			Method method = clazz.getDeclaredMethod("clone");
			if (method != null && method.isAccessible()) {
				return (Set<T>) method.invoke(set, null);
			} else {
				return new HashSet<T>(set);
			}
		} catch (Exception e) {
			logger.error(e);
			return new HashSet<T>(set);
		}
	}

}
