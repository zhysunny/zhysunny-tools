package com.zhysunny.transfer;

import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件类输出
 * @author 章云
 * @date 2019/11/7 18:37
 */
public abstract class DataOutputForFile implements DataOutput {

    protected File path;

    public DataOutputForFile() {
        String target = Constants.DATA_SOURCE_TO;
        path = new File(target);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

}
