package com.zhysunny.driver.filter.translation.impl;

import com.zhysunny.driver.filter.translation.TransDateFilter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date时间数据转换成String统一格式
 * @author 章云
 * @date 2019/8/15 16:48
 */
public class TransDateForString implements TransDateFilter {

	@Override
	public Object translation(Object value) {
		if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.format(value);
		}
		return value;
	}

}
