package com.zhysunny.io.excel.writer;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import java.util.stream.IntStream;

/**
 * @author zhysunny
 * @date 2023/3/12 10:15
 */
public class DefaultWriterHandler extends AbstractRowWriteHandler {
    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
            Integer relativeRowIndex, Boolean isHead) {
        // 获取当前所有列
        int size = row.getPhysicalNumberOfCells();
        IntStream.rangeClosed(0, size - 1).forEach(i -> {
            Cell cell = row.getCell(i);
            // 创建单元格样式
            CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
            // 克隆当前的样式
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            cell.setCellStyle(cellStyle);
            setCellStyle(writeSheetHolder, isHead, cell, cellStyle);
            // 设置字体
            Font font = writeSheetHolder.getSheet().getWorkbook().createFont();
            cellStyle.setFont(font);
            setFront(writeSheetHolder, isHead, cell, font);
        });
    }

    public void setFront(WriteSheetHolder writeSheetHolder, Boolean isHead, Cell cell, Font font) {
        if (isHead) {
            font.setFontHeight((short) (14 * 20));
            font.setBold(true);
        } else {
            font.setFontHeight((short) (12 * 20));
        }
        font.setFontName("微软雅黑");
        // 超链接
        String stringCellValue = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
        if (stringCellValue != null && (stringCellValue.startsWith("http://") || stringCellValue
                .startsWith("https://"))) {
            CreationHelper creationHelper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
            Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
            hyperlink.setAddress(stringCellValue);
            cell.setHyperlink(hyperlink);
            // 超链接样式
            font.setUnderline((byte) 1);
            font.setColor(IndexedColors.BLUE.getIndex());
        }
    }

    public void setCellStyle(WriteSheetHolder writeSheetHolder, Boolean isHead, Cell cell, CellStyle cellStyle) {
        // 设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        // 自动换行
        cellStyle.setWrapText(true);
    }
}
