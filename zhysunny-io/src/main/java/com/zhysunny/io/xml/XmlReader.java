package com.zhysunny.io.xml;

import com.zhysunny.io.BaseReader;
import com.zhysunny.io.xml.reader.BaseXmlToAny;
import com.zhysunny.io.xml.reader.XmlToBean;
import com.zhysunny.io.xml.reader.XmlToMap;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Xml读取类
 * @author 章云
 * @date 2019/7/24 15:29
 */
public class XmlReader extends BaseReader {

    public static final String ATTRIBUTES = "Attributes";
    public static final String ELEMENT = "Element";

    /**
     * xml文档
     */
    private Document document;

    public XmlReader(Object resource) {
        super(resource);
    }

    /**
     * 加载xml
     * @return
     * @throws Exception
     */
    @Override
    public XmlReader builder() throws Exception {
        if (document == null) {
            try {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                docBuilderFactory.setIgnoringComments(true);
                docBuilderFactory.setNamespaceAware(true);
                // 这是优先选择. 如果不允许DTDs (doctypes) ,几乎可以阻止所有的XML实体攻击
                docBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                docBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                docBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                docBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
                docBuilderFactory.setExpandEntityReferences(false);
                docBuilderFactory.setXIncludeAware(false);
                DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
                // xml文件一次只加载一个
                Object resource = resources.get(0);
                if (resource instanceof URL) {
                    URL url = (URL)resource;
                    document = builder.parse(url.toString());
                } else if (resource instanceof File) {
                    File file = (File)resource;
                    document = builder.parse(file);
                } else if (resource instanceof String) {
                    File file = new File((String)resource);
                    URL url = Thread.currentThread().getContextClassLoader().getResource((String)resource);
                    if (url != null) {
                        document = builder.parse(url.toString());
                    } else {
                        document = builder.parse(file);
                    }
                } else if (resource instanceof InputStream) {
                    InputStream is = (InputStream)resource;
                    document = builder.parse(is);
                } else {
                    throw new RuntimeException("不支持的资源配置类型：" + resource.getClass());
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return this;
    }

    public Object read(BaseXmlToAny baseXmlToAny, Object... params) throws Exception {
        if (document == null) {
            builder();
        }
        return baseXmlToAny.read(this, params);
    }

    /**
     * xml转化成Map
     * @return
     * @throws Exception
     */
    public Map<String, Object> read() throws Exception {
        return (Map<String, Object>)read(new XmlToMap());
    }

    /**
     * xml转化成实体类
     * @param clz
     * @return
     * @throws Exception
     */
    public <T> T read(Class<T> clz) throws Exception {
        if (!XmlBean.class.isAssignableFrom(clz)) {
            throw new Exception(clz.getSimpleName() + " 没有实现 " + XmlBean.class);
        }
        return (T)read(new XmlToBean(), clz);
    }

    /**
     * 获取document实例
     * @return
     */
    public Document getDocument() {
        return document;
    }

}
