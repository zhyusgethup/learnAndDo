package utils;

public class ObjectInstance<T>{
	public T gener(Class clasz) throws InstantiationException, IllegalAccessException {
		return (T)clasz.newInstance();
	}
	
}
