package com.zhysunny.commons.compress;

import java.io.IOException;
import java.util.List;

/**
 * @author zhysunny
 * @date 2020/8/10 23:20
 */
public interface Compress<T extends EntryReader> {
    String compress(String content) throws IOException;

    void decompress(String encode, List<T> readers) throws IOException;
}
