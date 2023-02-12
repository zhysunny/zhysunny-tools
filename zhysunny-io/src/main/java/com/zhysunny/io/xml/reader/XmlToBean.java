package com.zhysunny.io.xml.reader;

import com.zhysunny.io.xml.XmlReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * xml转化成实体类
 * @author 章云
 * @date 2019/7/26 13:42
 */
public class XmlToBean extends BaseXmlToAny {

    public <T> T read(XmlReader reader, Class<T> clz) throws Exception {
        Object bean = null;
        try {
            JAXBContext cxt = JAXBContext.newInstance(clz);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            bean = unmarshaller.unmarshal(reader.getDocument());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return (T) bean;
    }

    @Override
    public Object read(XmlReader reader, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Class<?> param = (Class<?>) params[0];
            return read(reader, param);
        }
        return null;
    }
}
