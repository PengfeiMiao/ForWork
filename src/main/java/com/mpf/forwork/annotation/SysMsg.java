package com.mpf.forwork.annotation;

import java.lang.annotation.*;

/**
 * @Author: MiaoPengfei
 * @Description:
 * @Date: Create in 10:09 2020/9/15
 */
//声明注解类注解的范围，是变量，还是方法，还是类，还是参数,此处只为变量
@Target(ElementType.METHOD)
//最重要的：切面作用域
@Retention(RetentionPolicy.RUNTIME)
//文档声明,会被javadoc文档工具记录
@Documented
public @interface SysMsg {
}
