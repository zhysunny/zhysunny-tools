package com.zhysunny.io.properties;

import java.lang.annotation.*;

/**
 * properties映射到常量类
 * @author 章云
 * @date 2019/7/27 11:05
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropKey {

    /**
     * 变量名
     * @return
     */
    String key();

    /**
     * 默认值，当原value值为null时使用默认值，如果只是空字符串不使用
     * @return
     */
    String defaultValue() default "";

    /**
     * 类型转化类
     * @return
     */
    String classpath() default "";

    /**
     * 可以提供转化类一个参数
     * @return
     */
    String param() default ",";

}
