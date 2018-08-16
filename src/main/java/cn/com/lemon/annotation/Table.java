package cn.com.lemon.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies the primary table for the annotated entity.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
	/* The name of the table. Defaults to the property or field name */
	String value() default "";
}
