package com.zhysunny.driver.oracle;

import com.zhysunny.driver.FieldType;
import com.zhysunny.driver.bean.Field;
import com.zhysunny.driver.util.Constants;

/**
 * oracle默认字段类型
 * @author 章云
 * @date 2019/8/15 15:18
 */
public class FieldTypeOracle implements FieldType {
    @Override
    public Field getStringField() {
        return new Field(Constants.VARCHAR2, "255");
    }

    @Override
    public Field getByteField() {
        return new Field(Constants.NUMBER, "3");
    }

    @Override
    public Field getShortField() {
        return new Field(Constants.NUMBER, "5");
    }

    @Override
    public Field getIntegerField() {
        return new Field(Constants.NUMBER, "10");
    }

    @Override
    public Field getLongField() {
        return new Field(Constants.NUMBER, "19");
    }

    @Override
    public Field getFloatField() {
        return new Field(Constants.NUMBER, "(10,2)");
    }

    @Override
    public Field getDoubleField() {
        return new Field(Constants.NUMBER, "(20,2)");
    }

    @Override
    public Field getCharField() {
        return new Field(Constants.CHAR, "1");
    }

    @Override
    public Field getDateField() {
        return new Field(Constants.DATE, "0").setFormat(Constants.DateType.DATE);
    }

    @Override
    public Field getTimeField() {
        return new Field(Constants.DATE, "0").setFormat(Constants.DateType.TIME);
    }

    @Override
    public Field getDatetimeField() {
        return new Field(Constants.DATE, "0").setFormat(Constants.DateType.DATETIME);
    }

    @Override
    public Field getNumberField() {
        return new Field(Constants.NUMBER, "5");
    }

    @Override
    public Field getBooleanField() {
        return new Field(Constants.CHAR, "5");
    }
}
