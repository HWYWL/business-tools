# business-tools
在开发中积攒下来的业务工具类，方便快速编写业务。

[![author](https://img.shields.io/badge/author-HWY-red.svg)](https://github.com/HWYWL) [![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu) 

- [business-tools](#business-tools)
  * [安装](#--)
  * [一个增强parquet文件方便读写的工具类](#----parquet----------)
    + [使用](#--)
  * [HTML表格生成，可用于邮件发送或者直接展示前端](#html--------------------)
    + [示例1：](#--1-)
    + [示例2：](#--2-)
  * [1.0.1-RELEASE 版本更新](#101-release-----)
    + [感谢](#--)
    + [问题建议](#----)

## 安装
**maven**
```
<dependency>
    <groupId>com.github.hwywl</groupId>
    <artifactId>business-tools</artifactId>
    <version>1.0.1-RELEASE</version>
</dependency>
```

**Gradle**
```
implementation 'com.github.hwywl:business-tools:1.0.1-RELEASE'
```

## 一个增强parquet文件方便读写的工具类
通过反射自动生成MessageType，自动拼装数据结构，遵循简单的原则，你给我数据我给你parquet文件。
目前支持的数据类型如下：
- int、Integer
- long、Long
- float、Float
- double、Double
- String

### 使用

可以查看github中的源码，不看也行，很简单的，使用如下：
```java
public class ParquetTest {
    String filePath = "F:\\a.parquet";

    /**
     * 写入一个数据到parquet文件
     *
     * @throws IOException
     * @throws CustomException
     */
    @Test
    public void ParquetWriteTest() throws IOException, CustomException {
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
    public void ParquetWriteListTest() throws IOException, CustomException {
        List<TestModel> models = getModels();
        ParquetUtil.writerParquet(filePath, models);
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
```


## HTML表格生成，可用于邮件发送或者直接展示前端 
### 示例1：
```
public class HtmlTest {
    @Test
    public void htmlReadTest() {
        String template = EmailTemplateUtil.htmlMapTemplate("测试", getModels());
        System.out.println(template);
    }

    /**
     * 测试数据
     *
     * @return 测试数据
     */
    private List<Map<String, Object>> getModels() {
        List<Map<String, Object>> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("校花", "张花");
            map.put("美女", "李花");
            map.put("小萝莉", "茉莉花");
            map.put("御姐", null);
            map.put("班花", 111);

            arrayList.add(map);
        }

        return arrayList;
    }
}
```
![](https://hwy-figure-bed.oss-cn-hangzhou.aliyuncs.com/blog/image/1630996191028-ceshi2.png)

### 示例2：
```
public class HtmlTest {
    @Test
    public void htmlReadBeanTest() {
        String template = EmailTemplateUtil.htmlBeanTemplate("测试", getBeanModels());
        System.out.println(template);
    }

    /**
     * 测试数据
     *
     * @return
     */
    private List<HtmlModel> getBeanModels() {
        List<HtmlModel> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add(new HtmlModel(2, 3, 6L, "校花", 10));
        }

        return arrayList;
    }
}
```

HtmlModel类
```
public class HtmlModel {
    @HtmlAnnotation("游戏id")
    int app_id;
    @HtmlAnnotation("VIP等级")
    Integer svip_level;
    @HtmlAnnotation("VIP剩余时间")
    Long svip_remain;
    @HtmlAnnotation("游戏名称")
    String name;
    @HtmlAnnotation("充值金额")
    double money;
}
```
![](https://hwy-figure-bed.oss-cn-hangzhou.aliyuncs.com/blog/image/1630996121533-ceshi.png)

## 1.0.1-RELEASE 版本更新
1. 增加parquet文件的读写
2. 增加html表格的生成。

### 感谢
- 工具还有不足之处，请大家Issues ヾ(๑╹◡╹)ﾉ"
- 我那么可爱你不点个star吗 φ(>ω<*) 


### 问题建议

- 联系我的邮箱：ilovey_hwy@163.com
- 我的博客：https://www.hwy.ac.cn
- GitHub：https://github.com/HWYWL