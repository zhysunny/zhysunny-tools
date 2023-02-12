package com.zhysunny.io.xml.reader;

import com.zhysunny.io.xml.XmlReader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * 将xml转化成Map集合，只支持单个root标签的xml
 * 属性以属性名为key，如果最底层的Element有值，则Element标签名为key
 * 每个map集合多出两个键值对 Attributes,Element 存储对应key值集合
 * 列表统一用ArrayList存储，键值对统一用LinkedHashMap存储
 * @author 章云
 * @date 2019/7/26 13:35
 */
public class XmlToMap extends BaseXmlToAny {

    @Override
    public Map<String, Object> read(XmlReader reader, Object... params) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        getChildNodes(result, reader.getDocument().getDocumentElement());
        return result;
    }

    /**
     * 递归获取子节点
     * @param result
     * @param node
     */
    private final void getChildNodes(Map<String, Object> result, Node node) {
        // 节点名称
        result.put(XmlReader.ELEMENT, node.getNodeName());
        // 节点属性
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();
            Set<String> attributeKeys = new LinkedHashSet<String>(attributes.getLength());
            result.put(XmlReader.ATTRIBUTES, attributeKeys);
            for (int j = 0; j < attributes.getLength(); j++) {
                Node attribut = attributes.item(j);
                attributeKeys.add(attribut.getNodeName());
                result.put(attribut.getNodeName(), attribut.getNodeValue());
            }
        }
        // 子节点
        if (node.hasChildNodes()) {
            NodeList childNodes = node.getChildNodes();
            List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>(childNodes.getLength() - 1);
            Map<String, Object> childMap = null;
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node element = childNodes.item(i);
                if ("#text".equals(element.getNodeName())) {
                    // 即使有子节点也会有这一步
                    String value = element.getNodeValue();
                    if (value != null && value.trim().length() > 0) {
                        // 如果有子节点，走不到这一步，如果有text文本，必然没有子节点
                        result.put(node.getNodeName(), value);
                        break;
                    }
                } else {
                    result.put(node.getNodeName(), childList);
                    childMap = new LinkedHashMap<String, Object>();
                    childList.add(childMap);
                    // 有子节点
                    getChildNodes(childMap, element);
                }
            }
        }
    }

}
