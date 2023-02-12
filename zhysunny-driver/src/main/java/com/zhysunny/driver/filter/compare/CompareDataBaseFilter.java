package com.zhysunny.driver.filter.compare;

import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;

import java.util.Map;

/**
 * 更新表结构接口
 * @author 章云
 * @date 2019/8/15 16:46
 */
public interface CompareDataBaseFilter {

    /**
     * 两个表结构对比，获取需要修改，删除，添加的字段结构
     * @param columns     原表结构
     * @param newDataBase 更新之后的表结构
     * @return
     */
    JdbcDataBase getDataBase(Map<String, JdbcDataBaseColumn> columns, JdbcDataBase newDataBase);

}
