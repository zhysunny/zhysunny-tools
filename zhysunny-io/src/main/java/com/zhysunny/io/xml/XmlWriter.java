package com.zhysunny.io.xml;

import com.zhysunny.io.BaseWriter;
import com.zhysunny.io.xml.writer.BaseAnyToXml;
import com.zhysunny.io.xml.writer.BeanToXml;
import com.zhysunny.io.xml.writer.MapToXml;
import com.zhysunny.io.xml.writer.PropertiesToXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 实体类写入xml
 * @author 章云
 * @date 2019/7/25 11:14
 */
public class XmlWriter extends BaseWriter {

    /**
     * 输出流
     */
    private Writer out;

    public XmlWriter(String path) {
        super(path);
    }

    public XmlWriter(File file) {
        super(file);
    }

    public void write(BaseAnyToXml baseAnyToXml, Object... params) throws Exception {
        out = new OutputStreamWriter(new FileOutputStream(file));
        baseAnyToXml.write(out, params);
        out.close();
    }

    public void write(Map<String, Object> data) throws Exception {
        write(new MapToXml(), data);
    }

    public void write(Properties props) throws Exception {
        write(new PropertiesToXml(), props);
    }

    public void write(XmlBean bean, String encoding) throws Exception {
        write(new BeanToXml(), bean, encoding);
    }

    public void write(XmlBean bean) throws Exception {
        write(bean, ENCODING);
    }

}
