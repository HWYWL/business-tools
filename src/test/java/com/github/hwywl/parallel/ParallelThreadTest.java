package com.github.hwywl.parallel;

import com.github.hwywl.html.HtmlModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YI create in 2021/12/16 16:01
 * @description
 */
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
