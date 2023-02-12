package com.zhysunny.transfer.util;

import com.zhysunny.driver.util.JdbcConfig;
import com.zhysunny.io.properties.AbstractTypeConversion;
import org.apache.commons.lang3.StringUtils;

/**
 * String和JdbcConfig互转
 * @author 章云
 * @date 2019/11/11 23:38
 */
public class JdbcConfigTypeConversion extends AbstractTypeConversion<JdbcConfig> {

    public JdbcConfigTypeConversion(String param) {
        super(param);
    }

    @Override
    public JdbcConfig conversion(String value) {
        return new JdbcConfig(value.split(this.param));
    }

    @Override
    public String toString(JdbcConfig jdbcConfig) {
        String[] array = { jdbcConfig.getClassName(), jdbcConfig.getJdbcUrl(), jdbcConfig.getJdbcUsername(), jdbcConfig.getJdbcPassword() };
        return StringUtils.join(array, ",");
    }

}
