package com.zhysunny.io.xml.writer;

import com.zhysunny.io.xml.XmlBean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.Writer;

/**
 * java实体类转xml
 * @author 章云
 * @date 2019/7/26 14:48
 */
public class BeanToXml extends BaseAnyToXml {

    private void write(Writer out, XmlBean bean, String encoding) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(bean.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            marshaller.marshal(bean, out);
        } catch (JAXBException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void write(Writer out, Object... params) throws Exception {
        if (params != null && params.length >= 2) {
            XmlBean bean = (XmlBean) params[0];
            String encoding = (String) params[1];
            write(out, bean, encoding);
        } else {
            throw new IllegalArgumentException("参数不正确");
        }
    }
}
