package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 不对数据进行转换
 * @author 章云
 * @date 2019/8/26 9:04
 */
public class NoneTransform implements BaseDataTransform {
    @Override
    public List<JSONObject> transform(List<JSONObject> datas) {
        return datas;
    }
}
