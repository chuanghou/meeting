package com.stellariver.meeting.aspectj;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspect {

    @Pointcut("execution(* com.stellariver.meeting.application.UserAbility.registerUser())")
    private void pointCut() {}

    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before" + joinPoint.toShortString());
        return joinPoint.proceed();
    }

}
