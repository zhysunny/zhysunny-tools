package com.zhysunny.io.excel;

import com.zhysunny.io.BaseReader;
import com.zhysunny.io.excel.reader.ExcelToMap;
import com.zhysunny.io.exception.ExcelException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel读取类
 * @author 章云
 * @date 2019/7/25 17:41
 */
public class ExcelReader extends BaseReader implements Closeable {

    /**
     * 工作簿
     */
    private Workbook wb;
    /**
     * excel输入流
     */
    private InputStream is;
    /**
     * sheet对象
     */
    private Sheet sheet;
    /**
     * 第一行作为表头，记录的key值
     */
    private List<String> heads;
    /**
     * sheet数
     */
    private int sheetCount;
    /**
     * sheet名称
     */
    private String sheetName;
    /**
     * 总行数
     */
    private int rowCount;
    /**
     * 当前行数
     */
    private int rowNum;

    public ExcelReader(File file) {
        super(file);
    }

    public ExcelReader(String path) {
        super(path);
    }

    public ExcelReader(URL url) {
        super(url);
    }

    @Override
    public ExcelReader builder() {
        try {
            String filePath = "";
            Object resource = resources.get(0);
            if (resource instanceof URL) {
                URL url = (URL)resource;
                if (url != null) {
                    filePath = url.toString();
                    is = url.openStream();
                }
            } else if (resource instanceof File) {
                File file = (File)resource;
                if (file.exists()) {
                    filePath = file.getName();
                    is = new FileInputStream(file);
                }
            } else if (resource instanceof String) {
                filePath = (String)resource;
                File file = new File(filePath);
                URL url = Thread.currentThread().getContextClassLoader().getResource(filePath);
                if (url != null) {
                    is = url.openStream();
                } else {
                    is = new FileInputStream(file);
                }
            } else {
                throw new ExcelException("不支持的资源配置类型：" + resource.getClass());
            }
            if (filePath.endsWith(".xls")) {
                wb = new HSSFWorkbook(is);
            } else if (filePath.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(is);
            }
            sheetCount = wb.getNumberOfSheets();
        } catch (IOException e) {
            throw new ExcelException(e);
        }
        return this;
    }

    /**
     * 读取sheet索引并获取表头
     * @param sheetIndex sheet索引
     * @return
     */
    public BaseReader readTitle(int sheetIndex) {
        if (wb == null) {
            throw new ExcelException("Workbook对象为空！");
        }
        if (sheetIndex < 0 || sheetIndex >= sheetCount) {
            throw new ExcelException("sheet一共" + sheetCount + "个," + sheetIndex + "不在范围内");
        }
        sheet = wb.getSheetAt(sheetIndex);
        sheetName = sheet.getSheetName();
        rowCount = sheet.getLastRowNum();
        rowNum = 1;
        Row row = sheet.getRow(0);
        if (row == null) {
            throw new ExcelException("第一行标题不能为空！");
        }
        int minCell = row.getFirstCellNum();
        int maxCell = row.getLastCellNum();
        heads = new ArrayList<String>(maxCell - minCell);
        for (int i = minCell; i < maxCell; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                continue;
            }
            heads.add(cell.getStringCellValue());
        }
        return this;
    }

    public Map<String, Map<String, List<String>>> readToLessMap() {
        if (wb == null) {
            builder();
        }
        return new ExcelToMap(this).readLess();
    }

    @Deprecated
    public Map<String, List<Map<String, String>>> readToMap() {
        if (wb == null) {
            builder();
        }
        return new ExcelToMap(this).read();
    }

    /**
     * 读取正文数据
     * @return
     */
    public Map<String, String> readLine() {
        Map<String, String> map = new LinkedHashMap<>();
        if (wb == null) {
            throw new ExcelException("Workbook对象为空！");
        }
        if (sheet == null) {
            throw new ExcelException("Sheet对象为空！");
        }
        if (heads == null) {
            throw new ExcelException("第一行标题为空！");
        }
        if (rowNum > getRowCount()) {
            return null;
        }
        Row row = sheet.getRow(rowNum);
        rowNum++;
        if (row == null) {
            return null;
        }
        int minCell = row.getFirstCellNum();
        int maxCell = row.getLastCellNum();
        for (int i = minCell; i < maxCell; i++) {
            if (i >= heads.size()) {
                break;
            }
            String key = heads.get(i);
            Cell cell = row.getCell(i);
            if (cell == null) {
                continue;
            }
            map.put(key, getStringCellValue(cell));
        }
        boolean flag = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (null != entry.getValue() && !"".equals(entry.getValue())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return map;
        }
        return null;
    }

    /**
     * 获取总行数
     * @return
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * 获取sheet总数
     * @return
     */
    public int getSheetCount() {
        return sheetCount;
    }

    /**
     * 获取sheet名称
     * @return
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * 关闭资源
     */
    @Override
    public void close() {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                throw new ExcelException(e);
            }
        }
        if (wb != null) {
            try {
                wb.close();
            } catch (IOException e) {
                throw new ExcelException(e);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private final String getStringCellValue(Cell cell) {
        String strCell = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        strCell = sdf.format(cell.getDateCellValue());
                    } else {
                        strCell = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    strCell = "";
                    break;
                default:
                    strCell = "";
                    break;
            }
        }
        return cell == null || strCell == null ? "" : strCell;
    }

}
