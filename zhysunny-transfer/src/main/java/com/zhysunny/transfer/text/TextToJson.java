package com.zhysunny.transfer.text;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.io.text.reader.BaseTextToAny;
import com.zhysunny.transfer.mapping.Mapping;

/**
 * text数据转json
 * @author 章云
 * @date 2019/8/27 15:49
 */
public class TextToJson extends BaseTextToAny {

    private Mapping mapping;

    public TextToJson(Mapping mapping) {
        this.mapping = mapping;
    }

    @Override
    public JSONObject read(String[] data, Object... params) throws Exception {
        if (params != null && params.length > 0) {
            String[] heads = (String[]) params;
            if (heads == null) {
                // 如果不设置字段名，默认索引为key值
                heads = new String[data.length];
            }
            JSONObject json = new JSONObject(true);
            for (int i = 0; i < heads.length; i++) {
                if (i >= data.length) {
                    break;
                }
                if (heads[i] == null || heads[i].length() == 0) {
                    // 默认索引为key值
                    heads[i] = String.valueOf(i);
                }
                // 如果有字段名使用字段名，没有使用列索引值，这是映射字段value的对应值
                // 存储的时候使用映射字段name对应的值
                json.put(mapping.valueToName(heads[i]), data[i]);
            }
            return json;
        }
        return null;
    }
}
