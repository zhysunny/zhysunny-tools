package com.zhysunny.driver;

import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcInputRecord;
import com.zhysunny.driver.util.Constants;
import com.zhysunny.driver.util.JdbcConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * JDBC连接
 * @author 章云
 * @date 2019/8/15 15:38
 */
public class JdbcConnection {

    private static final String SELECT_ALL_SQL = "SELECT * FROM ";
    /**
     * 数据库连接
     */
    private Connection conn;
    /**
     * 数据库连接配置
     */
    private JdbcConfig config;
    /**
     * 执行过的sql
     */
    private Vector<String> executeSql;
    /**
     * 数据库管理(表数据操作)
     */
    private DataManipulationLanguage dml;
    /**
     * 数据库定义(表结构操作)
     */
    private DataDefinitionLanguage ddl;

    /******************************  构造函数开始 **********************************/
    public JdbcConnection(JdbcConfig config) throws SQLException, ClassNotFoundException {
        this.config = config;
        connection();
    }

    public JdbcConnection(String className, String jdbcUrl, String jdbcUsername, String jdbcPassword) throws SQLException, ClassNotFoundException {
        this.config = new JdbcConfig(className, jdbcUrl, jdbcUsername, jdbcPassword);
        connection();
    }

    public JdbcConnection(Constants.Driver driver, String jdbcUrl, String jdbcUsername, String jdbcPassword) throws SQLException, ClassNotFoundException {
        this.config = new JdbcConfig(driver, jdbcUrl, jdbcUsername, jdbcPassword);
        connection();
    }

    public JdbcConnection(Map<String, String> config) throws SQLException, ClassNotFoundException {
        this.config = new JdbcConfig(config);
        connection();
    }

    /******************************  构造函数结束 **********************************/

    /**
     * 获取数据库连接
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void connection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(config.getClassName());
            conn = DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUsername(), config.getJdbcPassword());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("数据库驱动加载失败", e);
        } catch (SQLException e) {
            throw new SQLException("数据库连接异常", e);
        }
        // 每次连接获取一个工厂实例
        LanguageFactory languageFactory = new LanguageFactory();
        // 创建dml,ddl对象
        this.dml = languageFactory.getDMLanguage(this);
        this.ddl = languageFactory.getDDLanguage(this, this.dml);
    }

    /**
     * 判断表是否存在
     * @param tableName 表名
     * @return
     */
    public boolean existsTable(String tableName) {
        ResultSet rs = null;
        boolean result = false;
        try {
            rs = dml.executeQuery(SELECT_ALL_SQL + tableName);
            result = null != rs ? true : false;
        } catch (SQLException e) {
        } finally {
            close(rs);
        }
        return result;
    }

    /**
     * 判断表是否有记录
     * @param tableName
     * @return
     */
    public boolean isEmptyTable(String tableName) {
        if (existsTable(tableName)) {
            boolean result = true;
            ResultSet rs = null;
            try {
                rs = dml.executeQuery(SELECT_ALL_SQL + tableName);
                result = (null != rs && rs.next()) ? false : true;
            } catch (SQLException e) {
            } finally {
                close(rs);
            }
            return result;
        }
        return false;
    }

    /**************************** DDL *************************************/

    /**
     * 建表
     * @param dataBase
     * @return
     */
    public boolean createDatabase(JdbcDataBase dataBase) throws SQLException {
        return ddl.createDatabase(dataBase);
    }

    /**
     * 无视记录数删除表
     * @param tableName
     * @return
     */
    public boolean deleteDatabase(String tableName) throws SQLException {
        if (existsTable(tableName)) {
            return ddl.deleteDatabase(tableName);
        }
        throw new SQLException(tableName + "表不存在");
    }

    /**
     * 删除记录数为0的表
     * @param tableName
     * @return
     */
    public boolean deleteDatabaseZero(String tableName) throws SQLException {
        if (isEmptyTable(tableName)) {
            return ddl.deleteDatabase(tableName);
        }
        throw new SQLException("表记录数不为0，不能删除表");
    }

    /**
     * 添加字段
     * @param dataBase
     * @return
     */
    public boolean addColumns(JdbcDataBase dataBase) throws SQLException {
        return ddl.addColumns(dataBase);
    }

    /**
     * 修改字段
     * @param dataBase
     * @return
     */
    public boolean modifyColumns(JdbcDataBase dataBase) throws SQLException {
        return ddl.modifyColumns(dataBase);
    }

    /**
     * 删除字段
     * @param dataBase
     * @return
     */
    public boolean dropColumns(JdbcDataBase dataBase) throws SQLException {
        return dropColumns(dataBase.getName(), dataBase.listColumns());
    }

    /**
     * 删除字段
     * @param tableName
     * @param columns
     * @return
     */
    public boolean dropColumns(String tableName, List<String> columns) throws SQLException {
        return ddl.dropColumns(tableName, columns);
    }

    /**
     * 更新字段(默认不删除)
     * @param tableName
     * @param newDataBase
     * @return
     */
    public void updateDataBase(String tableName, JdbcDataBase newDataBase) throws SQLException {
        updateDataBase(tableName, newDataBase, false);
    }

    /**
     * 更新字段(默认不删除)
     * @param dataBase
     * @param newDataBase
     * @return
     */
    public void updateDataBase(JdbcDataBase dataBase, JdbcDataBase newDataBase) throws SQLException {
        ddl.updateDataBase(dataBase, newDataBase, false);
    }

    /**
     * 更新字段
     * @param tableName
     * @param newDataBase
     * @param isDrop      是否删除字段
     * @return
     */
    public void updateDataBase(String tableName, JdbcDataBase newDataBase, boolean isDrop) throws SQLException {
        JdbcDataBase dataBase = dml.getDatabases(tableName);
        updateDataBase(dataBase, newDataBase, isDrop);
    }

    /**
     * 更新字段
     * @param dataBase
     * @param newDataBase
     * @param isDrop      是否删除字段
     * @return
     */
    public void updateDataBase(JdbcDataBase dataBase, JdbcDataBase newDataBase, boolean isDrop) throws SQLException {
        ddl.updateDataBase(dataBase, newDataBase, isDrop);
    }

    /**************************** DML *************************************/

    /**
     * Statement执行查询语句
     * @param sql
     * @return
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return dml.executeQuery(sql);
    }

    /**
     * Statement执行更新语句
     * @param sql
     * @return
     */
    public int executeUpdate(String sql) throws SQLException {
        return dml.executeUpdate(sql);
    }

    /**
     * PreparedStatement执行查询语句
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object[] args) throws SQLException {
        return dml.executeQuery(sql, args);
    }

    /**
     * PreparedStatement执行更新语句
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String sql, Object[] args) throws SQLException {
        return dml.executeUpdate(sql, args);
    }

    /**
     * PreparedStatement执行批量操作
     * @param sql
     * @param argsList
     * @throws SQLException
     */
    public int executeBatch(String sql, List<Object[]> argsList) throws SQLException {
        return dml.executeBatch(sql, argsList);
    }

    /**
     * 插入数据
     * @param tableName
     * @param records
     * @return
     */
    public int executeInsert(String tableName, List<JdbcInputRecord> records) throws SQLException {
        return dml.executeInsert(tableName, records);
    }

    /**
     * 获取表结构
     * @param tableName
     * @return
     */
    public JdbcDataBase getDatabases(String tableName) throws SQLException {
        return dml.getDatabases(tableName);
    }

    /**
     * 判断表是否含有某个字段
     * @param tableName
     * @param column
     * @return
     */
    public boolean containsColumn(String tableName, String column) throws SQLException {
        return dml.containsColumn(tableName, column);
    }

    /**************************** 关闭连接 *************************************/

    /**
     * 当前连接关闭
     */
    public void close() throws SQLException {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
    }

    /**
     * 关闭
     * @param sqlConns
     */
    public void close(AutoCloseable... sqlConns) {
        for (AutoCloseable sqlConn : sqlConns) {
            if (null != sqlConn) {
                try {
                    sqlConn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**************************** 获取信息 *************************************/

    public String getExecuteSql() {
        StringBuffer sb = new StringBuffer();
        if (executeSql != null) {
            sb.append("已执行SQL语句：\n");
            for (String sql : executeSql) {
                sb.append(sql).append('\n');
            }
        } else {
            sb.append("无SQL语句执行");
        }
        return sb.toString();
    }

    public void setExecuteSql(String sql) {
        if (this.executeSql == null) {
            this.executeSql = new Vector<String>();
        }
        this.executeSql.add(sql);
    }

    public Connection getConn() {
        return conn;
    }

    public JdbcConfig getConfig() {
        return config;
    }

    public String getThisDriver() {
        return config.getThisDriver();
    }

}
