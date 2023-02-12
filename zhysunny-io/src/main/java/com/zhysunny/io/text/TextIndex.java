package com.zhysunny.io.text;

import java.lang.annotation.*;

/**
 * 标识实体类属性对应的索引值
 * @author 章云
 * @date 2019/7/26 11:45
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TextIndex {

    /**
     * 索引值
     * @return
     */
    int value();

}
