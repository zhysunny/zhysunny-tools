package com.zhysunny.io.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:12
 */
public interface ISheetReader<T> extends ReadListener<T> {
    /**
     * 获取读取的数据集，数据集大小小于等于 getBatch()
     *
     * @return
     */
    List<T> getDataList();

    /**
     * 泛型类型
     *
     * @return
     */
    Class<T> getClazz();

    /**
     * 读取的sheet名称或者索引，默认对第一个sheet
     *
     * @return
     */
    default Object getSheetNoOrName() {
        return 0;
    }

    /**
     * excel文件全路径
     *
     * @return
     */
    String getExcelName();

    /**
     * 批处理数量，默认全部读取
     *
     * @return
     */
    default Integer getBatch() {
        return Integer.MAX_VALUE;
    }

    /**
     * 数据处理，按照getBatch()大小分批处理
     */
    void handle();
}
