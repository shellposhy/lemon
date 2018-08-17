package cn.com.lemon.framework.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.lemon.annotation.Column;
import cn.com.lemon.annotation.ID;

/**
 * Custom annotations tools.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class AnnotationHelper {

	private static final AnnotationHelper helper = new AnnotationHelper();

	public static AnnotationHelper getInstance() {
		return helper;
	}

	private AnnotationHelper() {
	}

	/**
	 * Gets all custom annotations for the specified class.
	 * 
	 * @param requiredType
	 * @return {@code Annotation[]}
	 */
	public <T> Annotation[] clazz(Class<T> requiredType) {
		Annotation[] result = requiredType.getDeclaredAnnotations();
		return result;
	}

	/**
	 * Gets all custom annotations for the specified class attribute.
	 * 
	 * @param requiredType
	 * @return {@code Field[]}
	 */
	public <T> Field[] filed(Class<T> requiredType) {
		Field[] fields = requiredType.getDeclaredFields();
		if (null != fields && fields.length > 0) {
			List<Field> list = new ArrayList<Field>();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(ID.class)) {
					field.setAccessible(true);
					list.add(field);
				}
			}
			if (list.size() > 0) {
				Field[] result = new Field[list.size()];
				return list.toArray(result);
			}
		}
		return null;
	}

	/**
	 * Gets all the variables and gets the annotation information on the
	 * specified field.
	 * 
	 * @param requiredType
	 * @param filedName
	 *            The class field name
	 * @return {@code Field} the annotation
	 */
	public <T> Field fieldValue(Class<T> requiredType, String filedName) {
		Field[] fields = filed(requiredType);
		Field field = null;
		for (Field f : fields) {
			if (f.getName().equals(filedName)) {
				field = f;
				break;
			}
		}
		return field;
	}

	/**
	 * Sets the value for the specified object.
	 * 
	 * @param t
	 * @param filedName
	 * @param value
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @return {@code T}
	 */
	public <T> T setFieldValue(T t, String filedName, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = t.getClass().getDeclaredFields();
		if (null != fields && fields.length > 0) {
			Field field = null;
			for (Field f : fields) {
				if (f.getName().equals(filedName)) {
					field = f;
					break;
				}
			}
			if (field != null) {
				field.setAccessible(true);
				String type = field.getType().toString();
				if (type.endsWith("String")) {
					field.set(t, value.toString());
				} else if (type.endsWith("int")) {
					field.setInt(t, (Integer) value);
				} else if (type.endsWith("Integer")) {
					field.set(t, (Integer) value);
				} else if (type.endsWith("Long")) {
					field.setLong(t, (Long) value);
				} else if (type.endsWith("Double")) {
					field.setDouble(t, (Double) value);
				} else if (type.endsWith("Short")) {
					field.setShort(t, (Short) value);
				} else if (type.endsWith("Float")) {
					field.setFloat(t, (Float) value);
				} else if (type.endsWith("BigDecimal")) {
					field.set(t, (BigDecimal) value);
				} else if (type.endsWith("Date")) {
					field.set(t, (Date) value);
				} else {
					field.set(t, value);
				}
			}
		}
		return t;
	}
}
