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
    //获取开始时间
    private long BEGIN_TIME ;

    //获取结束时间
    private long END_TIME;

    //定义本次log实体
    private Log log = new Log();

    @Pointcut("execution(* com.wang.web..*.*(..))")
    private void controllerAspect(){}

    /**
     * 方法开始执行
     */
    @Before("controllerAspect()")
    public void doBefore(){
        BEGIN_TIME = new Date().getTime();
        System.out.println("开始");
    }

    /**
     * 方法结束执行
     */
    @After("controllerAspect()")
    public void after(){
        END_TIME = new Date().getTime();
        System.out.println("结束");
    }

    /**
     * 方法结束执行后的操作
     */
    @AfterReturning("controllerAspect()")
    public void doAfter(){

        if(log.getState()==1||log.getState()==-1){
            log.setActionTime(END_TIME-BEGIN_TIME);
            log.setGmtCreate(new Date(BEGIN_TIME));
            System.out.println(log);
            System.out.println(">>>>>>>>>>存入到数据库");
        }else {
            System.out.println(log);
            System.out.println(">>>>>>>>不存入到数据库");
        }
    }

    /**
     * 方法有异常时的操作
     */
    @AfterThrowing("controllerAspect()")
    public void doAfterThrow(){
        System.out.println("例外通知-----------------------------------");
    }


    /**
     * 方法执行
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        //日志实体对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取当前登陆用户信息
        User loginUser = null;
        if(loginUser==null){
            log.setLoginAccount("—— ——");
        }else{
            log.setLoginAccount(loginUser.getUsername());
        }

        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
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
            // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
            if (method.isAnnotationPresent(SystemLog.class)) {
                SystemLog systemlog = method.getAnnotation(SystemLog.class);
                log.setModule(systemlog.module());
                log.setMethod(systemlog.methods());
                log.setLoginIp(getIp(request));
                log.setActionUrl(request.getRequestURI());

                try {
                    object = pjp.proceed();
                    log.setDescription("执行成功");
                    log.setState((short) 1);
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    log.setDescription("执行失败");
                    log.setState((short)-1);
                }
            } else {//没有包含注解
                object = pjp.proceed();
                log.setDescription("此操作不包含注解");
                log.setState((short)0);
            }
        } else { //不需要拦截直接执行
            object = pjp.proceed();
            log.setDescription("不需要拦截直接执行");
            log.setState((short)0);
        }
        return object;
    }

    /**
     * 获取ip地址
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