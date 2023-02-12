package com.zhysunny.hive.udaf;

import com.zhysunny.hive.udf.BirthToAge;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.io.DoubleWritable;
import java.text.ParseException;

/**
 * 对生日字段取年龄的平均值
 *
 * @author 章云
 * @date 2019/11/14 8:57
 */
@Description(name = "avg_age", value = "_FUNC_(x) - Returns the mean of a set of birth")
public class AvgBirthToAge extends AbstractGenericUDAFResolver {

    static final Log LOG = LogFactory.getLog(AvgBirthToAge.class.getName());

    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters) throws SemanticException {
        if (parameters.length != 1) {
            throw new UDFArgumentTypeException(parameters.length - 1, "Exactly one argument is expected.");
        } else if (parameters[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentTypeException(0,
                    "Only primitive type arguments are accepted but " + parameters[0].getTypeName() + " is passed.");
        } else {
            switch (((PrimitiveTypeInfo) parameters[0]).getPrimitiveCategory()) {
                case STRING:
                    return new GenericUDAFEvaluatorAvgBirthToAge();
                default:
                    throw new UDFArgumentTypeException(0,
                            "Only numeric or string type arguments are accepted but " + parameters[0].getTypeName()
                                    + " is passed.");
            }
        }
    }

    private static class GenericUDAFEvaluatorAvgBirthToAge extends GenericUDAFEvaluator {

        private BirthToAge birthToAge = new BirthToAge();

        private DoubleWritable result = new DoubleWritable(10.0);

        @Override
        public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
            super.init(m, parameters);
            return PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        }

        /**
         * 创建新的AverageAgg对象
         *
         * @return
         * @throws HiveException
         */
        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            AverageAgg averageAgg = new AverageAgg();
            this.reset(averageAgg);
            return averageAgg;
        }

        /**
         * 初始化AverageAgg
         *
         * @param aggregationBuffer
         * @throws HiveException
         */
        @Override
        public void reset(AggregationBuffer aggregationBuffer) throws HiveException {
            AverageAgg averageAgg = (AverageAgg) aggregationBuffer;
            averageAgg.count = 0;
            averageAgg.sum = 0.0;
        }

        /**
         * map阶段调用，只要把保存当前和的对象agg，再加上输入的参数，就可以了。
         *
         * @param aggregationBuffer
         * @param objects
         * @throws HiveException
         */
        @Override
        public void iterate(AggregationBuffer aggregationBuffer, Object[] objects) throws HiveException {
            merge(aggregationBuffer, objects[0]);
        }

        @Override
        public Object terminatePartial(AggregationBuffer aggregationBuffer) throws HiveException {
            return terminate(aggregationBuffer);
        }

        @Override
        public void merge(AggregationBuffer aggregationBuffer, Object partial) throws HiveException {
            if (partial != null) {
                AverageAgg averageAgg = (AverageAgg) aggregationBuffer;
                try {
                    averageAgg.sum += birthToAge.evaluate(partial.toString()).get();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                averageAgg.count += 1;
            }
        }

        @Override
        public Object terminate(AggregationBuffer aggregationBuffer) throws HiveException {
            AverageAgg averageAgg = (AverageAgg) aggregationBuffer;
            double value = averageAgg.sum / averageAgg.count;
            result.set(averageAgg.count);
            return result;
        }

        /**
         * 用于存储avg的数据
         */
        @AggregationType(estimable = true)
        static class AverageAgg extends AbstractAggregationBuffer {

            /**
             * 数据个数
             */
            long count;

            /**
             * 数据总和
             */
            double sum;

            AverageAgg() {
            }

            @Override
            public int estimate() {
                return 16;
            }

        }

    }

}
