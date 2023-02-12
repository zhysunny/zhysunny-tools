package com.zhysunny.io.text.reader;

import com.zhysunny.io.text.TextBean;
import com.zhysunny.io.text.TextIndex;
import com.zhysunny.io.util.FieldUtils;

import java.lang.reflect.Field;

/**
 * text文档每一行转成实体类
 * @author 章云
 * @date 2019/7/26 15:45
 */
public class TextToBean extends BaseTextToAny {

    @Override
    public TextBean read(String[] data, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            Class<? extends TextBean> clz = (Class<? extends TextBean>) params[0];
            TextBean bean = clz.newInstance();
            Field[] fields = clz.getFields();
            for (Field field : fields) {
                field.setAccessible(true);
                TextIndex textIndex = field.getAnnotation(TextIndex.class);
                if (textIndex != null) {
                    int index = textIndex.value();
                    String value = data[index];
                    FieldUtils.setValue(field, value, bean);
                }
            }
            return bean;
        }
        return null;
    }
}
