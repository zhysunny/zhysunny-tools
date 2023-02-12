package com.zhysunny.driver;

import com.zhysunny.driver.bean.JdbcDataBase;

import java.sql.SQLException;
import java.util.List;

/**
 * DDL表结构定义语句接口
 * @author 章云
 * @date 2019/8/15 16:06
 */
public interface DataDefinitionLanguage {

    /**
     * 创建表
     * @param dataBase 表结构
     * @return
     * @throws SQLException
     */
    boolean createDatabase(JdbcDataBase dataBase) throws SQLException;

    /**
     * 删除表
     * @param tableName 表名
     * @return
     * @throws SQLException
     */
    boolean deleteDatabase(String tableName) throws SQLException;

    /**
     * 添加表结构
     * @param dataBase 需要添加的表结构字段
     * @return
     * @throws SQLException
     */
    boolean addColumns(JdbcDataBase dataBase) throws SQLException;

    /**
     * 修改表结构
     * @param dataBase 需要修改的表结构字段
     * @return
     * @throws SQLException
     */
    boolean modifyColumns(JdbcDataBase dataBase) throws SQLException;

    /**
     * 删除表结构
     * @param tableName 表名
     * @param columns   需要删除的表字段
     * @return
     * @throws SQLException
     */
    boolean dropColumns(String tableName, List<String> columns) throws SQLException;

    /**
     * 更新表结构
     * @param dataBase    原表结构
     * @param newDataBase 新表结构
     * @param isDrop      是否删除多余字段
     * @throws SQLException
     */
    void updateDataBase(JdbcDataBase dataBase, JdbcDataBase newDataBase, boolean isDrop) throws SQLException;

}
