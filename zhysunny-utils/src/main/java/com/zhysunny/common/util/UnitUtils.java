package com.zhysunny.common.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单位换算
 * @author 章云
 * @date 2019/7/27 15:30
 */
public final class UnitUtils {

    private UnitUtils() {
    }

    private static final Pattern PATTERN = Pattern.compile("\\d*(\\.\\d+)?");
    private static final double PB = (long)1 << 50;
    private static final double TB = (long)1 << 40;
    private static final double GB = 1 << 30;
    private static final double MB = 1 << 20;
    private static final double KB = 1 << 10;
    private static final double B = 1;
    private static final DecimalFormat DF = new DecimalFormat(".00");

    /**
     * 容量单位换算
     * @param number
     * @return
     */
    public static String getCapacityUnit(Number number, String unit) {
        double size = number.doubleValue();
        double result;
        if ((null == unit && size > PB) || (unit != null && ("PB".equalsIgnoreCase(unit)) || "P".equalsIgnoreCase(unit))) {
            result = size / PB;
            return String.valueOf(DF.format(result)) + " PB";
        } else if ((null == unit && size > TB) || (unit != null && ("TB".equalsIgnoreCase(unit)) || "T".equalsIgnoreCase(unit))) {
            result = size / TB;
            return String.valueOf(DF.format(result)) + " TB";
        } else if ((null == unit && size > GB) || (unit != null && ("GB".equalsIgnoreCase(unit)) || "G".equalsIgnoreCase(unit))) {
            result = size / GB;
            return String.valueOf(DF.format(result)) + " GB";
        } else if ((null == unit && size > MB) || (unit != null && ("MB".equalsIgnoreCase(unit)) || "M".equalsIgnoreCase(unit))) {
            result = size / MB;
            return String.valueOf(DF.format(result)) + " MB";
        } else if ((null == unit && size > KB) || (unit != null && ("KB".equalsIgnoreCase(unit)) || "K".equalsIgnoreCase(unit))) {
            result = size / KB;
            return String.valueOf(DF.format(result)) + " KB";
        } else {
            result = size / B;
            return String.valueOf(DF.format(result)) + " B";
        }
    }

    /**
     * 单位换算
     * @param number
     * @return
     */
    public static String getCapacityUnit(Number number) {
        return getCapacityUnit(number, null);
    }

    /**
     * 单位换算
     * @param sizeUnit
     * @return
     */
    public static Number getCapacity(String sizeUnit) {
        double result = 0;
        Matcher m = PATTERN.matcher(sizeUnit);
        if (m.find()) {
            String doubleStr = m.group();
            String unit = sizeUnit.replaceAll(doubleStr, "").trim();
            double size = Double.parseDouble(doubleStr);
            double factory = 0;
            if ("PB".equalsIgnoreCase(unit) || "P".equalsIgnoreCase(unit)) {
                factory = PB;
            } else if ("TB".equalsIgnoreCase(unit) || "T".equalsIgnoreCase(unit)) {
                factory = TB;
            } else if ("GB".equalsIgnoreCase(unit) || "G".equalsIgnoreCase(unit)) {
                factory = GB;
            } else if ("MB".equalsIgnoreCase(unit) || "M".equalsIgnoreCase(unit)) {
                factory = MB;
            } else if ("KB".equalsIgnoreCase(unit) || "K".equalsIgnoreCase(unit)) {
                factory = KB;
            } else if ("B".equalsIgnoreCase(unit) || "".equalsIgnoreCase(unit)) {
                factory = 1;
            }
            result = factory * size;
        }
        return result;
    }

    /**
     * 单位换算
     * @param number
     * @return
     */
    public static String getNumberUnit(double number) {
        double result;
        if (Math.abs(number) >= Math.pow(10, 12)) {
            int i = 11;
            do {
                i++;
                result = number / Math.pow(10, i);
            } while (Math.abs(result) >= 10);
            return String.valueOf(DF.format(result)) + " * 10^" + i;
        } else {
            int type = 4;
            int i = -4;
            do {
                i += type;
                result = number / Math.pow(10, i);
            } while (Math.abs(result) >= 10000);
            if (i == 0) {
                return String.valueOf(DF.format(result));
            } else {
                return String.valueOf(DF.format(result)) + " * 10^" + i;
            }
        }
    }

    /**
     * 计算百分比
     * @param factor
     * @param denominator
     * @return
     */
    public static String getRateUnit(double factor, double denominator) {
        double rate;
        if (denominator != 0) {
            rate = factor / denominator * 100.0;
        } else {
            if (factor == 0) {
                rate = 0;
            } else {
                rate = 100;
            }
        }
        return DF.format(rate) + " %";
    }

    public static void main(String[] args) {
        double d = 15651521354531D;
        System.out.println(UnitUtils.getCapacityUnit(d));
        System.out.println(UnitUtils.getCapacityUnit(d, "G"));
        System.out.println(UnitUtils.getCapacity("1457.66 GB"));
        System.out.println(UnitUtils.getNumberUnit(d));
        System.out.println(UnitUtils.getRateUnit(523, 1565));
    }

}
