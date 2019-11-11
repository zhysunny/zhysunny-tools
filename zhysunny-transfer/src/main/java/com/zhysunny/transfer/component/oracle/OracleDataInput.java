package com.zhysunny.transfer.component.oracle;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.driver.JdbcConnection;
import com.zhysunny.transfer.AbstractDataInputForDB;
import com.zhysunny.transfer.DataInput;
import com.zhysunny.transfer.constant.Constants;
import com.zhysunny.transfer.mapping.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * oracle数据输入
 * @author 章云
 * @date 2019/11/11 22:39
 */
public class OracleDataInput extends AbstractDataInputForDB {

    private static final Logger LOGGER = LoggerFactory.getLogger(OracleDataInput.class);

    private Mapping mapping;
    private int start;

    public OracleDataInput(Mapping mapping) {
        super();
        this.mapping = mapping;
        this.start = 0;
        check();
    }

    @Override
    public List<JSONObject> input() throws Exception {
        return null;
    }

    private void check() {
        tables = tables.stream().filter(table -> {
            JdbcConnection connection = null;
            try {
                connection = new JdbcConnection(Constants.JDBC_CONNECTION_CONFIGS_INPUT);
                return connection.existsTable(table);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

}
