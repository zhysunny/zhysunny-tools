package com.zhysunny.driver.filter.translation.impl;

import com.zhysunny.driver.filter.translation.TransDateFilter;

import java.util.Date;

/**
 * long型时间数据转换成Date时间数据
 * @author 章云
 * @date 2019/8/15 16:48
 */
public class TransLongForDate implements TransDateFilter {

	@Override
	public Object translation(Object value) {
		if (value instanceof Long) {
			if (value.toString().length() == 10) {
				value = value + "000";
			}
			value = new Date((Long) value);
		}
		return value;
	}

}
