package com.zhysunny.transfer.example;

import com.zhysunny.transfer.DataBaseTransfer;

/**
 * text 执行样例
 * @author 章云
 * @date 2019/11/4 15:44
 */
public class TextTransfer {

    private static class TextTransfer1 {

        public static void main(String[] args) throws Exception {
            args = new String[]{ "zhysunny-transfer/src/main/resources/example/text/1/transfer-text_to_text.xml" };
            DataBaseTransfer.main(args);
        }

    }

    private static class TextTransfer2 {

        public static void main(String[] args) throws Exception {
            args = new String[]{ "zhysunny-transfer/src/main/resources/example/text/2/transfer-text_to_text.properties" };
            DataBaseTransfer.main(args);
        }

    }

}
