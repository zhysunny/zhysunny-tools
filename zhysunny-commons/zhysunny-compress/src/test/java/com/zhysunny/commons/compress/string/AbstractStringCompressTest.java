package com.zhysunny.commons.compress.string;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * AbstractStringCompress Test
 */
public class AbstractStringCompressTest {
    protected String content = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_1234567890_abcdefghijklmnopqrstuvwxyz";

    protected AbstractStringCompress compress;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test AbstractStringCompress Class Start...");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test AbstractStringCompress Class End...");
    }

} 
