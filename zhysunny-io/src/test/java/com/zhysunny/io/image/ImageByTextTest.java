package com.zhysunny.io.image;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * ImageByText Test
 * @author 章云
 * @date 2019/7/27 22:30
 */
public class ImageByTextTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test ImageByText Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test ImageByText Class End...");
    }

    /**
     * Method: markByText(String logoText, String srcImgPath, String targerPath, Integer degree)
     */
    @Test
    public void testMarkByTextForLogoTextSrcImgPathTargerPathDegree() throws Exception {
        String logoText = "赵丽颖";
        String srcImgPath = "src/test/resources/image/input.jpeg";
        String targerPath = "src/test/resources/image/output.jpeg";
        Integer degree = -45;
        ImageByText.markImageByText(logoText, srcImgPath, targerPath, degree);
    }


} 
