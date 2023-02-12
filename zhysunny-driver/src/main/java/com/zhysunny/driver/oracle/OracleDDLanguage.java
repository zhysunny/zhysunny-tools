package com.zhysunny.driver.oracle;

import com.zhysunny.driver.AbstractDDLanguage;
import com.zhysunny.driver.DataManipulationLanguage;
import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * 不兼容的ddl语句
 * @author 章云
 * @date 2019/8/15 16:49
 */
public class OracleDDLanguage extends AbstractDDLanguage {

    public OracleDDLanguage(JdbcConnection conn, DataManipulationLanguage dml) {
        super(conn, dml);
    }

    @Override
    public boolean modifyColumns(JdbcDataBase dataBase) throws SQLException {
        //ALTER TABLE TEST MODIFY(字段1 类型1, 字段2 类型2)
        boolean result = false;
        String tableName = dataBase.getName();
        List<JdbcDataBaseColumn> columns = dataBase.getAllColumns();
        StringBuffer sql = new StringBuffer(256);
        sql.append("ALTER TABLE ").append(tableName).append(" MODIFY(");
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
            } else if (column.getModifyNull()) {
                sql.append(" null");
            }
            sql.append(',');
        }
        if (sql.toString().endsWith(",")) {
            sql.delete(sql.length() - 1, sql.length());
            sql.append(')');
        }
        String alterSql = sql.toString().toUpperCase(Locale.ENGLISH);
        conn.setExecuteSql("修改字段：" + alterSql);
        int count = dml.executeUpdate(alterSql);
        if (count != -1) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean dropColumns(String tableName, List<String> columns) throws SQLException {
        // ALTER TABLE DROP(字段1,字段2)
        boolean result = false;
        StringBuffer sql = new StringBuffer(256);
        sql.append("ALTER TABLE ").append(tableName).append(" DROP(");
        for (String column : columns) {
            sql.append(column).append(',');
        }
        if (sql.toString().endsWith(",")) {
            sql.delete(sql.length() - 1, sql.length());
            sql.append(')');
        }
        String dropSql = sql.toString().toUpperCase(Locale.ENGLISH);
        conn.setExecuteSql("删除字段：" + dropSql);
        int count = dml.executeUpdate(dropSql);
        if (count != -1) {
            result = true;
        }
        return result;
    }

}
