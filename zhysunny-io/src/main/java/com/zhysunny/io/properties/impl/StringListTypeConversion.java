package com.zhysunny.io.properties.impl;

import com.zhysunny.io.properties.AbstractTypeConversion;
import com.zhysunny.io.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 字符串转列表
 * @author 章云
 * @date 2019/7/27 11:53
 */
public class StringListTypeConversion extends AbstractTypeConversion<List<String>> {

    public StringListTypeConversion(String param) {
        super(param);
    }

    @Override
    public List<String> conversion(String value) {
        return Arrays.asList(value.split(param, -1));
    }

    @Override
    public String toString(List<String> obj) {
        return StringUtils.join(obj, param);
    }
}
