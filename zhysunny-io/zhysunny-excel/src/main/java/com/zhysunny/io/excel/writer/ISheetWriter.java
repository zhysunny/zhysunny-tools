package com.zhysunny.io.excel.writer;

import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:12
 */
public interface ISheetWriter<T> {
    List<T> getDataList();

    Class<T> getClazz();

    int getSheetNo();

    String getSheetName();
}
