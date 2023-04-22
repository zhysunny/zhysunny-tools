package com.zhysunny.commons.compress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

/**
 * @author zhysunny
 * @date 2023/4/22 9:46
 */
public interface TarArchiveEntryReader extends EntryReader<TarArchiveEntry> {
    @Override
    default boolean filter(TarArchiveEntry entry) {
        return entry.getName().endsWith(getFileNameSuf());
    }
}
