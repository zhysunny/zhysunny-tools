package com.zhysunny.driver.bean;

import com.zhysunny.driver.util.Constants;

/**
 * 字段类型
 * @author 章云
 * @date 2019/8/15 14:37
 */
public class Field {

    /**
     * 字段类型名
     */
    private String name;

    /**
     * 字段默认长度
     */
    private String length;

    /**
     * 日期类型才有用
     */
    private String format;

    public Field(){}

    public Field(String name, String length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getFormat() {
        return format;
    }

    public Field setFormat(String format) {
        this.format = format;
        return this;
    }
    public Field setFormat(Constants.DateType format) {
        this.format = format.value();
        return this;
    }
}
