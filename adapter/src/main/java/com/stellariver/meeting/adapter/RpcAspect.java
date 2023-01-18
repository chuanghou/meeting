package com.stellariver.meeting.adapter;

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
import lombok.RequiredArgsConstructor;
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
//@Component
@CustomLog
public class RpcAspect {

    @Pointcut("execution(public com.stellariver.milky.common.base.Result com.stellariver.meeting.adapter.controller..*(..))")
    private void resultPointCut() {}

    @Pointcut("execution(public com.stellariver.milky.common.base.Result com.stellariver.meeting.adapter.controller..*(..))")
    private void pageResultPointCut() {}

    final MilkyStableSupport milkyStableSupport = null;

//    public RpcAspect(@Autowired(required = false) MilkyStableSupport milkyStableSupport) {
//        this.milkyStableSupport = milkyStableSupport;
//    }

    @Around("resultPointCut() || pageResultPointCut()")
    public Object resultResponseHandler(ProceedingJoinPoint pjp) {
        System.out.println("\n resultResponseHandler \n");
        if (milkyStableSupport != null) {
            String key = milkyStableSupport.ruleId(pjp);
            RateLimiterWrapper rateLimiterWrapper = milkyStableSupport.rateLimiter(key);
            if (rateLimiterWrapper != null) {
                rateLimiterWrapper.acquire();
            }
        }
        Object result = null;
        Object[] args = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Class<?> returnType = method.getReturnType();
        long start = Clock.currentTimeMillis();
        List<ErrorEnum> errorEnums = Collections.emptyList();
        Throwable t = null;
        try {
            ValidConfig annotation = method.getAnnotation(ValidConfig.class);
            Class<?>[] groups = Kit.op(annotation).map(ValidConfig::groups).orElse(new Class<?>[0]);
            boolean failFast = Kit.op(annotation).map(ValidConfig::failFast).orElse(true);
            ValidateUtil.bizValidate(pjp.getTarget(), method, args, failFast, groups);
            result = pjp.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof BaseException) {
                errorEnums = ((BaseException) throwable).getErrors();
            } else {
                ErrorEnum errorEnum = ErrorEnums.SYSTEM_EXCEPTION.message(throwable.getMessage());
                errorEnums = Collect.asList(errorEnum);
            }
            t = throwable;
        } finally {
            if (t != null && returnType == Result.class) {
                result = Result.error(errorEnums);
            } else if (t != null && returnType == PageResult.class) {
                result = PageResult.pageError(errorEnums);
            }
            IntStream.range(0, args.length).forEach(i -> log.with("arg" + i, args[i]));
            String logTag = ((MethodSignature) pjp.getSignature()).getMethod().getName();
            log.result(result).source("rpc's source").cost(Clock.currentTimeMillis() - start).log(logTag, t);
        }
        return result;
    }

}