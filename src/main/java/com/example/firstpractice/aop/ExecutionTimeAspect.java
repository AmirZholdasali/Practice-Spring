package com.example.firstpractice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect @Component
public class ExecutionTimeAspect {
    private static final Logger log = LoggerFactory.getLogger("timing");

    @Around("execution(* com.example.firstpractice.service..*(..))")
    Object time(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        try {
            return pjp.proceed();
        }
        catch (Throwable ex){
            log.warn("{} failed with {}",
                    pjp.getSignature().toShortString(),
                    ex.getClass().getSimpleName());
            throw ex;
        }
        finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.info("{} took {} ms", pjp.getSignature().toShortString(), ms);
        }

    }

}
