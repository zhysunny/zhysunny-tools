package com.zhysunny.driver.filter.translation;

/**
 * 日期类型的数据翻译接口
 * @author 章云
 * @date 2019/8/15 16:47
 */
public interface TransDateFilter {

    /**
     * 字段翻译
     * @param value
     * @return
     */
    Object translation(Object value);

}
