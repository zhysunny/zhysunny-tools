package com.zhysunny.io.zip;

import static org.junit.Assert.*;

import org.apache.tools.zip.ZipOutputStream;
import org.junit.*;

import java.io.File;

/**
 * ZipAntUtils Test
 * @author 章云
 * @date 2019/7/27 20:31
 */
public class ZipAntUtilsTest {

    private File zip;
    private File dir;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test ZipAntUtils Class Start...");
    }

    @Before
    public void before() throws Exception {
        zip = new File("src/test/resources/zip/test.zip");
        dir = new File("src/test/resources/zip/test");
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test ZipAntUtils Class End...");
    }

    /**
     * Method: unzip(File zip, String dirPath)
     */
    @Test
    public void testUnzipForZipDirPath() throws Exception {
        //测试解压到当前目录
        String out = zip.getParent();
        ZipAntUtils.unzip(zip, out);
    }

    /**
     * Method: zip(File dir, File zipFile)
     */
    @Test
    public void testZipForDirZipFile() throws Exception {
        ZipAntUtils.zip(dir, zip);
    }

    /**
     * Method: zip(File file, ZipOutputStream out)
     */
    @Test
    public void testZipForFileOut() throws Exception {
        ZipOutputStream zos = new ZipOutputStream(zip);
        ZipAntUtils.zip(dir, zos);
        zos.close();
    }

}
