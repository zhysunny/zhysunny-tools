package com.zhysunny.io.text.reader;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * text数据转map
 * @author 章云
 * @date 2019/8/27 9:43
 */
public class TextToMap extends BaseTextToAny {
    @Override
    public Map<String, String> read(String[] data, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            String[] heads = (String[]) params;
            if (heads == null) {
                // 如果不设置字段名，默认索引为key值
                heads = new String[data.length];
            }
            Map<String, String> map = new LinkedHashMap<String, String>(Math.min(data.length, heads.length));
            for (int i = 0; i < heads.length; i++) {
                if (i >= data.length) {
                    break;
                }
                if (heads[i] == null || heads[i].length() == 0) {
                    // 默认索引为key值
                    heads[i] = String.valueOf(i);
                }
                map.put(heads[i], data[i]);
            }
            return map;
        }
        return null;
    }
}
