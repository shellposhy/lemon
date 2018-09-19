package cn.com.lemon.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * Java reflection utility classes
 * <p>
 * 
 * @author shellpo shih
 * @version 1.0
 */
public final class Reflections {
	private Reflections() {
	}

	/**
	 * Method of obtaining a declaration.
	 * 
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return the declared {@code Method}
	 */
	public static Method method(Object object, String methodName, Class<?>... parameterTypes) {
		Method method = null;
		for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Call object methods directly, ignoring modifiers (private, protected,
	 * default)
	 * 
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return The result {@code Object} of the execution of the method in the
	 *         parent class.
	 */
	public static Object method(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		// The Method object is fetched according to the object, Method name and
		// corresponding Method parameters
		Method method = method(object, methodName, parameterTypes);
		// Inhibiting Java checking of methods, mainly for private methods
		method.setAccessible(true);
		try {
			if (null != method) {
				// The method of object is called to represent the method, whose
				// parameter is parameters
				return method.invoke(object, parameters);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * look up {@code Field} by field name
	 * 
	 * @param object
	 *            java {@code Object}
	 * @param fieldName
	 *            field name
	 * @return {@code Field}
	 */
	public static Field field(Object object, String fieldName) {
		Field field = null;
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Java reflection gets all the attributes in the class (including the
	 * parent)
	 * <p>
	 * The fields not contain static or transient {@code Field}
	 * 
	 * @param clazz
	 *            {@code Class}
	 * @return {@code List}
	 */
	public static List<Field> fields(Class<?> clazz) {
		return fields(clazz, true, true);
	}

	/**
	 * Java reflection gets all the attributes in the class (including the
	 * parent)
	 * 
	 * @param clazz
	 *            {@code Class}
	 * @param isStatic
	 *            is contain static {@code Field}
	 * @param isTransient
	 *            is transient static {@code Field}
	 * @return {@code List}
	 */
	public static List<Field> fields(Class<?> clazz, boolean isStatic, boolean isTransient) {
		if (null == clazz) {
			return null;
		}
		List<Field> fieldList = new LinkedList<Field>();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			if (null != fields && fields.length > 0) {
				for (Field field : fields) {
					if (isStatic && Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					if (isTransient && Modifier.isTransient(field.getModifiers())) {
						continue;
					}
					fieldList.add(field);
				}
			}
			// fieldList.addAll(Arrays.asList(fields));
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * Set the {@code Field} value
	 * 
	 * @param object
	 *            java {@code Object}
	 * @param fieldName
	 *            {@code Field} name
	 * @param value
	 *            the {@code Field} value
	 * @return
	 */
	public static void set(Object object, String fieldName, Object value) {
		Field field = field(object, fieldName);
		if (null != field) {
			field.setAccessible(true);
			try {
				field.set(object, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the {@code Field} value
	 * 
	 * @param object
	 *            java {@code Object}
	 * @param fieldName
	 *            {@code Field} name
	 * @return
	 */
	public static Object get(Object object, String fieldName) {
		Field field = field(object, fieldName);
		if (null != field) {
			field.setAccessible(true);
			try {
				return field.get(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
