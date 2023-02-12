package com.zhysunny.driver.filter.compare.impl;

import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.filter.compare.CompareDataBaseFilter;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 获取删除的字段结构
 * @author 章云
 * @date 2019/8/15 16:47
 */
public class CompareGetDrop implements CompareDataBaseFilter {

    @Override
    public JdbcDataBase getDataBase(Map<String, JdbcDataBaseColumn> columns, JdbcDataBase newDataBase) {
        JdbcDataBase result = new JdbcDataBase(newDataBase.getName());// 修改
        Map<String, JdbcDataBaseColumn> newColumns = newDataBase.getColumns();
        // 删除字段循环原表结构
        JdbcDataBaseColumn column = null;// 原字段
        String columnName = null;// 原字段名
        for (Entry<String, JdbcDataBaseColumn> entry : columns.entrySet()) {
            column = entry.getValue();
            columnName = column.getName();
            if (!newColumns.containsKey(columnName)) {
                // 新表结构不包含原结构的字段需要删除
                result.addColumn(column);
            }
        }
        return result;
    }

}
