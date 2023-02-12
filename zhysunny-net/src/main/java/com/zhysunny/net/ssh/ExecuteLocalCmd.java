package com.zhysunny.net.ssh;

import com.zhysunny.net.util.NetUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地执行cmd命令
 * @author 章云
 * @date 2019/7/27 23:23
 */
public class ExecuteLocalCmd {

    /**
     * 本地发送命令
     * @param command
     */
    public static List<String> executeCmd(String[] command) throws Exception {
        BufferedReader br = null;
        List<String> list = new ArrayList<String>();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (br != null) {
                br.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        String[] cmd = new String[]{ "ipconfig" };
        System.out.println(executeCmd(cmd));
    }

}
