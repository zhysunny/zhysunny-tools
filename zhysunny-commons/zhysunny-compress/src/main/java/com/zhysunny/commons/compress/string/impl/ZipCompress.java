package com.zhysunny.commons.compress.string.impl;

import com.zhysunny.commons.compress.string.AbstractStringCompress;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhysunny
 * @date 2020/8/10 23:33
 */
public class ZipCompress extends AbstractStringCompress {
    @Override
    public String compress(String content, String filename) throws IOException {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(byteOut);) {
            ZipEntry entry = new ZipEntry(filename);
            zipOut.putNextEntry(entry);
            zipOut.write(content.getBytes(StandardCharsets.UTF_8));
            zipOut.finish();
            String encode = Base64.getEncoder().encodeToString(byteOut.toByteArray());
            this.srcLength = content.length();
            this.targetLength = encode.length();
            return encode;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public String decompress(String encode, String filename) throws IOException {
        return null;
    }

}
