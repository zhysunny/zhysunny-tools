package com.zhysunny.transfer;

import com.alibaba.fastjson.JSONObject;
import java.util.List;

/**
 * 数据输出接口
 * @author 章云
 * @date 2019/8/15 21:11
 */
public interface DataOutput {

    /**
     * 输出数据，返回输出数据的个数
     * @param datas
     * @return
     * @throws Exception
     */
    int output(List<JSONObject> datas) throws Exception;

}
