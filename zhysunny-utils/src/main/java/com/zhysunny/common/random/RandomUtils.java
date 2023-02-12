package com.zhysunny.common.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机码
 * @author 章云
 * @date 2019/7/27 15:05
 */
public class RandomUtils {

    private RandomUtils() {}

    private static final int NUMBER_CHAR_RANGE_LOWER = 48;
    private static final int NUMBER_CHAR_RANGE_UPPER = 57;
    private static final int BIG_CHAR_RANGE_LOWER = 65;
    private static final int BIG_CHAR_RANGE_UPPER = 90;
    private static final int SMALL_CHAR_RANGE_LOWER = 97;
    private static final int SMALL_CHAR_RANGE_UPPER = 122;
    private static final Random RANDOM = new Random();

    private static List<Character> getNumberList() {
        List<Character> listNumber = new ArrayList<>(10);
        for (int i = NUMBER_CHAR_RANGE_LOWER; i <= NUMBER_CHAR_RANGE_UPPER; i++) {
            listNumber.add((char)i);
        }
        return listNumber;
    }

    private static List<Character> getBigList() {
        List<Character> listBig = new ArrayList<>(26);
        for (int i = BIG_CHAR_RANGE_LOWER; i <= BIG_CHAR_RANGE_UPPER; i++) {
            listBig.add((char)i);
        }
        return listBig;
    }

    private static List<Character> getSmallList() {
        List<Character> listSmall = new ArrayList<>(26);
        for (int i = SMALL_CHAR_RANGE_LOWER; i <= SMALL_CHAR_RANGE_UPPER; i++) {
            listSmall.add((char)i);
        }
        return listSmall;
    }

    private static String getRandomCode(List<Character> list, int n) {
        StringBuilder str = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int t = RANDOM.nextInt(list.size());
            str.append(list.get(t));
        }
        return str.toString();
    }

    /**
     * 数字与大小写字母随机码
     * @param n
     * @return
     */
    public static String allCode(int n) {
        List<Character> list = getNumberList();
        list.addAll(getBigList());
        list.addAll(getSmallList());
        return getRandomCode(list, n);
    }

    /**
     * 数字随机码
     * @param n
     * @return
     */
    public static String numberCode(int n) {
        List<Character> list = getNumberList();
        return getRandomCode(list, n);
    }

    /**
     * 小写字母随机码
     * @param n
     * @return
     */
    public static String smallCode(int n) {
        List<Character> list = getSmallList();
        return getRandomCode(list, n);
    }

    /**
     * 大写字母随机码
     * @param n
     * @return
     */
    public static String bigCode(int n) {
        List<Character> list = getBigList();
        return getRandomCode(list, n);
    }

    /**
     * 数字和小写字母集合
     * @param n
     * @return
     */
    public static String smallAndNumberCode(int n) {
        List<Character> list = getNumberList();
        list.addAll(getSmallList());
        return getRandomCode(list, n);
    }

    /**
     * 数字和大写字母集合
     * @param n
     * @return
     */
    public static String bigAndNumberCode(int n) {
        List<Character> list = getNumberList();
        list.addAll(getBigList());
        return getRandomCode(list, n);
    }

    /**
     * 小写字母和大写字母集合
     * @param n
     * @return
     */
    public static String smallAndBigCode(int n) {
        List<Character> list = getSmallList();
        list.addAll(getBigList());
        return getRandomCode(list, n);
    }

    public static void main(String[] args) {
        int n = 32;
        System.out.println(RandomUtils.allCode(n));
        System.out.println(RandomUtils.bigCode(n));
        System.out.println(RandomUtils.smallCode(n));
        System.out.println(RandomUtils.numberCode(n));
        System.out.println(RandomUtils.bigAndNumberCode(n));
        System.out.println(RandomUtils.smallAndBigCode(n));
        System.out.println(RandomUtils.smallAndNumberCode(n));
    }

}
