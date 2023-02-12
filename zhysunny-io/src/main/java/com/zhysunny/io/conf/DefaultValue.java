package com.zhysunny.io.conf;

import java.lang.annotation.*;

/**
 * 默认值
 * @author 章云
 * @date 2020/1/2 14:02
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultValue {

    String value();

}
