package com.zhysunny.driver.bean;

/**
 * Description: 记录字段类
 * @author 章云
 * @date 2018年6月23日 上午11:11:28
 */
public class JdbcInputColumn {
	// 字段名
	private String name;
	// 字段值
	private Object value;
	// 字段长度
	private int length = 0;

	/**
	 * Description: 构造函数
	 * @author 章云
	 * @date 2018年6月23日 上午11:20:09
	 */
	public JdbcInputColumn() {
		super();
	}

	/**
	 * Description: 构造函数
	 * @author 章云
	 * @date 2018年6月23日 上午11:20:14
	 * @param name
	 * @param value
	 */
	public JdbcInputColumn(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
		if (value != null) {
			this.length = value.toString().length();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		if (value != null) {
			this.length = value.toString().length();
		}
	}

	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "JdbcInputColumn [name=" + name + ", value=" + value + ", length=" + length + "]";
	}
}
