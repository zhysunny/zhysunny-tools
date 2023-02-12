package com.zhysunny.driver.bean;

import java.util.*;

/**
 * Description: 单条记录封装
 * 
 * @author 章云
 * @date 2018年6月23日 上午11:12:22
 */
public class JdbcInputRecord {
	// 记录ID
	private String uid;
	// 记录字段Map<字段名, 记录字段类>
	private Map<String, JdbcInputColumn> columns = new LinkedHashMap<>();// 记录所有字段

	/**
	 * Description: 构造函数
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:14:25
	 */
	public JdbcInputRecord() {
		super();
		this.uid = UUID.randomUUID().toString();
	}

	/**
	 * Description: 构造函数
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:14:40
	 * @param uid
	 * @param columns
	 */
	public JdbcInputRecord(String uid, Map<String, JdbcInputColumn> columns) {
		super();
		this.uid = uid;
		this.columns = columns;
	}

	/**
	 * Description: 添加记录字段类
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:15:10
	 * @param col
	 */
	public void addColumn(JdbcInputColumn col) {
		columns.put(col.getName(), col);
	}

	/**
	 * Description: 添加记录字段
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:15:33
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	public void addColumn(String name, Object value) {
		JdbcInputColumn col = new JdbcInputColumn(name, value);
		addColumn(col);
	}

	/**
	 * Description: 获取记录字段类
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:16:09
	 * @param name
	 *            字段名
	 * @return
	 */
	public JdbcInputColumn getColumn(String name) {
		return columns.get(name);
	}

	/**
	 * Description: 获取记录字段值
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:16:39
	 * @param name
	 *            字段名
	 * @return
	 */
	public String getColumnValue(String name) {
		JdbcInputColumn col = getColumn(name);
		String val = null;
		if (col != null) {
			val = col.getValue().toString();
		}
		return val;
	}

	/**
	 * Description: 获取所有记录字段类(List)
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:16:58
	 * @return
	 */
	public List<JdbcInputColumn> getAllColumns() {
		return new ArrayList<>(columns.values());
	}

	/**
	 * Description: 获取所有记录字段类(Map)
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:17:17
	 * @return
	 */
	public Map<String, JdbcInputColumn> getColumn() {
		return columns;
	}

	/**
	 * Description: 获取记录字段个数
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:17:42
	 * @return
	 */
	public int getColumnCount() {
		return columns.size();
	}

	/**
	 * Description: 获取所有记录字段名
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:18:00
	 * @return
	 */
	public List<String> listColumns() {
		return new ArrayList<>(columns.keySet());
	}

	/**
	 * Description: 删除记录字段
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:18:28
	 * @param name
	 *            字段名
	 * @return
	 */
	public JdbcInputColumn removeColumn(String name) {
		return columns.remove(name);
	}

	/**
	 * Description: 清空当前记录所有字段
	 * 
	 * @author 章云
	 * @date 2018年6月23日 上午11:18:40
	 */
	public void clear() {
		if (columns != null) {
			columns.clear();
		}
	}

	public String getUid() {
		return uid;
	}

}
