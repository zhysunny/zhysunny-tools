package com.zhysunny.driver.filter.compare.impl;

import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.filter.compare.CompareDataBaseFilter;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 获取修改的字段结构
 * @author 章云
 * @date 2019/8/15 16:47
 */
public class CompareGetModify implements CompareDataBaseFilter {

    @Override
    public JdbcDataBase getDataBase(Map<String, JdbcDataBaseColumn> columns, JdbcDataBase newDataBase) {
        JdbcDataBase result = new JdbcDataBase(newDataBase.getName());// 修改
        Map<String, JdbcDataBaseColumn> newColumns = newDataBase.getColumns();
        JdbcDataBaseColumn newColumn = null;// 新字段
        String newColumnName = null;// 新字段名
        String newType = null;// 新字段类型
        String newLength = null;// 新字段长度
        JdbcDataBaseColumn column = null;// 老字段
        String type = null;// 老字段类型
        String length = null;// 老字段长度
        for (Entry<String, JdbcDataBaseColumn> newEntry : newColumns.entrySet()) {
            newColumn = newEntry.getValue();
            newColumnName = newColumn.getName();
            if (columns.containsKey(newColumnName)) {
                newType = newColumn.getType();
                newLength = newColumn.getLength();
                boolean newNotNull = newColumn.getNotNull();
                boolean isModify = false;// 是否修改
                boolean modifyNull = false;// 是否设置该值为true
                column = columns.get(newColumnName);
                type = column.getType();
                length = column.getLength();
                boolean notNull = column.getNotNull();
                boolean primaryKey = column.getPrimaryKey();
                if (!newType.equals(type) || !newLength.equals(length) || newNotNull != notNull) {
                    // 有一个类型不同就得修改字段
                    isModify = true;
                    if (notNull && !newNotNull) {
                        // 当必填项改为非必填时为true
                        modifyNull = true;
                    }
                }
                if (primaryKey && newType.equals(type) && newLength.equals(length) && newNotNull != notNull) {
                    // 当字段为主键且只有必填类型不相同，不修改字段
                    isModify = false;
                }
                if (isModify) {
                    if (modifyNull) {
                        newColumn.setModifyNull(true);
                    }
                    result.addColumn(newColumn);
                }
            }
        }
        return result;
    }

}
