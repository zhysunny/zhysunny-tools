package com.zhysunny.driver.mysql;

import com.zhysunny.driver.AbstractDMLanguage;
import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.bean.JdbcInputRecord;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 不兼容的dml语句
 * @author 章云
 * @date 2019/8/15 16:51
 */
public class MysqlDMLanguage extends AbstractDMLanguage {

    public MysqlDMLanguage(JdbcConnection conn) {
        super(conn);
    }

    @Override
    protected String createInsertSql(String tableName, List<JdbcInputRecord> records, Map<String, JdbcDataBaseColumn> dbColumns) {
        JdbcInputRecord record = records.get(0);
        List<String> columns = record.listColumns();
        StringBuffer sql = new StringBuffer(256);
        sql.append("INSERT INTO ").append(tableName).append('(');
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            sql.append(columns.get(i)).append(',');
        }
        if (sql.toString().endsWith(",")) {
            sql.delete(sql.length() - 1, sql.length());
            sql.append(')');
        }
        sql.append(" VALUES (");
        for (int i = 0; i < size; i++) {
            sql.append("?,");
        }
        if (sql.toString().endsWith(",")) {
            sql.delete(sql.length() - 1, sql.length());
            sql.append(')');
        }
        String insertSql = sql.toString().toUpperCase(Locale.ENGLISH);
        return insertSql;
    }

}
