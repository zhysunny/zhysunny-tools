package com.zhysunny.io.excel.writer;

import com.zhysunny.io.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * map转excel
 * @author 章云
 * @date 2019/7/26 15:13
 */
public class MapToExcel {

    private ExcelWriter writer;

    public MapToExcel(ExcelWriter writer) {
        this.writer = writer;
    }

    /**
     * xls文件格式输出
     * @param dataMap
     * @param file
     * @throws IOException
     */
    public void write(Map<String, List<Map<String, String>>> dataMap, File file) throws IOException {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet
        HSSFSheet sheet = null;
        // 创建一行
        HSSFRow row = null;
        // 创建一个单元格
        HSSFCell cell = null;
        // 数据
        List<Map<String, String>> dataLists = null;
        // 数据头作为标题
        Set<String> heads = null;
        HSSFCellStyle titleStyle = writer.getTitleStyle(wb);
        HSSFCellStyle textStyle = writer.getTextStyle(wb);
        FileOutputStream fos = null;
        for (Map.Entry<String, List<Map<String, String>>> entry : dataMap.entrySet()) {
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            sheet = wb.createSheet(entry.getKey());
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            dataLists = entry.getValue();
            heads = new LinkedHashSet<>();
            if (dataLists != null && !dataLists.isEmpty()) {
                heads = dataLists.get(0).keySet();
            }
            // 添加第一行标题
            int i = 0;
            for (String head : heads) {
                cell = row.createCell(i);
                // 单元格宽度
                sheet.setColumnWidth(i, 5000);
                sheet.setAutobreaks(true);
                cell.setCellValue(head);
                // 标题需要加粗
                cell.setCellStyle(titleStyle);
                i++;
            }
            int rownum = 1;
            for (Map<String, String> map : dataLists) {
                row = sheet.createRow(rownum++);
                int j = 0;
                for (String head : heads) {
                    cell = row.createCell(j);
                    cell.setCellValue(map.get(head));
                    cell.setCellStyle(textStyle);
                    j++;
                }
            }
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
        }
        wb.close();
    }

    /**
     * xls文件格式输出
     * @param dataMap
     * @param file
     * @throws IOException
     */
    public void writeLess(Map<String, Map<String, List<String>>> dataMap, File file) throws IOException {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet
        HSSFSheet sheet = null;
        // 创建一行
        HSSFRow row = null;
        // 创建一个单元格
        HSSFCell cell = null;
        // 数据
        Map<String, List<String>> childMap = null;
        // 数据头作为标题
        Set<String> heads = null;
        HSSFCellStyle titleStyle = writer.getTitleStyle(wb);
        HSSFCellStyle textStyle = writer.getTextStyle(wb);
        FileOutputStream fos = null;
        for (Map.Entry<String, Map<String, List<String>>> entry : dataMap.entrySet()) {
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            sheet = wb.createSheet(entry.getKey());
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            childMap = entry.getValue();
            heads = new LinkedHashSet<>();
            if (childMap != null && !childMap.isEmpty()) {
                heads = childMap.keySet();
            }
            int count = 0;
            for (Map.Entry<String, List<String>> entryChild : childMap.entrySet()) {
                count = entryChild.getValue().size();
            }
            // 添加第一行标题
            int i = 0;
            for (String head : heads) {
                cell = row.createCell(i);
                // 单元格宽度
                sheet.setColumnWidth(i, 5000);
                sheet.setAutobreaks(true);
                cell.setCellValue(head);
                // 标题需要加粗
                cell.setCellStyle(titleStyle);
                i++;
            }
            int rownum = 1;
            int r = 0;
            while (true) {
                if (r >= count) {
                    break;
                }
                row = sheet.createRow(rownum++);
                int c = 0;
                for (Map.Entry<String, List<String>> entryChild : childMap.entrySet()) {
                    cell = row.createCell(c);
                    cell.setCellValue(entryChild.getValue().get(r));
                    cell.setCellStyle(textStyle);
                    c++;
                }
                r++;
            }
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
        }
        wb.close();
    }

}
