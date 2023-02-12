package com.zhysunny.driver.example;

import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.util.JdbcUtil;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 章云
 * @date 2019/10/26 11:24
 */
public class CreateTableMysql {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("className", "com.mysql.jdbc.Driver");
        map.put("url", "jdbc:mysql://localhost:3306/test?characterEncoding=utf8");
        map.put("username", "root");
        map.put("password", "123456");
        JdbcConnection conn = new JdbcConnection(map);
        System.out.println(conn.getConfig());
        System.out.println(conn.getThisDriver());
        //        conn.deleteDatabase("student");
        URL url = CreateTableMysql.class.getClassLoader().getResource("mysql/student.xml");
        JdbcUtil.createTable(conn, url);
        System.out.println(conn.getExecuteSql());
    }

}
