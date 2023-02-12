package com.zhysunny.io.xml.reader;

import com.zhysunny.io.xml.XmlReader;
import org.w3c.dom.*;
import java.util.*;

/**
 * 读取标准的xml配置文件
 * root name必须是configuration
 * 子节点为property，property下只读取name，value
 * @author 章云
 * @date 2019/7/26 13:35
 */
public class XmlToProperties extends BaseXmlToAny {

    @Override
    public Properties read(XmlReader reader, Object... params) {
        Properties properties = new Properties();
        Node root = reader.getDocument().getDocumentElement();
        if (!"configuration".equals(root.getNodeName())) {
            System.err.println("配置文件的root标签不是<configuration>");
        }
        NodeList props = root.getChildNodes();
        for (int i = 0; i < props.getLength(); i++) {
            Node propNode = props.item(i);
            if (!(propNode instanceof Element)) {
                continue;
            }
            Element prop = (Element)propNode;
            if (!"property".equals(prop.getNodeName())) {
                System.err.println("配置项标签不是<property>");
            }
            NodeList fields = prop.getChildNodes();
            String attr = null;
            String value = null;
            for (int j = 0; j < fields.getLength(); j++) {
                Node fieldNode = fields.item(j);
                if (!(fieldNode instanceof Element)) {
                    continue;
                }
                Element field = (Element)fieldNode;
                if ("name".equals(field.getNodeName())) {
                    attr = ((Text)field.getFirstChild()).getData();
                }
                if ("value".equals(field.getNodeName()) && field.hasChildNodes()) {
                    value = ((Text)field.getFirstChild()).getData();
                }
            }
            if (attr != null && value != null) {
                properties.setProperty(attr, value);
            }
        }
        return properties;
    }

}
