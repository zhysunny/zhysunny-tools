package com.zhysunny.io.excel.writer;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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
        // 创建单元格样式
        CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
        // 获取当前所有列
        int size = row.getPhysicalNumberOfCells();
        IntStream.rangeClosed(0, size - 1).forEach(i -> {
            Cell cell = row.getCell(i);
            // 克隆当前的样式
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            // 设置字体
            Font font = writeSheetHolder.getSheet().getWorkbook().createFont();
            if (isHead) {
                font.setFontHeight((short) (14 * 20));
                font.setBold(true);
            } else {
                font.setFontHeight((short) (12 * 20));
            }
            font.setFontName("微软雅黑");
            cellStyle.setFont(font);
            // 设置边框
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            // 自动换行
            cellStyle.setWrapText(true);

            cell.setCellStyle(cellStyle);
        });
    }
}
