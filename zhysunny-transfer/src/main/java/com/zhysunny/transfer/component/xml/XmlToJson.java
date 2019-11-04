package com.zhysunny.transfer.component.xml;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.XmlReader;
import com.zhysunny.io.xml.reader.BaseXmlToAny;
import com.zhysunny.transfer.mapping.Mapping;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * 将xml数据转为json格式。
 * 注意xml节点属性和子节点标签不能相同，否则值会被覆盖
 * @author 章云
 * @date 2019/8/22 20:58
 */
public class XmlToJson extends BaseXmlToAny {

    private Mapping mapping;
    private List<Node> nodes;

    public XmlToJson(Mapping mapping) {
        this.mapping = mapping;
        this.nodes = new ArrayList<Node>();
    }

    @Override
    public List<JSONObject> read(XmlReader reader, Object... params) throws Exception {
        List<JSONObject> result = new ArrayList<JSONObject>();
        Node root = reader.getDocument().getDocumentElement();
        String rootName = root.getNodeName();
        if (mapping.getXmlFromDataNode() == null || mapping.getXmlFromDataNode().trim().length() == 0 || mapping.getXmlFromDataNode().equals(rootName)) {
            // 这种情况下，一个xml作为一条数据
            JSONObject json = new JSONObject(true);
            read(json, root, root.getNodeName());
            result.add(json);
        } else {
            // 走到这个方法，tokenizer的长度肯定不是1
            StringTokenizer tokenizer = new StringTokenizer(mapping.getXmlFromDataNode(), "\\.");
            String first = tokenizer.nextToken();
            Vector<String> vector = new Vector<String>(tokenizer.countTokens());
            if (!rootName.equals(first)) {
                vector.add(first);
            }
            while (tokenizer.hasMoreTokens()) {
                vector.add(tokenizer.nextToken());
            }
            read(result, vector, root);
        }
        return result;
    }

    private void read(JSONObject json, Node root, String prefix) {
        // root属性
        if (root.hasAttributes()) {
            NamedNodeMap attributes = root.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribut = attributes.item(i);
                json.put(mapping.valueToName(prefix + "." + attribut.getNodeName()), attribut.getNodeValue().trim());
            }
        }
        // root子节点，只解析root子节点为字符串的节点
        if (root.hasChildNodes()) {
            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node element = childNodes.item(i);
                if ("#text".equals(element.getNodeName())) {
                    // 即使element有子节点也会有这一步
                    String value = element.getNodeValue();
                    if (value != null && value.trim().length() > 0) {
                        // 如果有子节点，走不到这一步，如果有text文本，必然没有子节点
                        json.put(mapping.valueToName(prefix), value.trim());
                    }
                } else {
                    // 有子节点
                    String newPrefix = prefix + "." + element.getNodeName();
                    read(json, element, newPrefix);
                }
            }
        }
    }

    private final void read(List<JSONObject> result, Vector<String> nodeNames, Node root) {
        getChildNodes(nodeNames, root);
        for (Node node : nodes) {
            JSONObject json = new JSONObject(true);
            read(json, node, node.getNodeName());
            result.add(json);
        }
    }

    private final void getChildNodes(Vector<String> nodeNames, Node root) {
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node element = childNodes.item(i);
            for (int j = 0; j < nodeNames.size(); j++) {
                if (j == nodeNames.size() - 1) {
                    if (element.getNodeName().equals(nodeNames.get(j))) {
                        nodes.add(element);
                    }
                } else {
                    getChildNodes(nodeNames, element);
                }
            }
        }
    }
}
