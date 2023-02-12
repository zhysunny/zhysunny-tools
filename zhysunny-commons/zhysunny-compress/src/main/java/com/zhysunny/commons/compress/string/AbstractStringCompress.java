package com.zhysunny.commons.compress.string;

import com.zhysunny.commons.compress.Compress;
import java.io.IOException;

/**
 * @author zhysunny
 * @date 2020/8/10 23:21
 */
public abstract class AbstractStringCompress implements Compress {

    protected double srcLength;
    protected double targetLength;

    public abstract String compress(String content, String filename) throws IOException;

    public abstract String decompress(String encode, String filename) throws IOException;

    @Override
    public double rate() {
        if (srcLength == 0) {
            return 0D;
        }
        return 1.00 - targetLength / srcLength;
    }

}
