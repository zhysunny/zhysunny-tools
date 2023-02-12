package com.zhysunny.driver.filter.translation.impl;

import com.zhysunny.driver.filter.translation.TransDateFilter;

/**
 * 字符串时间类型数据转换成TIME类型
 * @author 章云
 * @date 2019/8/15 16:48
 */
public class TransStringForTimeString implements TransDateFilter {

    @Override
    public Object translation(Object value) {
        if (value instanceof String) {
            if (value.toString().matches("\\d{2}")) {
                // HH
                value = value + "0000";
            }
            if (value.toString().matches("\\d{2}[.:]?\\d{2}")) {
                // HHmm
                value = value.toString().replaceAll("[.:]?", "") + "00";
            }
            if (value.toString().matches("\\d{2}[.:]?\\d{2}[.:]?\\d{2}")) {
                // HHmmss
                value = value.toString().replaceAll("[.:]?", "");
            }
            if (value.toString().matches("\\d{4}[-/.]?\\d{2}[-/.]?\\d{2}(\\ )?\\d{2}(:)?\\d{2}(:)?\\d{2}")) {
                // yyyyMMddHHmmss
                value = value.toString().replaceAll("[-/.:\\ ]?", "");
            }
        }
        return value;
    }

}
