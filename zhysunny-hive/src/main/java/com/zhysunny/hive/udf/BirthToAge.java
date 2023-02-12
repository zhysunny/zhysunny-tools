package com.zhysunny.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 将生日转为年龄
 *
 * @author 章云
 * @date 2019/11/13 15:51
 */
@Description(name = "to_age", value = "_FUNC_(date[, format]) - Returns age",
        extended = "Example:\n  > SELECT _FUNC_('1994-01-09') FROM src LIMIT 1;\n  25")
public class BirthToAge extends UDF {

    private final IntWritable result = new IntWritable();

    /**
     * 将生日转为年龄
     *
     * @param date 默认yyyy-MM-dd
     * @return
     */
    public IntWritable evaluate(String date) throws ParseException {
        return evaluate(date, "yyyy-MM-dd");
    }

    public IntWritable evaluate(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat from = new SimpleDateFormat(format);
        Date parse = from.parse(date);
        int birth = Integer.parseInt(sdf.format(parse));
        // 当前时间
        Calendar calendar = Calendar.getInstance();
        int today = Integer.parseInt(sdf.format(calendar.getTime()));
        result.set(today - birth);
        return result;
    }

}
