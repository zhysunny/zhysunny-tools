package com.zhysunny.driver.util;

import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.driver.bean.Column;
import com.zhysunny.driver.bean.JdbcDataBase;
import com.zhysunny.driver.bean.JdbcDataBaseColumn;
import com.zhysunny.driver.bean.Table;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 表结构更新或者创建工具类
 * @author 章云
 * @date 2019/8/15 16:51
 */
public class JdbcUtil {

    public static String createTable(JdbcConnection conn, String tableName, List<Map<String, String>> fieldList) throws Exception {
        JdbcDataBase db = new JdbcDataBase(tableName);
        JdbcDataBaseColumn dbColumn = null;
        for (Map<String, String> map : fieldList) {
            Column column = new Column();
            column.setName(map.get("name"));
            column.setType(map.get("type"));
            column.setLength(map.get("length"));
            column.setNotNull(map.get("notNull"));
            column.setPrimaryKey(map.get("primaryKey"));
            column.setComment(map.get("comment"));
            column.setField(conn);
            dbColumn = column.copy();
            db.addColumn(dbColumn);
        }
        updateOrCreate(conn, tableName, db);
        return conn.getExecuteSql();
    }

    public static String createTable(JdbcConnection conn, File xmlFile) throws Exception {
        Document document = getDocumentBuilder().parse(xmlFile);
        return createTable(conn, document, xmlFile.getName());
    }

    public static String createTable(JdbcConnection conn, String xmlFilePath) throws Exception {
        return createTable(conn, new File(xmlFilePath));
    }

    public static String createTable(JdbcConnection conn, URL url) throws Exception {
        Document document = getDocumentBuilder().parse(url.toString());
        return createTable(conn, document, new File(url.toString()).getName());
    }

    /**
     * 获得创建document的实例
     * @return
     * @throws ParserConfigurationException
     */
    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringComments(true);
        docBuilderFactory.setNamespaceAware(true);
        docBuilderFactory.setXIncludeAware(true);
        return docBuilderFactory.newDocumentBuilder();
    }

    /**
     * 解析document转为Table类
     * @param conn
     * @param document
     * @return
     * @throws Exception
     */
    private static String createTable(JdbcConnection conn, Document document, String fileName) throws Exception {
        Table table = null;
        try {
            JAXBContext cxt = JAXBContext.newInstance(Table.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            table = (Table) unmarshaller.unmarshal(document);
        } catch (Exception e) {
            throw new Exception(e);
        }
        if (table == null) {
            throw new Exception("xml转实体类失败");
        }
        // 开始添加字段
        String tableName = table.getName();
        if (tableName == null || tableName.trim().length() == 0) {
            tableName = fileName.replaceAll(".xml", "");
        }
        tableName = tableName.toUpperCase(Locale.ENGLISH);
        JdbcDataBase db = new JdbcDataBase(tableName);
        JdbcDataBaseColumn dbColumn = null;
        for (Column column : table.getColumns()) {
            // 必须执行setField方法，用于设置不同驱动的字段类型
            column.setField(conn);
            dbColumn = column.copy();
            db.addColumn(dbColumn);
        }
        updateOrCreate(conn, tableName, db);
        return conn.getExecuteSql();
    }

    private static void updateOrCreate(JdbcConnection conn, String tableName, JdbcDataBase db) throws Exception {
        if (conn.existsTable(tableName)) {
            try {
                conn.updateDataBase(tableName, db, true);
            } catch (Exception e) {
                throw new Exception("更新表结构异常", e);
            }
        } else {
            try {
                conn.createDatabase(db);
            } catch (Exception e) {
                throw new Exception("建表异常", e);
            }
        }
    }

}
