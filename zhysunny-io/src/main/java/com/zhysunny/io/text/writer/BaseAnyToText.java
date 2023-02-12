package com.zhysunny.io.text.writer;

/**
 * 其他数据结构写入text的基类，当外部使用自己的数据结构时，可继承这个基类
 * @author 章云
 * @date 2019/8/27 14:57
 */
public abstract class BaseAnyToText {

    /**
     * 通过实现这个方法，可以自由控制text输出的数据结构
     * @param params
     * @return
     * @throws Exception
     */
    public abstract String[] write(Object... params) throws Exception;

    /**
     * 子类实例化生成Heads，通过get方法获取
     * @return
     */
    public abstract String[] getHeads();

}
