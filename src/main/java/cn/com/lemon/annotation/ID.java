package cn.com.lemon.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Database primary key field, used to identify the primary key identity when
 * data is updated or entered into the library.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface ID {
	/* The name of the column. Defaults to the property or field name */
	String value() default "";
}
