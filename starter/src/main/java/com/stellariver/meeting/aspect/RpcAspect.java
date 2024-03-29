//package com.stellariver.meeting.aspect;
//
//import com.stellariver.basic.AspectJLog;
//import com.stellariver.milky.common.base.ErrorEnum;
//import com.stellariver.milky.common.base.ExceptionType;
//import com.stellariver.milky.common.base.PageResult;
//import com.stellariver.milky.common.base.Result;
//import com.stellariver.milky.common.tool.common.Clock;
//import com.stellariver.milky.common.tool.exception.BaseException;
//import com.stellariver.milky.common.tool.exception.BizException;
//import com.stellariver.milky.common.tool.stable.MilkyStableSupport;
//import com.stellariver.milky.common.tool.stable.RateLimiterWrapper;
//import com.stellariver.milky.common.tool.util.Collect;
//import com.stellariver.milky.domain.support.ErrorEnums;
//import com.stellariver.milky.validate.tool.Validate;
//import com.stellariver.milky.validate.tool.ValidateUtil;
//import lombok.CustomLog;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.IntStream;
//
///**
// * @author houchuang
// */
//@Aspect
//@Component
//@CustomLog
//public class RpcAspect {
//
//    @Pointcut("execution(public com.stellariver.milky.common.base.Result com.stellariver.meeting.adapter.controller..*(..))")
//    private void resultPointCut() {}
//
//    @Pointcut("execution(public com.stellariver.milky.common.base.Result com.stellariver.meeting.adapter.controller..*(..))")
//    private void pageResultPointCut() {}
//
//    final MilkyStableSupport milkyStableSupport;
//
//    public RpcAspect(@Autowired(required = false) MilkyStableSupport milkyStableSupport) {
//        this.milkyStableSupport = milkyStableSupport;
//    }
//
//    @Around("resultPointCut() || pageResultPointCut()")
//    public Object resultResponseHandler(ProceedingJoinPoint pjp) {
//        if (milkyStableSupport != null) {
//            String key = milkyStableSupport.ruleId(pjp);
//            RateLimiterWrapper rateLimiterWrapper = milkyStableSupport.rateLimiter(key);
//            if (rateLimiterWrapper != null) {
//                rateLimiterWrapper.acquire();
//            }
//        }
//        Object result = null;
//        Object[] args = pjp.getArgs();
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        Class<?> returnType = method.getReturnType();
//        long start = Clock.currentTimeMillis();
//        List<ErrorEnum> errorEnums = Collections.emptyList();
//        Throwable t = null;
//        try {
//            Validate validate = method.getAnnotation(Validate.class);
//            AspectJLog aspectJLog = method.getAnnotation(AspectJLog.class);
//            boolean notNecessary = validate != null || aspectJLog != null;
//            if (notNecessary) {
//                log.arg0(method.getDeclaringClass().getName()).arg1(method.getName()).warn("NOT_NECESSARY");
//            }
//            result = pjp.proceed();
//        } catch (Throwable throwable) {
//            if (throwable instanceof BaseException) {
//                errorEnums = ((BaseException) throwable).getErrors();
//            } else {
//                ErrorEnum errorEnum = ErrorEnums.SYSTEM_EXCEPTION.message(throwable.getMessage());
//                errorEnums = Collect.asList(errorEnum);
//            }
//            t = throwable;
//        } finally {
//            ExceptionType exceptionType = t instanceof BizException ? ExceptionType.BIZ : ExceptionType.SYS;
//            if (t != null && returnType == Result.class) {
//                result = Result.error(errorEnums, exceptionType);
//            } else if (t != null && returnType == PageResult.class) {
//                result = PageResult.pageError(errorEnums, exceptionType);
//            }
//            IntStream.range(0, args.length).forEach(i -> log.with("arg" + i, args[i]));
//            String logTag = ((MethodSignature) pjp.getSignature()).getMethod().getName();
//            log.result(result).source("rpc's source").cost(Clock.currentTimeMillis() - start).log(logTag, t);
//        }
//        return result;
//    }
//
//}
