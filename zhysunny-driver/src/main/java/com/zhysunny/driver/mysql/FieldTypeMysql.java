package com.zhysunny.driver.mysql;

import com.zhysunny.driver.FieldType;
import com.zhysunny.driver.bean.Field;
import com.zhysunny.driver.util.Constants;

/**
 * mysql默认字段类型
 * @author 章云
 * @date 2019/8/15 15:18
 */
public class FieldTypeMysql implements FieldType {
    @Override
    public Field getStringField() {
        return new Field(Constants.VARCHAR, "255");
    }

    @Override
    public Field getByteField() {
        return new Field(Constants.TINYINT, "3");
    }

    @Override
    public Field getShortField() {
        return new Field(Constants.SMALLINT, "5");
    }

    @Override
    public Field getIntegerField() {
        return new Field(Constants.INT, "10");
    }

    @Override
    public Field getLongField() {
        return new Field(Constants.BIGINT, "19");
    }

    @Override
    public Field getFloatField() {
        return new Field(Constants.FLOAT, "12");
    }

    @Override
    public Field getDoubleField() {
        return new Field(Constants.DOUBLE, "22");
    }

    @Override
    public Field getCharField() {
        return new Field(Constants.CHAR, "1");
    }

    @Override
    public Field getDateField() {
        return new Field(Constants.DATE, "0");
    }

    @Override
    public Field getTimeField() {
        return new Field(Constants.TIME, "0");
    }

    @Override
    public Field getDatetimeField() {
        return new Field(Constants.DATETIME, "0");
    }

    @Override
    public Field getNumberField() {
        return new Field(Constants.INT, "10");
    }

    @Override
    public Field getBooleanField() {
        return new Field(Constants.CHAR, "5");
    }
}
