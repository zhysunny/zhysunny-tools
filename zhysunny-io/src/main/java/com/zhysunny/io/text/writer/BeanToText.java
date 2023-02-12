package com.zhysunny.io.text.writer;

import com.zhysunny.io.text.TextBean;
import com.zhysunny.io.text.TextIndex;
import com.zhysunny.io.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * java实体类转Text数据
 * @author 章云
 * @date 2019/7/26 16:29
 */
public class BeanToText<T> extends BaseAnyToText {

    private String[] heads;

    public BeanToText(List<T> data) {
        writeHead(data.get(0));
    }

    private void writeHead(T bean) {
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getFields();
        Map<Integer, String> map = new TreeMap<Integer, String>();
        for (Field field : fields) {
            field.setAccessible(true);
            TextIndex textIndex = field.getAnnotation(TextIndex.class);
            if (textIndex != null) {
                int index = textIndex.value();
                String name = field.getName();
                map.put(index, name);
            }
        }
        String[] heads = new String[map.size()];
        int i = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            heads[i] = entry.getValue();
            i++;
        }
        this.heads = heads;
    }

    @Override
    public String[] write(Object... params) throws Exception {
        if (params != null && params.length > 0) {
            TextBean bean = (TextBean) params[0];
            Class<? extends TextBean> clz = bean.getClass();
            Field[] fields = clz.getFields();
            Map<Integer, String> map = new TreeMap<Integer, String>();
            for (Field field : fields) {
                field.setAccessible(true);
                TextIndex textIndex = field.getAnnotation(TextIndex.class);
                if (textIndex != null) {
                    int index = textIndex.value();
                    String value = FieldUtils.getValue(field, bean);
                    map.put(index, value);
                }
            }
            String[] data = new String[map.size()];
            int i = 0;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                data[i] = entry.getValue();
                i++;
            }
            return data;
        }
        return null;
    }

    @Override
    public String[] getHeads() {
        return this.heads;
    }
}
