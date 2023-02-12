package com.zhysunny.io.properties;

/**
 * 自定义类型转换
 * @author 章云
 * @date 2019/7/27 11:43
 */
public abstract class AbstractTypeConversion<T> {

    protected String param;

    public AbstractTypeConversion(String param) {
        this.param = param;
    }

    /**
     * 将prop的value值转换成想要的类型
     * @param value
     * @return
     */
    public abstract T conversion(String value);

    /**
     * 将实体类特殊字段转成字符串
     * @param obj
     * @return
     */
    public abstract String toString(T obj);

}
