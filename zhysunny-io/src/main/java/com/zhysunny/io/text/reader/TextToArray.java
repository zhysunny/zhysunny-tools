package com.zhysunny.io.text.reader;

/**
 * 不做处理，为了适应多态
 * @author 章云
 * @date 2019/8/27 10:16
 */
public class TextToArray extends BaseTextToAny {
    @Override
    public Object read(String[] data, Object... params) throws Exception {
        return data;
    }
}
