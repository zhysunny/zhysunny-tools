package com.zhysunny.transfer;

import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库类输入源
 * @author 章云
 * @date 2019/11/7 17:59
 */
public abstract class AbstractDataInputForDB implements DataInput {

    protected List<String> tables;
    protected int index;

    public AbstractDataInputForDB() {
        this.index = 0;
        String[] sources = Constants.DATA_SOURCE_FROM;
        tables = new ArrayList<>();
        for (String source : sources) {
            tables.add(source);
        }
    }

}
