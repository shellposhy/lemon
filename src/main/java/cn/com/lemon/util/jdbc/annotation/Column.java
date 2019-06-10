package cn.com.lemon.util.jdbc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定表的字段名称，使用在GET方法上使用注释。
 * 
 * @author <a href="mailto:main_shorttime@163.com">tengfei.fangtf</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	public abstract String name();
}
