package com.zhysunny.commons.compress.string.impl;

import com.zhysunny.commons.compress.string.AbstractStringCompressTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * ZipCompress Test
 */
public class ZipCompressTest extends AbstractStringCompressTest {

    @Before
    public void before() throws Exception {
        compress = new ZipCompress();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testCompress() throws Exception {
        String encode = this.compress.compress(content, "");
        String decode = this.compress.decompress(encode, "");
        assertThat(this.compress.rate()).isLessThan(0);
        assertThat(decode).isEqualTo(content);
    }

    @Test
    public void testCompressEmpty() throws Exception {
        String content = "";
        String encode = this.compress.compress(content, "");
        String decode = this.compress.decompress(encode, "");
        assertThat(this.compress.rate()).isEqualTo(0);
        assertThat(decode).isEqualTo(content);
    }

    @Test
    public void testCompressNull() throws Exception {
        String content = null;
        String encode = this.compress.compress(content, "");
        String decode = this.compress.decompress(encode, "");
        assertThat(this.compress.rate()).isEqualTo(0);
        assertThat(decode).isEqualTo(content);
    }
} 
