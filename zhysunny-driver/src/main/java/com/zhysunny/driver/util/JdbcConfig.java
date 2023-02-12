package com.zhysunny.driver.util;

import java.util.Locale;
import java.util.Map;

/**
 * 数据库连接配置
 * @author 章云
 * @date 2019/8/15 11:52
 */
public class JdbcConfig {
    private String thisDriver;
    private String className;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    public JdbcConfig(String className, String jdbcUrl, String jdbcUsername, String jdbcPassword) {
        setClassName(className);
        this.className = className;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public JdbcConfig(String[] array) {
        setClassName(array[0]);
        this.className = array[0];
        this.jdbcUrl = array[1];
        this.jdbcUsername = array[2];
        this.jdbcPassword = array[3];
    }

    public JdbcConfig(Constants.Driver driver, String jdbcUrl, String jdbcUsername, String jdbcPassword) {
        setClassName(driver.getClassName());
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public JdbcConfig(Map<String, String> config) {
        for (Map.Entry<String, String> entry : config.entrySet()) {
            String key = entry.getKey().toLowerCase(Locale.ENGLISH);
            String value = entry.getValue();
            if (key.contains("class")) {
                setClassName(value);
            } else if (key.contains("url")) {
                this.jdbcUrl = value;
            } else if (key.contains("user")) {
                this.jdbcUsername = value;
            } else if (key.contains("password") || key.contains("pwd")) {
                this.jdbcPassword = value;
            }
        }
    }

    public String getThisDriver() {
        return thisDriver;
    }

    public void setClassName(String className) {
        this.className = className;
        if (Constants.Driver.MYSQL.getClassName().equals(className)) {
            this.thisDriver = Constants.Driver.MYSQL.getName();
        } else if (Constants.Driver.ORACLE.getClassName().equals(className)) {
            this.thisDriver = Constants.Driver.ORACLE.getName();
        } else if (Constants.Driver.HIVE.getClassName().equals(className)) {
            this.thisDriver = Constants.Driver.HIVE.getName();
        } else if (Constants.Driver.PHOENIX.getClassName().equals(className)) {
            this.thisDriver = Constants.Driver.PHOENIX.getName();
        }
    }

    public String getClassName() {
        return className;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    @Override
    public String toString() {
        return "JdbcConfig{" + "thisDriver='" + thisDriver + '\'' + ", className='" + className + '\'' + ", jdbcUrl='" + jdbcUrl + '\''
        + ", jdbcUsername='" + jdbcUsername + '\'' + ", jdbcPassword='" + jdbcPassword + '\'' + '}';
    }
}
