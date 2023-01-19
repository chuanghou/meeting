package com.stellariver.meeting.aspect;

import lombok.CustomLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author houchuang
 */
@Aspect
@Component
@CustomLog
public class SpringAspect {
    @Pointcut("execution(* com.stellariver.meeting.application.UserAbility.internalInvoke())")
    private void pointCutInternal() {}

    @Around("pointCutInternal()")
    public Object testInternal(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("\n Spring Aspect pointCutInternal " + joinPoint.toShortString() + "\n");
        return joinPoint.proceed();
    }

    @Pointcut("execution(* com.stellariver.meeting.application.UserAbility.invoke())")
    private void pointCutOut() {}

    @Around("pointCutOut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("\n Spring Aspect pointCutOut " + joinPoint.toShortString() + "\n");
        return joinPoint.proceed();
    }

}
