package com.misty.annotation.method;

import java.lang.annotation.*;

// ===== 四个基本的元注解 =====
/*
 * @Target 表示该注解可以声明在哪些目标元素之前
 *
 * ElementType.PACKAGE：该注解只能声明在一个包名前
 * ElementType.ANNOTATION_TYPE：该注解只能声明在一个注解类型前
 * ElementType.TYPE：该注解只能声明在一个类前 ✔class
 * ElementType.CONSTRUCTOR：该注解只能声明在一个类的构造方法前
 * ElementType.LOCAL_VARIABLE：该注解只能声明在一个局部变量前
 * ElementType.METHOD：该注解只能声明在一个类的方法前 ✔method
 * ElementType.PARAMETER：该注解只能声明在一个方法参数前 ✔parameter
 * ElementType.FIELD：该注解只能声明在一个类的字段前 ✔field
 */
@Target(ElementType.METHOD)
/*
 * @Retention: 告诉编译器注解类的生命周期
 *
 * RetentionPolicy.RUNTIME: 注解保留在程序运行期间
 * RetentionPolicy.SOURCE: 注解只保留在源文件中
 * RetentionPolicy.CLASS: 注解保留在class文件中，当加载到JVM虚拟机时丢弃
 */
@Retention(RetentionPolicy.RUNTIME)
/*
 * 一个类(接口除外)用上了@Inherited修饰的注解，那么其子类也会继承这个注解
 */
@Inherited
/*
 * 该注解会被javadoc工具记录
 */
@Documented
public @interface ScnAnnotation {

    String desc();
}
