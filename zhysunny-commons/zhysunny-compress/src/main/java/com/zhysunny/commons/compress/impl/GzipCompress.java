package com.zhysunny.commons.compress.impl;

import com.zhysunny.commons.compress.Compress;
import com.zhysunny.commons.compress.TarArchiveEntryReader;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zhysunny
 * @date 2023/4/22 9:54
 */
public class GzipCompress implements Compress {
    @Override
    public String compress(String content) throws IOException {
        String filename = System.currentTimeMillis() + ".txt";
        try (ByteArrayOutputStream tarByteOut = new ByteArrayOutputStream();
                TarArchiveOutputStream tarOut = new TarArchiveOutputStream(tarByteOut);
                ByteArrayOutputStream gzipByteOut = new ByteArrayOutputStream();
                GZIPOutputStream gzipOut = new GZIPOutputStream(gzipByteOut)) {
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            TarArchiveEntry entry = new TarArchiveEntry(filename);
            entry.setSize(bytes.length);
            tarOut.putArchiveEntry(entry);
            IOUtils.copy(new ByteArrayInputStream(bytes), tarOut);
            tarOut.closeArchiveEntry();
            tarOut.finish();
            byte[] temp = tarByteOut.toByteArray();
            gzipOut.write(temp);
            gzipOut.finish();
            return Base64.getEncoder().encodeToString(gzipByteOut.toByteArray());
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void decompress(String encode, List<TarArchiveEntryReader> readers) throws IOException {
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(Base64.getDecoder().decode(encode.getBytes()));
                GZIPInputStream gzipIn = new GZIPInputStream(byteIn);
                TarArchiveInputStream tarIn = new TarArchiveInputStream(gzipIn)) {

            TarArchiveEntry entry;
            while ((entry = tarIn.getNextTarEntry()) != null) {
                handle(entry, tarIn, readers);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private void handle(TarArchiveEntry entry, TarArchiveInputStream tarIn, List<TarArchiveEntryReader> readers)
            throws IOException {
        if (entry.isFile() && entry.getSize() == 0) {
            return;
        }
        if (entry.isDirectory()) {
            TarArchiveEntry[] entries = entry.getDirectoryEntries();
            for (TarArchiveEntry tarArchiveEntry : entries) {
                handle(tarArchiveEntry, tarIn, readers);
            }
        }
        for (TarArchiveEntryReader reader : readers) {
            if (reader.filter(entry)) {
                try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
                    IOUtils.copy(tarIn, byteOut);
                    reader.read(byteOut.toString());
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

}
