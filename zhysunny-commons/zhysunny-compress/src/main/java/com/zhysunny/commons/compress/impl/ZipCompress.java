package com.zhysunny.commons.compress.impl;

import com.zhysunny.commons.compress.Compress;
import com.zhysunny.commons.compress.TarArchiveEntryReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhysunny
 * @date 2020/8/10 23:33
 */
public class ZipCompress implements Compress {

    @Override
    public String compress(String content) throws IOException {
        String filename = System.currentTimeMillis() + ".txt";
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ZipOutputStream zipOut = new ZipOutputStream(byteOut);) {
            ZipEntry entry = new ZipEntry(filename);
            zipOut.putNextEntry(entry);
            zipOut.write(content.getBytes(StandardCharsets.UTF_8));
            zipOut.finish();
            return Base64.getEncoder().encodeToString(byteOut.toByteArray());
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void decompress(String encode, List<TarArchiveEntryReader> readers) {

    }
}
