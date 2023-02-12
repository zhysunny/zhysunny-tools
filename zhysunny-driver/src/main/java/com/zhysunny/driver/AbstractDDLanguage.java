package com.zhysunny.driver;

import com.zhysunny.driver.filter.compare.CompareDataBaseFilter;
import com.zhysunny.driver.filter.compare.impl.CompareGetAdd;
import com.zhysunny.driver.filter.compare.impl.CompareGetDrop;
import com.zhysunny.driver.filter.compare.impl.CompareGetModify;
import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.util.Constants;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * DDL接口适配以及公共语句
 * @author 章云
 * @date 2019/8/15 16:37
 */
public abstract class AbstractDDLanguage implements DataDefinitionLanguage {

    protected JdbcConnection conn;
    protected DataManipulationLanguage dml;

    public AbstractDDLanguage(JdbcConnection conn, DataManipulationLanguage dml) {
        this.conn = conn;
        this.dml = dml;
    }

    @Override
    public final boolean createDatabase(JdbcDataBase dataBase) throws SQLException {
        boolean result = false;
        String tableName = dataBase.getName();
        List<JdbcDataBaseColumn> columns = dataBase.getAllColumns();
        StringBuffer sql = new StringBuffer(256);
        sql.append("CREATE TABLE ").append(tableName).append('(');
        String createSql = appendSql(columns, sql);
        conn.setExecuteSql("创建表：" + createSql);
        int count = dml.executeUpdate(createSql);
        if (count != -1) {
            result = true;
        }
        return result;
    }

    @Override
    public final boolean deleteDatabase(String tableName) throws SQLException {
        boolean result = false;
        String dropSql = "DROP TABLE " + tableName;
        conn.setExecuteSql("删除表：" + dropSql);
        int count = dml.executeUpdate(dropSql);
        if (count != -1) {
            result = true;
        }
        return result;
    }

    @Override
    public final boolean addColumns(JdbcDataBase dataBase) throws SQLException {
        boolean result = false;
        String tableName = dataBase.getName();
        List<JdbcDataBaseColumn> columns = dataBase.getAllColumns();
        StringBuffer sql = new StringBuffer(256);
        sql.append("ALTER TABLE ").append(tableName).append(" ADD(");
        String alterSql = appendSql(columns, sql);
        conn.setExecuteSql("添加字段：" + alterSql);
        int count = dml.executeUpdate(alterSql);
        if (count != -1) {
            result = true;
        }
        return result;
    }

    @Override
    public abstract boolean modifyColumns(JdbcDataBase dataBase) throws SQLException;

    /**
     * 删除表结构(重载)
     * @param dataBase
     * @return
     * @throws SQLException
     */
    public final boolean dropColumns(JdbcDataBase dataBase) throws SQLException {
        String tableName = dataBase.getName();
        List<String> columns = dataBase.listColumns();
        return dropColumns(tableName, columns);
    }

    @Override
    public abstract boolean dropColumns(String tableName, List<String> columns) throws SQLException;

    /**
     * 更新表结构(重载)
     * @param tableName
     * @param newDataBase
     * @param isDrop
     * @throws SQLException
     */
    public final void updateDataBase(String tableName, JdbcDataBase newDataBase, boolean isDrop) throws SQLException {
        JdbcDataBase dataBase = dml.getDatabases(tableName);
        updateDataBase(dataBase, newDataBase, isDrop);
    }

    @Override
    public final void updateDataBase(JdbcDataBase dataBase, JdbcDataBase newDataBase, boolean isDrop) throws SQLException {
        Map<String, JdbcDataBaseColumn> columns = dataBase.getColumns();
        // 1.比较获得需要修改的字段结构
        CompareDataBaseFilter modify = new CompareGetModify();
        JdbcDataBase dbModify = modify.getDataBase(columns, newDataBase);
        // 开始修改
        if (dbModify.getColumnCount() > 0) {
            modifyColumns(dbModify);
        }
        // 2.比较获得需要添加的字段结构
        CompareDataBaseFilter add = new CompareGetAdd();
        JdbcDataBase dbAdd = add.getDataBase(columns, newDataBase);
        // 开始添加
        if (dbAdd.getColumnCount() > 0) {
            addColumns(dbAdd);
        }
        // 3.比较获得需要删除的字段结构
        CompareDataBaseFilter drop = new CompareGetDrop();
        JdbcDataBase dbDrop = drop.getDataBase(columns, newDataBase);
        // 开始删除
        if (isDrop && dbDrop.getColumnCount() > 0) {
            dropColumns(dbDrop);
        }
    }

    /**
     * 建表语句，加字段语句，修改字段语句拼接
     * @param columns
     * @param sql
     * @return
     */
    protected final String appendSql(List<JdbcDataBaseColumn> columns, StringBuffer sql) {
        for (JdbcDataBaseColumn column : columns) {
            String name = column.getName();
            String type = column.getType();
            String length = column.getLength();
            sql.append(name).append(' ').append(type);
            if (null != length && !"".equals(length) && !"0".equals(length)) {
                sql.append('(').append(length).append(')');
            }
            if (column.getNotNull()) {
                sql.append(" not null");
            }
            if (column.getPrimaryKey()) {
                // 建表语句或者增加字段时才能设置主键
                // 增加字段时已用主键会异常
                sql.append(" primary key");
                if (type.contains(Constants.INT)) {
                    // mysql中int类型默认自增长
                    sql.append(" auto_increment");
                }
            }
            sql.append(',');
        }
        if (sql.toString().endsWith(",")) {
            sql.delete(sql.length() - 1, sql.length());
            sql.append(')');
        }
        return sql.toString().toUpperCase(Locale.ENGLISH);
    }

}
