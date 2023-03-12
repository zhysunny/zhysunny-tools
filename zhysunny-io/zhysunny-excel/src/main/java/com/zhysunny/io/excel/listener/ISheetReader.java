package com.zhysunny.io.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;
import java.util.List;

/**
 * @author zhysunny
 * @date 2023/3/12 10:12
 */
public interface ISheetReader<T> extends ReadListener<T> {
    List<T> getDataList();

    Class<T> getClazz();

    Object getSheetNoOrName();
}
