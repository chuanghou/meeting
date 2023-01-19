package com.stellariver.meeting.aspectj;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspect {

    @Pointcut("execution(* com.stellariver.meeting.application.UserAbility.internalInvoke())")
    private void pointCut() {}

    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("\n Aspectj Aspect Before " + joinPoint.toShortString() + "\n");
        return joinPoint.proceed();
    }

}
