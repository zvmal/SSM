package com.wang.annotation;

import java.lang.annotation.*;


@Target({ ElementType.PARAMETER, ElementType.METHOD }) // 表明只能定义在方法参数和方法上面
@Retention(RetentionPolicy.RUNTIME) //保留策略是RUNTIME，在JVM加载类时，会把注解加载到JVM内存中（它是唯一可以用反射来读取注解的策略）
@Documented //@Documented用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。
public @interface SystemLog {
	String module() default "";

	String methods() default "";
}