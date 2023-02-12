package com.zhysunny.io.properties.impl;

import com.zhysunny.io.properties.AbstractTypeConversion;
import com.zhysunny.io.util.StringUtils;

/**
 * 字符串转数组
 * @author 章云
 * @date 2019/7/27 11:52
 */
public class StringArrayTypeConversion extends AbstractTypeConversion<String[]> {

    public StringArrayTypeConversion(String param) {
        super(param);
    }

    @Override
    public String[] conversion(String value) {
        return value.split(param, -1);
    }

    @Override
    public String toString(String[] obj) {
        return StringUtils.join(obj, param);
    }

}
