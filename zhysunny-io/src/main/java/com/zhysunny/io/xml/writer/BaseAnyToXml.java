package com.zhysunny.io.xml.writer;

import org.w3c.dom.Document;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Writer;

/**
 * 其他数据结构写入xml的基类，当外部使用自己的数据结构时，可继承这个基类
 * @author 章云
 * @date 2019/8/23 16:40
 */
public abstract class BaseAnyToXml {

    /**
     * 通过实现这个方法，可以自由控制xml输出的数据结构
     * @param out
     * @param params
     * @return
     * @throws Exception
     */
    public abstract void write(Writer out, Object... params) throws Exception;

    public final void output(Document document, Writer out) throws TransformerException {
        // 开始写入xml
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(out);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, result);
    }

}
