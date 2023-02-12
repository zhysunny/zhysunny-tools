package com.zhysunny.io.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public interface FileReadWrite<E> {

    /**
     * 文件读
     * @return
     * @throws IOException
     */
    List<E> read() throws IOException;

    /**
     * 文件写
     * @param datas
     * @return
     * @throws IOException
     */
    Object write(List<E> datas) throws IOException;

    /**
     * 文件写
     * @param datas
     * @return
     * @throws IOException
     */
    Object write(Map<String, E> datas) throws IOException;

    /**
     * 获取当前操作文件
     * @return
     */
    File getFile();

}
