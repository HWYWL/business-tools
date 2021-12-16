# business-tools
在开发中积攒下来的业务工具类，方便快速编写业务。

[![author](https://img.shields.io/badge/author-HWY-red.svg)](https://github.com/HWYWL) [![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu) 

## 安装
**maven**
```
<dependency>
    <groupId>com.github.hwywl</groupId>
    <artifactId>business-tools</artifactId>
    <version>1.0.3-RELEASE</version>
</dependency>
```

**Gradle**
```
implementation 'com.github.hwywl:business-tools:1.0.3-RELEASE'
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


## 一个简单封装的多线程并行工具类
在我们业务中很多场景为了速度是需要多线程并行运算的，最后再把各个结果集合起来返回。
示例：
```java
public class ParallelThreadTest {
    /**
     * 测试并发执行运算
     */
    @Test
    public void parallelTest() throws InterruptedException {
        List<HtmlModel> list = new ArrayList<>();

        // 模拟四个线程并发执行，最后得到一个结果集
        Runnable runnable1 = () -> list.addAll(getBeanModels());
        Runnable runnable2 = () -> list.addAll(getBeanModels());
        Runnable runnable3 = () -> list.addAll(getBeanModels());
        Runnable runnable4 = () -> list.addAll(getBeanModels());

        List<Runnable> tasks = Arrays.asList(runnable1, runnable2, runnable3, runnable4);

        int secs = ParallelThread.parallelThreadTask(tasks);

        // 这句代码在正式环境中没有意义，这里是为了防止主线程结束
        // Junit不支持多线程
        Thread.sleep(3000);

        System.out.println("线程耗时：" + secs + "毫秒");
        System.out.println(list);
    }

    /**
     * 测试数据
     *
     * @return
     */
    private List<HtmlModel> getBeanModels() {
        List<HtmlModel> arrayList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            arrayList.add(new HtmlModel(2, 3, 6L, "校花", 10));
        }

        return arrayList;
    }
}
```

## 1.0.3-RELEASE 版本更新
1. 增加一个简单的多线程并发执行工具
2. 升级开源工具类

## 1.0.2-RELEASE 版本更新
1. 开放parquet文件写入的原始API
2. 修复写入parquet文件乱序问题

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