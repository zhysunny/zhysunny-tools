# 添加hive自定义函数

    package com.zhysunny.hive.udf;
    
    import org.apache.hadoop.hive.ql.exec.UDF;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Date;
    
    /**
     * 将生日转为年龄
     * @author 章云
     * @date 2019/11/13 15:51
     */
    public class BirthToAge extends UDF {
    
        /**
         * 将生日转为年龄
         * @param date 默认yyyy-MM-dd
         * @return
         */
        public int toAge(String date) throws ParseException {
            return toAge(date, "yyyy-MM-dd");
        }
    
        public int toAge(String date, String format) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat from = new SimpleDateFormat(format);
            Date parse = from.parse(date);
            int birth = Integer.parseInt(sdf.format(parse));
            // 当前时间
            Calendar calendar = Calendar.getInstance();
            int today = Integer.parseInt(sdf.format(calendar.getTime()));
            return today - birth;
        }
    
    }
    
* 打包上传到linux服务器，我的目录是/opt/zhysunny-deploy/tools/lib

* hive客户端操作


    hive> add jar /opt/zhysunny-deploy/tools/lib/zhysunny-hive-1.1.jar;
    hive> create temporary function to_age as 'com.zhysunny.hive.udf.BirthToAge';
    
    hive> drop temporary function to_age;
    
    
    select avg(age) age from(select to_age(birth) age from test.student) t;
    select avg_age(birth) age from test.student;