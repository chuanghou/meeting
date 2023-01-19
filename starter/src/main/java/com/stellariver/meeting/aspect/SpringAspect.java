package com.stellariver.meeting.aspect;

import com.stellariver.milky.common.base.ErrorEnum;
import com.stellariver.milky.common.base.PageResult;
import com.stellariver.milky.common.base.Result;
import com.stellariver.milky.common.tool.common.Clock;
import com.stellariver.milky.common.tool.common.Kit;
import com.stellariver.milky.common.tool.exception.BaseException;
import com.stellariver.milky.common.tool.stable.MilkyStableSupport;
import com.stellariver.milky.common.tool.stable.RateLimiterWrapper;
import com.stellariver.milky.common.tool.util.Collect;
import com.stellariver.milky.common.tool.validate.ValidConfig;
import com.stellariver.milky.common.tool.validate.ValidateUtil;
import com.stellariver.milky.domain.support.ErrorEnums;
import lombok.CustomLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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
