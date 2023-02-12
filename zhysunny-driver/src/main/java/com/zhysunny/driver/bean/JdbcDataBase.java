package com.zhysunny.driver.bean;

import java.util.*;
import java.util.Map.Entry;

/**
 * 表结构，key值统一为大写
 * @author 章云
 * @date 2019/8/15 11:55
 */
public class JdbcDataBase {
    /**
     * 表名
     */
    private String name;
    /**
     * 表字段 Map<字段名, 表字段类>
     */
    private Map<String, JdbcDataBaseColumn> columns = new LinkedHashMap<String, JdbcDataBaseColumn>();
    /**
     * 表记录数
     */
    private long recordNum;

    public JdbcDataBase(String name) {
        super();
        this.name = name;
    }

    /**
     * 根据字段名获取表字段类
     * @param columnName 字段名
     * @return
     */
    public JdbcDataBaseColumn getColumn(String columnName) {
        return columns.get(columnName.toUpperCase(Locale.ENGLISH));
    }

    /**
     * 获取所有表字段类(List)
     * @return
     */
    public List<JdbcDataBaseColumn> getAllColumns() {
        return new ArrayList<JdbcDataBaseColumn>(columns.values());
    }

    /**
     * 获取所有表字段类(Map)
     * @return
     */
    public Map<String, JdbcDataBaseColumn> getColumns() {
        return columns;
    }

    /**
     * 获取所有字段名
     * @return
     */
    public List<String> listColumns() {
        return new ArrayList<String>(columns.keySet());
    }

    /**
     * 添加表字段类
     * @param column
     */
    public void addColumn(JdbcDataBaseColumn column) {
        columns.put(column.getName(), column);
    }

    /**
     * 获取表名
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取表字段个数
     * @return
     */
    public int getColumnCount() {
        return columns.size();
    }

    /**
     * 获取表记录数
     * @return
     */
    public long getRecordNum() {
        return recordNum;
    }

    /**
     * 设置表记录数
     * @param recordNum
     */
    public void setRecordNum(long recordNum) {
        this.recordNum = recordNum;
    }

    @Override
    public String toString() {
        if (columns == null || columns.isEmpty()) {
            return "JdbcDataBase [name=" + name + ", columns=" + columns + ", recordNum=" + recordNum + "]";
        } else {
            StringBuffer sb = new StringBuffer(256);
            sb.append("JdbcDataBase [name=").append(name).append(", recordNum=").append(recordNum).append('\n');
            sb.append("columns [\n");
            for (Entry<String, JdbcDataBaseColumn> entry : columns.entrySet()) {
                sb.append('\t').append(entry.getValue()).append('\n');
            }
            sb.append(']');
            return sb.toString();
        }
    }

}
