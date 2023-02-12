package com.zhysunny.net.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ip操作
 * @author 章云
 * @date 2019/7/27 14:51
 */
public class NetUtils {
    /**
     * 获取本地IP
     * @return
     */
    public static String getLocalIp() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 读取ip地址
     * @param ipString
     * @return
     */
    public static List<String> splitIp(String ipString) {
        String regexIpString = "[0-9]{1,3}(.[0-9]{1,3}){3}(([,-][0-9]{1,3}(.[0-9]{1,3}){3})|[-][0-9]{1,3})*";
        List<String> ipList = new ArrayList<String>();
        if (!ipString.matches(regexIpString)) {
            return null;
        } else {
            String[] ipRangs = ipString.split(",");
            String regexIpRang = "[0-9]{1,3}(.[0-9]{1,3}){3}([-][0-9]{1,3}((.[0-9]{1,3}){3})?)?";
            for (String ipRang : ipRangs) {
                if (!ipRang.matches(regexIpRang)) {
                    continue;
                } else {
                    if (ipRang.contains("-")) {
                        String[] ips = ipRang.split("-");
                        if (ips.length == 2) {
                            String regex1 = "[0-9]{1,3}(.[0-9]{1,3}){3}[-][0-9]{1,3}(.[0-9]{1,3}){3}";
                            String regex2 = "[0-9]{1,3}(.[0-9]{1,3}){3}[-][0-9]{1,3}";
                            String[] array = ips[0].split("\\.");
                            String prefix = array[0] + "." + array[1] + "." + array[2] + ".";
                            int suf0 = Integer.parseInt(array[3]);
                            int suf1 = 0;
                            if (ipRang.matches(regex1)) {
                                suf1 = Integer.parseInt(ips[1].split("\\.")[3]);
                            } else if (ipRang.matches(regex2)) {
                                suf1 = Integer.parseInt(ips[1]);
                            }
                            int max = 0;
                            int min = 0;
                            if (suf1 >= suf0) {
                                max = suf1;
                                min = suf0;
                            } else {
                                max = suf0;
                                min = suf1;
                            }
                            for (int i = min; i <= max; i++) {
                                String ip = prefix + i;
                                ipList.add(ip);
                            }
                        }
                    } else {
                        ipList.add(ipRang);
                    }
                }
            }
        }
        return ipList;
    }

    public static void main(String[] args) {
        String ipString = "192.168.31.10,192.168.31.11-192.168.31.50,192.168.31.129-138";
        System.out.println(splitIp(ipString));
    }

}
