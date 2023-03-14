package com.zhysunny.io.excel.writer;

import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:12
 */
public interface ISheetWriter<T> {
    /**
     * 写入excel的数据集
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
     * sheet索引，默认使用名称，可以不填
     *
     * @return
     */
    default Integer getSheetNo() {
        return null;
    }

    /**
     * sheet名称
     *
     * @return
     */
    String getSheetName();

    /**
     * excel文件全路径
     *
     * @return
     */
    String getExcelName();
}
