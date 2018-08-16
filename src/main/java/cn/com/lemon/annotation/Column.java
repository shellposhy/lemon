package cn.com.lemon.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Is used to specify the mapped column for a persistent property or field. If
 * no <code>Column</code> annotation is specified, the default values apply.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Column {
	/* The name of the column. Defaults to the property or field name */
	String value() default "";
}
