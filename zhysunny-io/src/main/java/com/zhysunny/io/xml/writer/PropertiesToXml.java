package com.zhysunny.io.xml.writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * 写入标准的xml配置文件
 * root name必须是configuration
 * 子节点为property，property下只读取name，value
 * @author 章云
 * @date 2019/7/26 14:42
 */
public class PropertiesToXml extends BaseAnyToXml {

    private void write(Writer out, Properties props) throws Exception {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            // 开始写root
            Element root = document.createElement("configuration");
            document.appendChild(root);
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                Element property = document.createElement("property");
                root.appendChild(property);
                Element name = document.createElement("name");
                name.setTextContent(entry.getKey().toString());
                property.appendChild(name);
                Element value = document.createElement("value");
                value.setTextContent(entry.getValue().toString());
                property.appendChild(value);
            }
            // 开始写入xml
            output(document, out);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void write(Writer out, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Properties props = (Properties)params[0];
            write(out, props);
        } else {
            throw new IllegalArgumentException("参数不正确");
        }
    }

}
