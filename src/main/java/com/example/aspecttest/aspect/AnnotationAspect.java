package com.example.aspecttest.aspect;

import com.example.aspecttest.model.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AnnotationAspect {

    @Before("@annotation(com.example.aspecttest.interfaces.LogExecutionTime)")
    public void logExecutionTime(JoinPoint joinPoint) throws Throwable {
        System.out.println("Before executing any method with the annotation LogExecutionTime");
        Object[] extractValues = joinPoint.getArgs();
        Employee emp = (Employee) extractValues[1];
        System.out.println("Extraction:  " + emp);
        System.out.println("value: " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("signature: " + joinPoint.getSignature());
    }
}
