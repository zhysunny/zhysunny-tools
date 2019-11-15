package com.zhysunny.hive.udaf;

import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.io.DoubleWritable;
import org.junit.*;
import static org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * AvgBirthToAge Test.
 * @author 章云
 * @date 2019/11/14 15:43
 */
public class AvgBirthToAgeTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test AvgBirthToAge Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test AvgBirthToAge Class End...");
    }

    /**
     * Method: getEvaluator(TypeInfo[] parameters)
     */
    @Test
    public void testGetEvaluator() throws Exception {
        PrimitiveTypeInfo typeInfo = mock(PrimitiveTypeInfo.class);
        when(typeInfo.getPrimitiveCategory()).thenReturn(STRING);
        when(typeInfo.getCategory()).thenReturn(ObjectInspector.Category.PRIMITIVE);
        TypeInfo[] param = new TypeInfo[]{ typeInfo };
        AvgBirthToAge avgBirthToAge = new AvgBirthToAge();
        GenericUDAFEvaluator evaluator = avgBirthToAge.getEvaluator(param);
        evaluator
        .init(GenericUDAFEvaluator.Mode.FINAL, new ObjectInspector[]{ PrimitiveObjectInspectorFactory.writableStringObjectInspector });
        GenericUDAFEvaluator.AggregationBuffer agg = evaluator.getNewAggregationBuffer();
        evaluator.iterate(agg, new String[]{ "1994-01-09" });
        evaluator.iterate(agg, new String[]{ "1996-01-09" });
        DoubleWritable result = (DoubleWritable)evaluator.terminatePartial(agg);
        System.out.println(result.get());
    }

}
