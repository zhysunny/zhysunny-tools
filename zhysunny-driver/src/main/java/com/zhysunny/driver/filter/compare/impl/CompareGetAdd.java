package com.zhysunny.driver.filter.compare.impl;

import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.filter.compare.CompareDataBaseFilter;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 获取添加的字段结构
 * @author 章云
 * @date 2019/8/15 16:46
 */
public class CompareGetAdd implements CompareDataBaseFilter {

    @Override
    public JdbcDataBase getDataBase(Map<String, JdbcDataBaseColumn> columns, JdbcDataBase newDataBase) {
        JdbcDataBase result = new JdbcDataBase(newDataBase.getName());// 修改
        Map<String, JdbcDataBaseColumn> newColumns = newDataBase.getColumns();
        JdbcDataBaseColumn newColumn = null;// 新字段
        String newColumnName = null;// 新字段名
        for (Entry<String, JdbcDataBaseColumn> newEntry : newColumns.entrySet()) {
            newColumn = newEntry.getValue();
            newColumnName = newColumn.getName();
            if (!columns.containsKey(newColumnName)) {
                // 原表结构不包含新结构的字段需要添加
                result.addColumn(newColumn);
            }
        }
        return result;
    }

}
