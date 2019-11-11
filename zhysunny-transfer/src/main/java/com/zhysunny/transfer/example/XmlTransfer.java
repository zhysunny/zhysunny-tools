package com.zhysunny.transfer.example;

import com.zhysunny.transfer.DataBaseTransfer;

/**
 * xml 执行样例
 * @author 章云
 * @date 2019/11/4 15:43
 */
public class XmlTransfer {

    private static class XmlTransfer1 {

        public static void main(String[] args) throws Exception {
            args = new String[]{ "zhysunny-transfer/src/main/resources/example/xml/1/transfer-xml_to_xml.xml" };
            DataBaseTransfer.main(args);
        }

    }

    private static class XmlTransfer2 {

        public static void main(String[] args) throws Exception {
            args = new String[]{ "zhysunny-transfer/src/main/resources/example/xml/2/transfer-xml_to_xml.properties" };
            DataBaseTransfer.main(args);
        }

    }

    private static class XmlTransfer3 {

        public static void main(String[] args) throws Exception {
            args = new String[]{ "zhysunny-transfer/src/main/resources/example/xml/3/transfer-xml_to_xml.properties" };
            DataBaseTransfer.main(args);
        }

    }

}
