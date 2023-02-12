package com.zhysunny.driver;

import com.zhysunny.driver.mysql.MysqlDDLanguage;
import com.zhysunny.driver.mysql.MysqlDMLanguage;
import com.zhysunny.driver.oracle.OracleDDLanguage;
import com.zhysunny.driver.oracle.OracleDMLanguage;
import com.zhysunny.driver.util.Constants;

/**
 * 数据驱动操作工厂
 * @author 章云
 * @date 2019/8/15 15:57
 */
public class LanguageFactory {

    private DataManipulationLanguage dml;
    private DataDefinitionLanguage ddl;

    /**
     * 创建DML对象
     * @param conn
     * @return
     */
    public DataManipulationLanguage getDMLanguage(JdbcConnection conn) {
        if (dml == null) {
            if (Constants.Driver.ORACLE.getName().equalsIgnoreCase(conn.getThisDriver())) {
                dml = new OracleDMLanguage(conn);
            } else if (Constants.Driver.MYSQL.getName().equalsIgnoreCase(conn.getThisDriver())) {
                dml = new MysqlDMLanguage(conn);
            }
        }
        return dml;
    }

    /**
     * 创建DDL对象
     * @param conn
     * @param dml
     * @return
     */
    public DataDefinitionLanguage getDDLanguage(JdbcConnection conn, DataManipulationLanguage dml) {
        if (ddl == null) {
            if (Constants.Driver.ORACLE.getName().equalsIgnoreCase(conn.getThisDriver())) {
                ddl = new OracleDDLanguage(conn, dml);
            } else if (Constants.Driver.MYSQL.getName().equalsIgnoreCase(conn.getThisDriver())) {
                ddl = new MysqlDDLanguage(conn, dml);
            }
        }
        return ddl;
    }

}
