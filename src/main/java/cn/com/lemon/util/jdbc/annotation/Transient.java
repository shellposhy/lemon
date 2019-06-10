package cn.com.lemon.util.jdbc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略，如果数据库表中没有当前字段，则加上这个标识。
 * 
 * @author <a href="mailto:main_shorttime@163.com">tengfei.fangtf</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transient {

}
