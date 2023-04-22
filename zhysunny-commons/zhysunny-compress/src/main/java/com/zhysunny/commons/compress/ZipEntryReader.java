package com.zhysunny.commons.compress;

import java.util.zip.ZipEntry;

/**
 * @author zhysunny
 * @date 2023/4/22 9:46
 */
public interface ZipEntryReader extends EntryReader<ZipEntry> {
    @Override
    default boolean filter(ZipEntry entry) {
        return entry.getName().endsWith(getFileNameSuf());
    }
}
