package com.zhysunny.io;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 文件写入基类
 *
 * @author 章云
 * @date 2019/7/26 13:54
 */
public abstract class BaseWriter {
    protected static final Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * 输出文件
     */
    protected File file;

    protected BaseWriter(String path) {
        this.file = new File(path);
    }

    protected BaseWriter(File file) {
        this.file = file;
    }

}
