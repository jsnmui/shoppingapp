package com.superdupermart.shopping_app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceLoggingAspect.class);


    @Pointcut("execution(* com.superdupermart.shopping_app.service..*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("[AOP] {}#{} executed in {} ms",
                    joinPoint.getSignature().getDeclaringType().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    duration);
        }
    }
}
