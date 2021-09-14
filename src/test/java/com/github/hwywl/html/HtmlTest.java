package com.github.hwywl.html;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YI
 * @description
 * @date create in 2021/9/7 11:13
 */
public class HtmlTest {
    @Test
    public void htmlReadTest() {
        String template = EmailTemplate.htmlMapTemplate("测试", getModels());
        System.out.println(template);
    }

    @Test
    public void htmlReadBeanTest() {
        String template = EmailTemplate.htmlBeanTemplate("测试", getBeanModels());
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
