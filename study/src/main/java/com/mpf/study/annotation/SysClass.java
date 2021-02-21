package com.mpf.study.annotation;

import java.lang.annotation.*;

/**
 * @Author: MiaoPengfei
 * @Date: 2021/1/11 15:19
 * @Description:
 */
//声明注解类注解的范围，是变量，还是方法，还是类，还是参数,此处只为变量
@Target({ElementType.TYPE, ElementType.METHOD})
//最重要的：切面作用域
@Retention(RetentionPolicy.RUNTIME)
//文档声明,会被javadoc文档工具记录
@Documented
public @interface SysClass {
}
