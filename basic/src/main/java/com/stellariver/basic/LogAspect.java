package com.stellariver.basic;

import com.stellariver.milky.common.tool.common.Clock;
import com.stellariver.milky.common.tool.exception.ErrorEnumsBase;
import com.stellariver.milky.common.tool.exception.SysException;
import com.stellariver.milky.common.tool.log.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.stream.IntStream;

@Aspect
public class LogAspect {

    private static final Logger log = Logger.getLogger(LogAspect.class);

    @Pointcut("execution(@com.stellariver.basic.AspectJLog * *(..))")
    private void pointCut() {}

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        SysException.trueThrowGet(args.length > 5,
                () -> ErrorEnumsBase.PARAM_FORMAT_WRONG.message("参数个数不能大于5个！"));
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        AspectJLog annotation = method.getAnnotation(AspectJLog.class);
        Throwable backUp = null;
        Object result = null;
        long start = Clock.currentTimeMillis();
        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable) {
            backUp = throwable;
            throw throwable;
        } finally {
            IntStream.range(0, args.length).forEach(i -> log.with("arg" + i, args[i]));
            log.result(result).cost(Clock.currentTimeMillis() - start).log(annotation.value(), backUp);
        }
    }

}
