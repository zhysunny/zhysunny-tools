package com.zhysunny.transfer.constant;

import com.zhysunny.driver.util.JdbcConfig;
import com.zhysunny.io.properties.PropKey;
import com.zhysunny.io.properties.PropertiesConstant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 任务配置
 * @author 章云
 * @date 2019/8/15 20:29
 */
public class Constants implements PropertiesConstant {

    /**
     * 映射文件，用于数据转移字段的映射
     */
    @PropKey(key = "mapping.file")
    public static String MAPPING_FILE;

    /**
     * 数据源类型
     */
    @PropKey(key = "data.type.from")
    public static String DATA_TYPE_FROM;

    /**
     * 数据输出类型
     */
    @PropKey(key = "data.type.to")
    public static String DATA_TYPE_TO;

    /**
     * 数据源(目录、文件、表名)
     */
    @PropKey(key = "data.source.from", classpath = "com.zhysunny.io.properties.impl.StringArrayTypeConversion")
    public static String[] DATA_SOURCE_FROM;

    /**
     * 数据输出（目录或者表名）
     */
    @PropKey(key = "data.source.to")
    public static String DATA_SOURCE_TO;

    /**
     * 数据转移批量数，默认10000
     */
    @PropKey(key = "transfer.batch", defaultValue = "10000")
    public static int TRANSFER_BATCH;

    /**
     * 数据转移线程数，默认20
     */
    @PropKey(key = "transfer.parallel", defaultValue = "20")
    public static int TRANSFER_PARALLEL;

    /**
     * 输出线程池核心线程数
     */
    @PropKey(key = "transfer.thread.core.num", defaultValue = "20")
    public static int TRANSFER_THREAD_CORE_NUM;

    /**
     * 数据转换方式，默认不转换
     */
    @PropKey(key = "transform.way", defaultValue = "com.zhysunny.transfer.transform.NoneTransform")
    public static String TRANSFER_WAY;

    /*********************   xml config   ***************************/

    /**
     * 表示root节点的名字
     */
    @PropKey(key = "xml.root.name")
    public static String XML_ROOT_NAME;

    /**
     * 表示从哪个节点开始作为单个数据
     */
    @PropKey(key = "xml.data.node.from")
    public static String XML_DATA_NODE_FROM;

    /**
     * 表示从哪个节点开始作为单个数据
     */
    @PropKey(key = "xml.data.node.to")
    public static String XML_DATA_NODE_TO;

    /*********************   text config   ***************************/

    /**
     * 输入数据是否包含字段名(默认不包含)
     */
    @PropKey(key = "text.has.heads.from", defaultValue = "false")
    public static boolean TEXT_HAS_HEADS_FROM;

    /**
     * 输入数据分隔符(默认逗号)
     */
    @PropKey(key = "text.line.split.from", defaultValue = ",")
    public static String TEXT_LINE_SPLIT_FROM;

    /**
     * 输出数据是否包含字段名(默认不包含)
     */
    @PropKey(key = "text.has.heads.to", defaultValue = "false")
    public static boolean TEXT_HAS_HEADS_TO;

    /**
     * 输出数据分隔符(默认逗号)
     */
    @PropKey(key = "text.line.split.to", defaultValue = ",")
    public static String TEXT_LINE_SPLIT_TO;

    /*********************   oracle config   ***************************/

    /**
     * oracle输入表连接配置，driver，url，username，password
     */
    @PropKey(key = "jdbc.connection.configs.input", classpath = "com.zhysunny.transfer.util.JdbcConfigTypeConversion")
    public static JdbcConfig JDBC_CONNECTION_CONFIGS_INPUT;

    /**
     * oracle输入表连接配置，driver，url，username，password
     */
    @PropKey(key = "jdbc.connection.configs.output", classpath = "com.zhysunny.transfer.util.JdbcConfigTypeConversion")
    public static JdbcConfig JDBC_CONNECTION_CONFIGS_OUTPUT;

    /**
     * 是否自动更新输出表表结构，表结构为映射xml(默认true)
     */
    @PropKey(key = "oracle.column.auto.update", defaultValue = "true")
    public static boolean ORACLE_COLUMN_AUTO_UPDATE;

}
