package com.zhysunny.io.image;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * ImageByIcon Test
 * @author 章云
 * @date 2019/7/27 22:35
 */
public class ImageByIconTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test ImageByIcon Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test ImageByIcon Class End...");
    }

    /**
     * Method: markImageByIcon(String iconPath, String srcImgPath, String tagertPath, Integer degree)
     */
    @Test
    public void testMarkImageByIconForIconPathSrcImgPathTagertPathDegree() throws Exception {
        String iconPath = "src/test/resources/image/icon.jpg";
        String srcImgPath = "src/test/resources/image/input.jpeg";
        String targerPath = "src/test/resources/image/output.jpeg";
        Integer degree = -45;
        ImageByIcon.markImageByIcon(iconPath, srcImgPath, targerPath, degree);
    }

}
