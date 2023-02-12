package com.zhysunny.common.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhysunny
 * @date 2020/11/7 21:11
 */
@Slf4j
public final class ThreadUtils extends org.apache.commons.lang3.ThreadUtils {
    private ThreadUtils() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("thread sleep exception", e);
        }
    }
}
