package com.example.demo.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author iCoderLad
 * @date 2022/09/01 16:34
 */
@Aspect
@Component
public class CommonAdvice {
    @Pointcut("execution (public * com.example.demo.controller.*.*(..))")
    public void test(){

    }

    @Before("test()")
    public void before(JoinPoint jp){
        System.out.println("-------- aop before --------");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("CLASS_METHOD : " + jp);
        System.out.println("ARGS : " + Arrays.toString(jp.getArgs()));
    }

    @Around("test()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("---aop around---");
        Object result = pjp.proceed();
        System.out.println(result);
        return result;
    }

    @AfterReturning(returning = "ret", pointcut = "test()")
    public void afterReturn(Object ret){
        System.out.println("aop afterReturn");
    }

    @After("test()")
    public void after(JoinPoint jp){
        System.out.println("aop after");
    }
}
