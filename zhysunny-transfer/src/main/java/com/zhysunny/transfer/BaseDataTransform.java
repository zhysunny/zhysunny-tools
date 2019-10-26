package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 数据输入时对数据进行转换
 * @author 章云
 * @date 2019/8/26 9:00
 */
public interface BaseDataTransform {

    /**
     * 数据转换，只针对value值改变
     * @param datas
     * @return
     */
    List<JSONObject> transform(List<JSONObject> datas);

}
