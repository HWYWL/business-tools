package com.github.hwywl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlAnnotation {
    /**
     * html表格头部
     *
     * @return 值
     */
    String value() default "";
}
