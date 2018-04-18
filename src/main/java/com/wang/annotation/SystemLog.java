package com.wang.annotation;

import java.lang.annotation.*;


@Target({ ElementType.PARAMETER, ElementType.METHOD }) // ����ֻ�ܶ����ڷ��������ͷ�������
@Retention(RetentionPolicy.RUNTIME) //����������RUNTIME����JVM������ʱ�����ע����ص�JVM�ڴ��У�����Ψһ�����÷�������ȡע��Ĳ��ԣ�
@Documented //@Documented���������������͵�annotationӦ�ñ���Ϊ����ע�ĳ����Ա�Ĺ���API����˿��Ա�����javadoc����Ĺ����ĵ�����
public @interface SystemLog {
	String module() default "";

	String methods() default "";
}