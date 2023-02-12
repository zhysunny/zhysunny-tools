package com.zhysunny.driver.filter.translation.impl;

import com.zhysunny.driver.filter.translation.TransDateFilter;

/**
 * 字符串时间类型数据转换成DATETIME类型
 * @author 章云
 * @date 2019/8/15 16:48
 */
public class TransStringForDateString implements TransDateFilter {

    @Override
    public Object translation(Object value) {
        if (value instanceof String) {
            if (value.toString().matches("\\d{4}")) {
                // yyyy
                value = value + "0101000000";
            }
            if (value.toString().matches("\\d{4}[-/.]?\\d{2}")) {
                // yyyyMM
                value = value.toString().replaceAll("[-/.]?", "") + "01000000";
            }
            if (value.toString().matches("\\d{4}[-/.]?\\d{2}[-/.]?\\d{2}")) {
                // yyyyMMdd
                value = value.toString().replaceAll("[-/.]?", "") + "000000";
            }
            if (value.toString().matches("\\d{4}[-/.]?\\d{2}[-/.]?\\d{2}(\\ )?\\d{2}(:)?\\d{2}(:)?\\d{2}")) {
                // yyyyMMddHHmmss
                value = value.toString().replaceAll("[-/.:\\ ]?", "");
            }
        }
        return value;
    }

}
