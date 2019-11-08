package com.zhysunny.transfer.component.xml;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.xml.writer.BaseAnyToXml;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Writer;
import java.util.*;

/**
 * 将json格式转为xml数据。
 * @author 章云
 * @date 2019/8/23 17:11
 */
public class JsonToXml extends BaseAnyToXml {

    private Mapping mapping;
    private List<Node> nodes;
    private Document document;

    public JsonToXml(Mapping mapping) {
        this.mapping = mapping;
        this.nodes = new ArrayList<Node>();
    }

    @Override
    public void write(Writer out, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            List<JSONObject> datas = (List<JSONObject>)params[0];
            try {
                document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                // 开始写root
                String rootName = Constants.XML_ROOT_NAME;
                Element root = document.createElement(rootName);
                document.appendChild(root);
                root.appendChild(document.createTextNode("\n"));
                if (Constants.XML_DATA_NODE_TO == null || Constants.XML_DATA_NODE_TO.trim().length() == 0 || Constants.XML_DATA_NODE_TO
                .equals(rootName)) {
                    // 这种情况下，一个xml作为一条数据
                    appendChild(datas.get(0), root);
                } else {
                    // 走到这个方法，tokenizer的长度肯定不是1
                    StringTokenizer tokenizer = new StringTokenizer(Constants.XML_DATA_NODE_TO, "\\.");
                    String first = tokenizer.nextToken();
                    Vector<String> vector = new Vector<String>(tokenizer.countTokens());
                    if (!rootName.equals(first)) {
                        vector.add(first);
                    }
                    while (tokenizer.hasMoreTokens()) {
                        vector.add(tokenizer.nextToken());
                    }
                    addChildNodes(vector, root, datas.size());
                    for (int i = 0; i < datas.size(); i++) {
                        appendChild(datas.get(i), (Element)nodes.get(i));
                    }
                }
                // 开始写入xml
                output(document, out);
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            throw new IllegalArgumentException("参数不正确");
        }
    }

    /**
     * 递归写入子节点
     * @param json    json数据
     * @param element 节点
     * @throws Exception
     */
    private void appendChild(JSONObject json, Element element) throws Exception {
        Element temp = element;
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            StringTokenizer tokenizer = new StringTokenizer(key, "\\.");
            String first = tokenizer.nextToken();
            Vector<String> vector = new Vector<String>(tokenizer.countTokens());
            if (!first.equals(Constants.XML_ROOT_NAME)) {
                vector.add(first);
            }
            while (tokenizer.hasMoreTokens()) {
                vector.add(tokenizer.nextToken());
            }
            for (int i = 0; i < vector.size(); i++) {
                if (i == vector.size() - 1) {
                    String fieldType = mapping.getFieldType(key);
                    if ("text".equalsIgnoreCase(fieldType)) {
                        Element child = document.createElement(vector.get(i));
                        child.setTextContent(json.getString(key));
                        temp.appendChild(child);
                    } else {
                        temp.setAttribute(vector.get(i), json.getString(key));
                    }
                } else {
                    Element child = null;
                    if (!contains(temp, vector.get(i))) {
                        child = document.createElement(vector.get(i));
                        temp.appendChild(child);
                    } else {
                        child = nextElement(temp, vector.get(i));
                    }
                    temp = child;
                }
            }
            temp = element;
        }
    }

    private final void addChildNodes(Vector<String> nodeNames, Node root, int dataLength) {
        for (int i = 0; i < dataLength; i++) {
            Node temp = root;
            for (int j = 0; j < nodeNames.size(); j++) {
                Element child = document.createElement(nodeNames.get(j));
                temp.appendChild(child);
                temp = child;
                if (j == nodeNames.size() - 1) {
                    nodes.add(child);
                }
            }
        }
    }

    /**
     * 当前节点是否包含这个子节点
     * @param element
     * @param childName
     * @return
     */
    private boolean contains(Element element, String childName) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (childName.equals(node.getNodeName())) {
                return true;
            }
        }
        return false;
    }

    private Element nextElement(Element element, String childName) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (childName.equals(node.getNodeName())) {
                return (Element)node;
            }
        }
        return null;
    }

}
