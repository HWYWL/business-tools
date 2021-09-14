package com.github.hwywl.html;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.github.hwywl.annotation.HtmlAnnotation;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author YI
 * @description 加载html模板
 * @date create in 2021/9/7 10:36
 */
public class EmailTemplate {
    /**
     * 邮件的表格头部
     */
    private static final String titleTemplate = "<th>{topName}</th>";

    /**
     * 邮件的数据列表
     */
    private static final String dataTemplate = "<td>{value}</td>";

    /**
     * 根据map转换为html
     *
     * @param title 标题
     * @param data  数据
     * @return 生成的html
     */
    public static String htmlMapTemplate(String title, List<Map<String, Object>> data) {
        if (CollUtil.isEmpty(data)) {
            return null;
        }
        InputStream inputStream = EmailTemplate.class.getResourceAsStream("/templates/email.html");

        // 拼装头部标题
        StringBuilder titleBuffer = new StringBuilder();
        for (String key : data.get(0).keySet()) {
            titleBuffer.append(StrUtil.format(titleTemplate, Dict.create().set("topName", key)));
        }

        // 拼装数据
        StringBuffer dataBuffer = new StringBuffer();
        data.forEach(map -> {
            dataBuffer.append("<tr>");
            map.values().forEach(value -> {
                value = value == null ? "" : value;
                dataBuffer.append(StrUtil.format(dataTemplate, Dict.create().set("value", value)));
            });
            dataBuffer.append("</tr>");
        });

        // 组装html
        String readStr = IoUtil.read(inputStream, CharsetUtil.CHARSET_UTF_8);
        return StrUtil.format(readStr, Dict.create().set("title", title)
                .set("listTitle", titleBuffer.toString())
                .set("listData", dataBuffer.toString()));
    }

    /**
     * 根据bean转为html
     *
     * @param title 标题
     * @param data  数据
     * @param <T>   泛型
     * @return 生成的html
     */
    public static <T> String htmlBeanTemplate(String title, List<T> data) {
        if (CollUtil.isEmpty(data)) {
            return null;
        }
        InputStream inputStream = EmailTemplate.class.getResourceAsStream("/templates/email.html");

        // 获得字段注解,拼装头部标题
        StringBuilder titleBuffer = new StringBuilder();
        Field[] fields = data.get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            HtmlAnnotation fieldAnnotation = field.getAnnotation(HtmlAnnotation.class);
            titleBuffer.append(StrUtil.format(titleTemplate, Dict.create().set("topName", fieldAnnotation.value())));
        }

        // 拼装数据
        StringBuffer dataBuffer = new StringBuffer();
        data.forEach(bean -> {
            dataBuffer.append("<tr>");
            for (Field field : fields) {
                try {
                    Object value = field.get(bean);
                    value = value == null ? "" : value;
                    dataBuffer.append(StrUtil.format(dataTemplate, Dict.create().set("value", value)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            dataBuffer.append("</tr>");
        });

        // 组装html
        String readStr = IoUtil.read(inputStream, CharsetUtil.CHARSET_UTF_8);
        return StrUtil.format(readStr, Dict.create().set("title", title)
                .set("listTitle", titleBuffer.toString())
                .set("listData", dataBuffer.toString()));
    }
}
