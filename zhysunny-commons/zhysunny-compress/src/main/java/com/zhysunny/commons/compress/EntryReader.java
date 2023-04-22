package com.zhysunny.commons.compress;

/**
 * @author zhysunny
 * @date 2023/4/22 9:46
 */
public interface EntryReader<T> {
    String getFileNameSuf();

    boolean filter(T entry);

    void read(String content);
}
