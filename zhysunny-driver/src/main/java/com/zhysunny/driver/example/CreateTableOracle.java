package com.zhysunny.driver.example;

import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.util.JdbcUtil;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 章云
 * @date 2019/10/26 11:25
 */
public class CreateTableOracle {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("className", "oracle.jdbc.driver.OracleDriver");
        map.put("url", "jdbc:oracle:thin:@localhost:1521:ORCL");
        map.put("username", "ZHANGYUN");
        map.put("password", "ZHANGYUN");
        JdbcConnection conn = new JdbcConnection(map);
        System.out.println(conn.getConfig());
        System.out.println(conn.getThisDriver());
        //        conn.deleteDatabase("student");
        URL url = CreateTableOracle.class.getClassLoader().getResource("oracle/student.xml");
        JdbcUtil.createTable(conn, url);
        System.out.println(conn.getExecuteSql());
    }

}
