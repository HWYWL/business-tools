package com.github.hwywl.parquet;

import com.github.hwywl.exception.CustomException;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetWriter;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author YI
 * @description
 * @Test
 * @date create in 2021/9/2 18:38
 */
public class ParquetUtilTest {
    String filePath = "F:\\a.parquet";

    /**
     * 写入一个数据到parquet文件
     *
     * @throws IOException
     * @throws CustomException
     */
    @Test
    public void ParquetWriteTest() throws IOException, CustomException, IntrospectionException {
        TestModel model = getModel();
        ParquetUtil.writerParquet(filePath, model);
    }

    /**
     * 写入多个数据到parquet文件
     *
     * @throws IOException
     * @throws CustomException
     */
    @Test
    public void ParquetWriteListTest() throws IOException, CustomException, IntrospectionException {
        List<TestModel> models = getModels();
        ParquetUtil.writerParquet(filePath, models);
    }

    /**
     * 开放原始API，可以用于非一次性写入
     *
     * @throws IOException
     * @throws CustomException
     * @throws IntrospectionException
     */
    @Test
    public void ParquetWriteGroupListTest() throws IOException, CustomException, IntrospectionException {
        ParquetWriter<Group> writer = ParquetUtil.getParquetWriter(filePath, TestModel.class);
        Group group = ParquetUtil.getGroup(getModel());
        writer.write(group);

        // close写出文件
        writer.close();
    }

    /**
     * 将parquet文件转为对象集合
     *
     * @throws IOException
     */
    @Test
    public void ParquetReadBeanTest() throws IOException {
        List<TestModel> models = ParquetUtil.readParquetBean(filePath, TestModel.class);
        System.out.println(models);
    }

    /**
     * 将parquet文件转为Map集合
     *
     * @throws IOException
     */
    @Test
    public void ParquetReadMapTest() throws IOException {
        List<Map<String, Object>> maps = ParquetUtil.readParquetMap(filePath);
        System.out.println(maps);
    }

    /**
     * 将parquet文件转为对象集合,并只拿2条数据
     *
     * @throws IOException
     */
    @Test
    public void ParquetReadBeanMaxLineTest() throws IOException {
        List<TestModel> models = ParquetUtil.readParquetBean(filePath, 2, TestModel.class);
        System.out.println(models);
    }

    /**
     * 测试数据
     *
     * @return
     */
    private TestModel getModel() {
        return new TestModel(2, 3, 6L, "校花", 10);
    }

    /**
     * 测试数据
     *
     * @return
     */
    private List<TestModel> getModels() {
        List<TestModel> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add(new TestModel(2, 3, 6L, "校花", 10));
        }

        return arrayList;
    }
}
