package com.zhysunny.io.text.writer;

/**
 * 不做处理，为了适应多态
 * @author 章云
 * @date 2019/8/27 15:04
 */
public class ArrayToText extends BaseAnyToText {
    @Override
    public String[] write(Object... params) throws Exception {
        return (String[]) params[0];
    }

    @Override
    public String[] getHeads() {
        return null;
    }
}
