package com.zhysunny.driver;

import com.zhysunny.driver.filter.translation.TransDateFilter;
import com.zhysunny.driver.filter.translation.impl.TransDateForString;
import com.zhysunny.driver.filter.translation.impl.TransLongForDate;
import com.zhysunny.driver.filter.translation.impl.TransStringForDateString;
import com.zhysunny.driver.filter.translation.impl.TransStringForTimeString;
import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.bean.JdbcInputColumn;
import com.zhysunny.driver.bean.JdbcInputRecord;
import com.zhysunny.driver.util.Constants;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

/**
 * DML接口适配以及公共语句
 * @author 章云
 * @date 2019/8/15 16:41
 */
public abstract class AbstractDMLanguage implements DataManipulationLanguage {

    protected JdbcConnection conn;

    public AbstractDMLanguage(JdbcConnection conn) {
        this.conn = conn;
    }

    /**
     * 优点：个别字段不需要添加的话可以不添加，插入字段以records中的第一条记录为准
     * 缺点：如果records中记录的字段不一致，会存在丢某个字段数据或者错位 建议：records中字段名和字段顺序必须一致
     */
    @Override
    public final int executeInsert(String tableName, List<JdbcInputRecord> records) throws SQLException {
        int count = 0;
        if (!records.isEmpty()) {
            // 第一步，获取表结构
            JdbcDataBase db = getDatabases(tableName);
            Map<String, JdbcDataBaseColumn> dbColumns = db.getColumns();// 获取表字段
            // 第二步，去掉表结构中没有的字段，并重新封装新的入库记录
            Map<String, JdbcInputColumn> inputColumns = null;
            Map<String, JdbcInputColumn> newInputColumn = null;
            for (JdbcInputRecord record : records) {
                inputColumns = record.getColumn();
                newInputColumn = new LinkedHashMap<>();
                // 循环每个字段并进行对比
                for (Entry<String, JdbcInputColumn> entry : inputColumns.entrySet()) {
                    String inputColumnName = entry.getKey();// 需要插入数据库的字段名
                    if (dbColumns.containsKey(inputColumnName)) {
                        // 只有表结构字段包含的字段才要
                        newInputColumn.put(inputColumnName, entry.getValue());
                    }
                }
                // 重新封装记录
                record = new JdbcInputRecord(record.getUid(), newInputColumn);
            }
            // 第三步，封装批量插入的SQL语句
            String insertSql = createInsertSql(tableName, records, dbColumns);
            conn.setExecuteSql("添加记录：" + insertSql);
            List<Object[]> argsList = new ArrayList<>(records.size());// 把JdbcInputRecord封装成数组
            List<JdbcInputColumn> columnList = null;
            JdbcInputColumn column = null;
            for (JdbcInputRecord record : records) {
                columnList = record.getAllColumns();
                int size = columnList.size();
                Object[] args = new Object[size];
                for (int i = 0; i < size; i++) {
                    column = columnList.get(i);
                    args[i] = translation(column, dbColumns);// 翻译日期字段
                }
                argsList.add(args);
            }
            // 第四步，执行批量语句
            count = executeBatch(insertSql, argsList);
        }
        return count;
    }

    @Override
    public final ResultSet executeQuery(String sql) throws SQLException {
        ResultSet rs = conn.getConn()// 获取连接
        .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)// 设置ResultSet双向滚动,只读
        .executeQuery(sql);// 执行
        // 1.TYPE_FORWORD_ONLY,只可向前滚动；
        // 2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
        // 3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。
        // 1.CONCUR_READ_ONLY：表示只读。
        // 2.CONCUR_UPDATABLE：支持对数据库中表的修改。
        // 1.CLOSE_CURSORS_AT_COMMIT：当statement执行下个查询时，关闭结果集。
        // 2.HOLD_CURSORS_OVER_COMMIT：当statement执行下个查询时，不关闭结果集。
        return rs;
    }

    @Override
    public final ResultSet executeQuery(String sql, Object[] args) throws SQLException {
        PreparedStatement ps = conn.getConn()// 获取连接
        .prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);// 设置ResultSet双向滚动,只读,自动关闭结果集
        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
        }
        ResultSet rs = ps.executeQuery();
        conn.setExecuteSql("查询记录：" + sql);
        conn.setExecuteSql("\t参数：" + Arrays.toString(args));
        return rs;
    }

    @Override
    public final int executeUpdate(String sql) throws SQLException {
        int count;
        try {
            count = conn.getConn().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return count;
    }

    @Override
    public final int executeUpdate(String sql, Object[] args) throws SQLException {
        PreparedStatement ps = conn.getConn().prepareStatement(sql);
        int count = 0;
        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
        }
        try {
            count = ps.executeUpdate();
        } catch (SQLException e) {
            count = -1;
            throw new SQLException(e);
        } finally {
            ps.close();
        }
        if (sql.toUpperCase(Locale.ENGLISH).startsWith("UPDATE")) {
            conn.setExecuteSql("修改记录：" + sql);
        } else if (sql.toUpperCase(Locale.ENGLISH).startsWith("DELETE")) {
            conn.setExecuteSql("删除记录：" + sql);
        }
        conn.setExecuteSql("    参数：" + Arrays.toString(args));
        return count;
    }

    @Override
    public final int executeBatch(String sql, List<Object[]> argsList) throws SQLException {
        PreparedStatement ps = conn.getConn().prepareStatement(sql);
        int count = 0;
        try {
            if (argsList != null && !argsList.isEmpty()) {
                for (Object[] args : argsList) {
                    if (args != null && args.length != 0) {
                        for (int i = 0, len = args.length; i < len; i++) {
                            ps.setObject(i + 1, args[i]);
                        }
                        count++;
                        ps.addBatch();
                    }
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            count = -1;
            throw new SQLException(e);
        } finally {
            ps.close();
        }
        return count;
    }

    @Override
    public final JdbcDataBase getDatabases(String tableName) throws SQLException {
        // 查询表记录数SQL
        String sqlCount = "SELECT COUNT(*) RECORDNUM FROM " + tableName;
        DatabaseMetaData dbmd = conn.getConn().getMetaData();
        ResultSet rs = dbmd.getColumns(null, null, tableName, null);// 获取查询结果集元信息
        JdbcDataBase db = new JdbcDataBase(tableName);// 创建新的表结构封装查询结果集
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");// 字段名
            String type = rs.getString("TYPE_NAME");// 字段类型
            JdbcDataBaseColumn column = new JdbcDataBaseColumn(name, type);// 创建新的字段
            int scale = rs.getInt("DECIMAL_DIGITS");// 小数位数
            String length = rs.getString("COLUMN_SIZE");// 字段长度
            if (Integer.parseInt(length) >= 0) {
                column.setLength(length);
            }
            if (type.contains(Constants.DATE) || type.contains(Constants.TIME)) {
                // 时间类型可以不设置长度，默认0即可
                column.setLength("0");
            }
            if (scale > 0) {
                column.setIsFloat(true, scale);
            }
            int notNull = rs.getInt("NULLABLE");// 是否为空
            if (notNull == 0) {
                column.setNotNull(true);
            }
            db.addColumn(column);
        }
        // 找主键
        rs = dbmd.getPrimaryKeys(null, null, tableName);
        Map<String, JdbcDataBaseColumn> columns = null;
        while (rs.next()) {
            String primaryKeyColumnName = rs.getString("COLUMN_NAME");
            columns = db.getColumns();
            if (columns.containsKey(primaryKeyColumnName)) {
                columns.get(primaryKeyColumnName).setPrimaryKey(true);
            }
        }
        rs = executeQuery(sqlCount);
        if (rs.next()) {
            // 查询表记录数
            db.setRecordNum(rs.getLong("RECORDNUM"));
        }
        conn.close(rs);
        return db;
    }

    @Override
    public final boolean containsColumn(String tableName, String column) throws SQLException {
        DatabaseMetaData dbmd = conn.getConn().getMetaData();
        ResultSet rs = dbmd.getColumns(null, null, tableName, null);// 获取查询结果集元信息
        String name = null;
        boolean flag = false;
        while (rs.next()) {
            name = rs.getString("COLUMN_NAME");// 字段名
            if (name != null ? name.equalsIgnoreCase(column) : column == null) {
                flag = true;
                break;
            }
        }
        conn.close(rs);
        return flag;
    }

    /**
     * insert语句拼接
     * @param tableName
     * @param records
     * @param dbColumns
     * @return
     */
    protected abstract String createInsertSql(String tableName, List<JdbcInputRecord> records, Map<String, JdbcDataBaseColumn> dbColumns);

    /**
     * 日期类型字段翻译
     * @param column
     * @param dbColumns
     * @return
     */
    protected final Object translation(JdbcInputColumn column, Map<String, JdbcDataBaseColumn> dbColumns) {
        String name = column.getName();// 字段名
        Object value = column.getValue();
        if (dbColumns.containsKey(name)) {
            String type = dbColumns.get(name).getType();
            if (type.contains(Constants.DATE) || type.contains(Constants.TIME)) {
                // 1.long型时间数据转换成Date时间数据
                TransDateFilter longForDate = new TransLongForDate();
                value = longForDate.translation(value);
                // 2.字符串格式统一化
                TransDateFilter stringForString = null;
                if (type.equalsIgnoreCase(Constants.TIME)) {
                    // 只有时间部分
                    stringForString = new TransStringForTimeString();
                } else {
                    // 转成DATETIME
                    stringForString = new TransStringForDateString();
                }
                value = stringForString.translation(value);
                // 3.Date时间数据转换成String统一格式
                TransDateFilter dateForString = new TransDateForString();
                value = dateForString.translation(value);
            }
        }
        return value;
    }

}
