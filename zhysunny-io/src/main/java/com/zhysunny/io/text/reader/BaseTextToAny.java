package com.zhysunny.io.text.reader;

/**
 * text写入其他数据结构的基类，当外部使用自己的数据结构时，可继承这个基类
 * @author 章云
 * @date 2019/8/27 9:38
 */
public abstract class BaseTextToAny {

    /**
     * 通过实现这个方法，可以自由控制text输入的存储结构
     * @param data
     * @param params
     * @return
     * @throws Exception
     */
    public abstract Object read(String[] data, Object... params) throws Exception;

}
