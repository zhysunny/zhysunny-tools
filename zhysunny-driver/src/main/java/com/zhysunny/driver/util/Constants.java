package com.zhysunny.driver.util;

/**
 * Driver工程常量类
 * @author 章云
 * @date 2019/8/15 11:49
 */
public class Constants {

    public static final String CHAR = "CHAR";// 固定长度，mysql最多255个字符,oracle 2000字节
    public static final String VARCHAR = "VARCHAR";// mysql可变长度0-65535字节
    public static final String VARCHAR2 = "VARCHAR2";// oracle可变长度最大4000字节
    public static final String STRING = "STRING";

    public static final String TINYINT = "TINYINT";// 1字节,-2^7~2^7-1,最大长度3
    public static final String SMALLINT = "SMALLINT";// 2字节,-2^15~2^15-1,最大长度5
    public static final String INT = "INT";// 4字节,-2^31~2^31-1,最大长度10
    public static final String BIGINT = "BIGINT";// 8字节,-2^63~2^63-1,最大长度19
    public static final String BYTE = "BYTE";
    public static final String SHORT = "SHORT";
    public static final String INTEGER = "INTEGER";
    public static final String LONG = "LONG";
    // 浮点型
    // float(5, 3)
    // 1.插入123.45678，最后查询得到的结果为99.999；
    // 2.插入123.456，最后查询结果为99.999；
    // 3.插入12.34567，最后查询结果为12.346；
    public static final String FLOAT = "FLOAT";// 4字节，单精度浮点型，m总个数，d小数位
    public static final String DOUBLE = "DOUBLE";// 8字节，双精度浮点型，m总个数，d小数位
    public static final String NUMBER = "NUMBER";

    // 通用常量，日期格式在Field类中定义
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String DATETIME = "DATETIME";

    // hive
    public static final String BOOLEAN = "BOOLEAN";

    public enum Driver {

        /**
         * oracle驱动
         */
        ORACLE("oracle.jdbc.driver.OracleDriver"),
        /**
         * mysql驱动
         */
        MYSQL("com.mysql.jdbc.Driver"),
        /**
         * hive驱动
         */
        HIVE("org.apache.hive.jdbc.HiveDriver"),
        /**
         * phoenix驱动
         */
        PHOENIX("org.apache.phoenix.jdbc.PhoenixDriver");
        private String className;

        Driver(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }

        public String getName() {
            return this.name();
        }

        public int getIndex() {
            return this.ordinal();
        }

    }

    public enum DateType {
        /**
         * 时间类型-日期
         */
        DATE("yyyy-MM-dd"),
        /**
         * 时间类型-时间
         */
        TIME("HH:mm:ss"),
        /**
         * 时间类型-日期时间
         */
        DATETIME("yyyy-MM-dd HH:mm:ss");

        private String value;

        DateType(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

    }

}
