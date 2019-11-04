package com.zhysunny.transfer.util;

import com.zhysunny.io.properties.PropKey;
import com.zhysunny.io.properties.PropertiesConstant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 任务配置
 * @author 章云
 * @date 2019/8/15 20:29
 */
public class TaskConstants implements PropertiesConstant {

    /**
     * 映射文件，用于数据转移字段的映射
     */
    @PropKey(key = "mapping.file")
    public static String MAPPING_FILE;

    /**
     * 数据转移批量数，默认1000
     */
    @PropKey(key = "transfer.batch", defaultValue = "1000")
    public static int TRANSFER_BATCH;

    /**
     * 数据转移线程数，默认1
     */
    @PropKey(key = "transfer.parallel", defaultValue = "1")
    public static int TRANSFER_PARALLEL;

    /**
     * 数据转换方式，默认不转换
     */
    @PropKey(key = "transform.way", defaultValue = "com.zhysunny.transfer.transform.NoneTransform")
    public static String TRANSFER_WAY;

}
