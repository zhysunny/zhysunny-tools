package com.zhysunny.driver.oracle;

import com.zhysunny.driver.AbstractDMLanguage;
import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.bean.JdbcInputRecord;
import com.zhysunny.driver.util.Constants;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 不兼容的dml语句
 * @author 章云
 * @date 2019/8/15 16:50
 */
public class OracleDMLanguage extends AbstractDMLanguage {

    private static final String GET_ONE_RECORD_SQL = "SELECT S.* FROM $tableName S WHERE ROWNUM=1";

    public OracleDMLanguage(JdbcConnection conn) {
        super(conn);
    }

    @Deprecated
    public JdbcDataBase getDatabases2(String tableName) throws SQLException {
        // 查询一条记录的SQL
        String sql = GET_ONE_RECORD_SQL.replace("$tableName", tableName);
        // 查询表记录数SQL
        String sqlCount = "SELECT COUNT(*) RECORDNUM FROM " + tableName;
        ResultSet rs = executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();// 获取查询结果集元信息
        JdbcDataBase db = new JdbcDataBase(tableName);// 创建新的表结构封装查询结果集
        int count = rsmd.getColumnCount();// 字段个数
        for (int i = 1; i <= count; i++) {
            String name = rsmd.getColumnName(i);// 字段名
            String type = rsmd.getColumnTypeName(i);// 字段类型
            JdbcDataBaseColumn column = new JdbcDataBaseColumn(name, type);// 创建新的字段
            int length = rsmd.getPrecision(i);// 字段长度
            int scale = rsmd.getScale(i);// 浮点数精度
            if (length >= 0) {
                column.setLength(String.valueOf(length));
            }
            if (scale > 0 && Constants.NUMBER.equalsIgnoreCase(type)) {
                column.setIsFloat(true, scale);
            }
            int notNull = rsmd.isNullable(i);// 是否必填
            if (notNull == 0) {
                column.setNotNull(true);
            }
            db.addColumn(column);
        }
        rs = executeQuery(sqlCount);
        if (rs.next()) {
            // 查询表记录数
            db.setRecordNum(rs.getLong("RECORDNUM"));
        }
        return db;
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
        String name = null;
        for (int i = 0; i < size; i++) {
            name = columns.get(i);
            if (dbColumns.containsKey(name)
                    && dbColumns.get(name).getType().equalsIgnoreCase(Constants.DATE)) {
                // 日期类型格式
                sql.append("TO_DATE(?,'yyyyMMddHH24miss'),");
            } else {
                sql.append("?,");
            }
        }
        if (sql.toString().endsWith(",")) {
            sql.delete(sql.length() - 1, sql.length());
            sql.append(')');
        }
        String insertSql = sql.toString().toUpperCase(Locale.ENGLISH);
        return insertSql;
    }

}
