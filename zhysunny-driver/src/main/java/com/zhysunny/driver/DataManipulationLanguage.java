package com.zhysunny.driver;

import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcInputRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DML数据操作语句接口
 * @author 章云
 * @date 2019/8/15 16:06
 */
public interface DataManipulationLanguage {

    /**
     * 插入数据
     * @param tableName 表名
     * @param records   记录
     * @return
     * @throws SQLException
     */
    int executeInsert(String tableName, List<JdbcInputRecord> records) throws SQLException;

    /**
     * 查询语句(Statement)
     * @param sql
     * @return
     * @throws SQLException
     */
    ResultSet executeQuery(String sql) throws SQLException;

    /**
     * 查询语句(PreparedStatement)
     * @param sql
     * @param args 参数
     * @return
     * @throws SQLException
     */
    ResultSet executeQuery(String sql, Object[] args) throws SQLException;

    /**
     * 更新或者删除语句(Statement)
     * @param sql
     * @return
     * @throws SQLException
     */
    int executeUpdate(String sql) throws SQLException;

    /**
     * 更新或者删除语句(PreparedStatement)
     * @param sql
     * @param args 参数
     * @return
     * @throws SQLException
     */
    int executeUpdate(String sql, Object[] args) throws SQLException;

    /**
     * 批量操作语句(PreparedStatement)
     * @param sql
     * @param argsList 批量参数
     * @return
     * @throws SQLException
     */
    int executeBatch(String sql, List<Object[]> argsList) throws SQLException;

    /**
     * 获取表结构
     * @param tableName 表名
     * @return
     * @throws SQLException
     */
    JdbcDataBase getDatabases(String tableName) throws SQLException;

    /**
     * 判断表结构是否包含某个字段
     * @param tableName 表名
     * @param column    字段名
     * @return
     * @throws SQLException
     */
    boolean containsColumn(String tableName, String column) throws SQLException;

}
