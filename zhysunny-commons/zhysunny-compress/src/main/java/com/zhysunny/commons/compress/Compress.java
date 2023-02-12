package com.zhysunny.commons.compress;

import java.text.DecimalFormat;

/**
 * @author zhysunny
 * @date 2020/8/10 23:20
 */
public interface Compress {
    DecimalFormat DF = new DecimalFormat("0.0000");

    double rate();
}
