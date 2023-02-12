package com.zhysunny.commons.compress.string.impl;

import com.zhysunny.commons.compress.string.AbstractStringCompress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author zhysunny
 * @date 2020/8/10 23:22
 */
public class NoCompress extends AbstractStringCompress {
    @Override
    public String compress(String content, String filename) {
        if (content == null) {
            return null;
        }
        String encode = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        this.srcLength = content.length();
        this.targetLength = encode.length();
        return encode;
    }

    @Override
    public String decompress(String encode, String filename) {
        if (encode == null) {
            return null;
        }
        return new String(Base64.getDecoder().decode(encode), StandardCharsets.UTF_8);
    }

}
