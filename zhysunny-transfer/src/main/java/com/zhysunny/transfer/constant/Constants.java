package com.zhysunny.transfer.constant;

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

}
