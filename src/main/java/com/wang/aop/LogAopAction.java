package com.wang.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wang.annotation.SystemLog;
import com.wang.domain.Log;
import com.wang.domain.User;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 
 */
@Aspect
public class LogAopAction {
    //��ȡ��ʼʱ��
    private long BEGIN_TIME ;

    //��ȡ����ʱ��
    private long END_TIME;

    //���屾��logʵ��
    private Log log = new Log();

    @Pointcut("execution(* com.wang.web..*.*(..))")
    private void controllerAspect(){}

    /**
     * ������ʼִ��
     */
    @Before("controllerAspect()")
    public void doBefore(){
        BEGIN_TIME = new Date().getTime();
        System.out.println("��ʼ");
    }

    /**
     * ��������ִ��
     */
    @After("controllerAspect()")
    public void after(){
        END_TIME = new Date().getTime();
        System.out.println("����");
    }

    /**
     * ��������ִ�к�Ĳ���
     */
    @AfterReturning("controllerAspect()")
    public void doAfter(){

        if(log.getState()==1||log.getState()==-1){
            log.setActionTime(END_TIME-BEGIN_TIME);
            log.setGmtCreate(new Date(BEGIN_TIME));
            System.out.println(log);
            System.out.println(">>>>>>>>>>���뵽���ݿ�");
        }else {
            System.out.println(log);
            System.out.println(">>>>>>>>�����뵽���ݿ�");
        }
    }

    /**
     * �������쳣ʱ�Ĳ���
     */
    @AfterThrowing("controllerAspect()")
    public void doAfterThrow(){
        System.out.println("����֪ͨ-----------------------------------");
    }


    /**
     * ����ִ��
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        //��־ʵ�����
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //��ȡ��ǰ��½�û���Ϣ
        User loginUser = null;
        if(loginUser==null){
            log.setLoginAccount("���� ����");
        }else{
            log.setLoginAccount(loginUser.getUsername());
        }

        // ���ص�ʵ���࣬���ǵ�ǰ����ִ�е�controller
        Object target = pjp.getTarget();
        // ���صķ������ơ���ǰ����ִ�еķ���
        String methodName = pjp.getSignature().getName();
        // ���صķ�������
        Object[] args = pjp.getArgs();
        // ���صķŲ�������
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("��ע��ֻ�����ڷ���");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

        Object object = null;

        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (null != method) {
            // �ж��Ƿ�����Զ����ע�⣬˵��һ�������SystemLog�������Լ��Զ����ע��
            if (method.isAnnotationPresent(SystemLog.class)) {
                SystemLog systemlog = method.getAnnotation(SystemLog.class);
                log.setModule(systemlog.module());
                log.setMethod(systemlog.methods());
                log.setLoginIp(getIp(request));
                log.setActionUrl(request.getRequestURI());

                try {
                    object = pjp.proceed();
                    log.setDescription("ִ�гɹ�");
                    log.setState((short) 1);
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    log.setDescription("ִ��ʧ��");
                    log.setState((short)-1);
                }
            } else {//û�а���ע��
                object = pjp.proceed();
                log.setDescription("�˲���������ע��");
                log.setState((short)0);
            }
        } else { //����Ҫ����ֱ��ִ��
            object = pjp.proceed();
            log.setDescription("����Ҫ����ֱ��ִ��");
            log.setState((short)0);
        }
        return object;
    }

    /**
     * ��ȡip��ַ
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request){
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}