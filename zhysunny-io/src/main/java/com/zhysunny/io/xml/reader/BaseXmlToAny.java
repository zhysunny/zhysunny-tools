package com.zhysunny.io.xml.reader;

import com.zhysunny.io.xml.XmlReader;

/**
 * xml写入其他数据结构的基类，当外部使用自己的数据结构时，可继承这个基类
 * @author 章云
 * @date 2019/8/22 20:34
 */
public abstract class BaseXmlToAny {

    /**
     * 通过实现这个方法，可以自由控制xml输入的存储结构
     * @param reader
     * @param params
     * @return
     */
    public abstract Object read(XmlReader reader, Object... params) throws Exception;

}
