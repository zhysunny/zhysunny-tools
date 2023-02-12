package com.zhysunny.driver;

import com.zhysunny.driver.bean.Field;

/**
 * 字段类型接口
 * @author 章云
 * @date 2019/8/15 13:58
 */
public interface FieldType {

    /**
     * 字符串
     * @return
     */
    Field getStringField();

    /**
     * 字节
     * @return
     */
    Field getByteField();

    /**
     * 短整数
     * @return
     */
    Field getShortField();

    /**
     * 整数
     * @return
     */
    Field getIntegerField();

    /**
     * 长整数
     * @return
     */
    Field getLongField();

    /**
     * 单精度浮点型
     * @return
     */
    Field getFloatField();

    /**
     * 双精度浮点型
     * @return
     */
    Field getDoubleField();

    /**
     * 定长字符串(不是单个字符),一般长度不大于255
     * @return
     */
    Field getCharField();

    /**
     * 日期
     * @return
     */
    Field getDateField();

    /**
     * 时间
     * @return
     */
    Field getTimeField();

    /**
     * 日期时间
     * @return
     */
    Field getDatetimeField();

    /******************************  oracle中的数值型字段  ************************************/
    /**
     * oracle中数值型，有长度决定类型
     * @return
     */
    Field getNumberField();

    /******************************  hive中的字段  ************************************/
    /**
     * 布尔型
     * @return
     */
    Field getBooleanField();

}
