package zhyu.common.spring;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.ContextLoader;

@SuppressWarnings("all")
public class SpringBeanUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static MutablePropertySources propertySources;
	private static final SpringBeanUtils instance = new SpringBeanUtils();

	private SpringBeanUtils() {
	}

	public static SpringBeanUtils getInstance() {
		return instance;
	}

	private static void newInstance() {
		if (applicationContext == null) {
			synchronized (SpringBeanUtils.class) {
				if (applicationContext == null) {
					instance.setApplicationContext(ContextLoader.getCurrentWebApplicationContext());
				}
			}
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (this.applicationContext == null) {
			synchronized (SpringBeanUtils.class) {
				if (this.applicationContext == null) {
					this.applicationContext = applicationContext;
				}
			}
		}
	}

	/**
	 * 根据名称获取唯一Bean
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	/**
	 * 根据类型获取唯一Bean，若该类型存在多个Bean，则抛出异常
	 * 
	 * @param beanName
	 * @return
	 */
	public <T> T getBean(Class<T> beanType) {
		return (T) applicationContext.getBean(beanType);
	}

	/**
	 * 根据名称获取唯一Bean，若该名称对应Bean不存在，则根据类型获取Bean，若该类型存在多个Bean，则抛出异常
	 * 
	 * @param beanName
	 * @return
	 */
	public <T> T getBean(String beanName, Class<T> beanType) {
		Object obj = getBean(beanName);
		if (obj == null) {
			return (T) getBean(beanType);
		}
		return (T) obj;
	}

	/**
	 * 指定名称的Bean是否存在
	 * 
	 * @param beanName
	 * @return
	 */
	public boolean existsBean(String beanName) {
		return applicationContext.containsBean(beanName);
	}

	/**
	 * 指定名称的Bean的定义是否存在
	 * 
	 * @param beanName
	 * @return
	 */
	public boolean existsBeanDefinition(String beanName) {
		return applicationContext.containsBeanDefinition(beanName);
	}

	/**
	 * Return whether the local bean factory contains a bean of the given name,
	 * ignoring beans defined in ancestor contexts.
	 * <p>
	 * This is an alternative to {@code containsBean}, ignoring a bean of the
	 * given name from an ancestor bean factory.
	 * 
	 * @param name
	 *            the name of the bean to query
	 * @return whether a bean with the given name is defined in the local
	 *         factory
	 * @see BeanFactory#containsBean
	 */
	public boolean existsLocalBean(String beanName) {
		return applicationContext.containsLocalBean(beanName);
	}

	/**
	 * 是否存在指定类型的Bean
	 * 
	 * @param beanType
	 * @return
	 */
	public boolean existsBean(Class<?> beanType) {
		return applicationContext.getBeanNamesForType(beanType).length > 0;
	}

	/**
	 * 得到Spring ApplicationContext容器
	 * 
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public String getLocalProperty(String propertyKey) {
		return getLocalProperty(propertyKey, null);
	}

	public String getLocalProperty(String propertyKey, String defaultValue) {
		if (propertySources == null && applicationContext != null) {
			PropertySourcesPlaceholderConfigurer placeholder = applicationContext.getBean(PropertySourcesPlaceholderConfigurer.class);
			if (placeholder != null) {
				try {
					Class<?> clazz = placeholder.getClass();
					Field field = clazz.getDeclaredField("propertySources");
					field.setAccessible(true);
					Object obj = field.get(placeholder);
					if (obj != null && obj instanceof MutablePropertySources) {
						propertySources = (MutablePropertySources) obj;
					}
				} catch (Exception e) {
//					Constants.logger.error("得到localProperties：", e);
				}
			}
		}
		if (propertySources == null) {
			return defaultValue;
		} else {
			PropertySource<?> source = propertySources.get("localProperties");
			Object property = source.getProperty(propertyKey);
			return property == null ? defaultValue : property.toString();
		}
	}
	
	/**
	 * 得到缓存客户端
	 * 
	 * @return
	 */
//	public CacheDataService getCacheDataService() {
//		return getBean("memcacheDataServiceImpl", CacheDataService.class);
//	}

}
