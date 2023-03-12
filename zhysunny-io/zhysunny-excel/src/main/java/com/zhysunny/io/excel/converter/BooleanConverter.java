package com.zhysunny.io.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author zhysunny
 * @date 2023/3/12 10:01
 */
public class BooleanConverter implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
            GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        return "是".equals(value) ? 1 : 0;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty excelContentProperty,
            GlobalConfiguration globalConfiguration) throws Exception {
        return value == 1 ? new CellData("是") : new CellData("否");
    }
}
