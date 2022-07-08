package com.example.aspecttest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class UserAspect {

    @Before("execution(* com.example.aspecttest.service.*.*(..))")
    public void beforeTransaction(JoinPoint joinPoint){
        joinPoint.getArgs();
        System.out.println("Before executing: " + joinPoint);
    }

    @AfterReturning("execution(* com.example.aspecttest.service.*.*(..))")
    public void afterReturning(JoinPoint joinPoint){
        System.out.println("afterReturning executing: " + joinPoint);
    }

    @After("execution(* com.example.aspecttest.service.*.*(..))")
    public void after(JoinPoint joinPoint){
        System.out.println("after executing: " + joinPoint);
    }
}
