package com.zhysunny.io.xml.writer;

import com.zhysunny.io.xml.XmlReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * map集合写入xml
 * @author 章云
 * @date 2019/7/26 14:42
 */
public class MapToXml extends BaseAnyToXml {

    private void write(Writer out, Map<String, Object> rootMap) throws Exception {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            // 开始写root
            String rootName = rootMap.get(XmlReader.ELEMENT).toString();
            Element root = document.createElement(rootName);
            document.appendChild(root);
            root.appendChild(document.createTextNode("\n"));
            Set<String> attributes = (Set<String>)rootMap.get(XmlReader.ATTRIBUTES);
            if (attributes != null && attributes.size() > 0) {
                // 添加属性值
                for (String attributeName : attributes) {
                    root.setAttribute(attributeName, rootMap.get(attributeName).toString());
                }
            }
            Object child = rootMap.get(rootMap.get(XmlReader.ELEMENT));
            appendChild(child, root, document);
            root.appendChild(document.createTextNode("\n"));
            // 开始写入xml
            output(document, out);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 递归写入子节点
     * @param obj      数据,String或List类型
     * @param element  节点
     * @param document
     * @throws Exception
     */
    private void appendChild(Object obj, Element element, Document document) throws Exception {
        if (obj == null) {
            element.appendChild(document.createTextNode(""));
        } else if (obj instanceof String) {
            Node textNode = document.createTextNode(obj.toString());
            element.appendChild(textNode);
        } else if (obj instanceof List) {
            List<Map<String, Object>> childList = (List<Map<String, Object>>)obj;
            for (Map<String, Object> childMap : childList) {
                String nodeName = childMap.get(XmlReader.ELEMENT).toString();
                Element childNode = document.createElement(nodeName);
                element.appendChild(childNode);
                Set<String> attributes = (Set<String>)childMap.get(XmlReader.ATTRIBUTES);
                if (attributes != null && attributes.size() > 0) {
                    // 添加属性值
                    for (String attributeName : attributes) {
                        childNode.setAttribute(attributeName, childMap.get(attributeName).toString());
                    }
                }
                Object child = childMap.get(childMap.get(XmlReader.ELEMENT));
                appendChild(child, childNode, document);
            }
        } else {
            throw new Exception(obj + " 值不能是String或List以外的类型");
        }
    }

    @Override
    public void write(Writer out, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Map<String, Object> rootMap = (Map<String, Object>)params[0];
            write(out, rootMap);
        } else {
            throw new IllegalArgumentException("参数不正确");
        }
    }

}
