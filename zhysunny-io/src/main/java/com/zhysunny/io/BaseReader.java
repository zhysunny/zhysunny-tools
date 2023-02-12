package com.zhysunny.io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件读取基类
 * @author 章云
 * @date 2019/7/25 17:09
 */
public abstract class BaseReader {

    /**
     * 输入实例，支持URL，InputStream，File，String(File或URL)
     */
    protected List<Object> resources;

    protected BaseReader(Object... resources) {
        this.resources = new ArrayList<>();
        addResource(resources);
    }

    protected BaseReader(List<Object> resources) {
        this.resources = resources;
    }

    public void addResource(Object... resources) {
        if (resources != null && resources.length > 0) {
            for (Object resource : resources) {
                this.resources.add(resource);
            }
        }
    }

    /**
     * 用于读取输出的文件流
     * @return 返回当前对象
     * @throws Exception
     */
    public abstract BaseReader builder() throws Exception;

}
