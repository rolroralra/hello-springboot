package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("execution(* com.example.demo.controller.*Controller.*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();
        Object result = null;

        try {
            log.info(">>>>>>>>>>>> Request Started >>>>>>>>>>>>");
            result = joinPoint.proceed();
            return result;
        } finally {
            long end = System.nanoTime();
            String kind = joinPoint.getKind();
            // String targetClassName = joinPoint.getTarget().getClass().getName();
            String targetClassName = joinPoint.getSignature().getDeclaringTypeName();
            String targetMethodName = joinPoint.getSignature().getName();
            String arguments =
                    Arrays.stream(joinPoint.getArgs())
                            .filter(Objects::nonNull)
                            .map(Object::toString)
                            .collect(Collectors.joining(", ", "(", ")"));

//            log.info("[{}] {}.{}{} running time is {} ms",
//                    kind, targetClassName, targetMethodName, arguments,
//                    (end - start) / 1000 / 1000
//            );
            log.info("Time spent : {} ms", (end - start) / 1000 / 1000);
            log.info("Result : {}", result);
            log.info("<<<<<<<<<<<< Request Completed <<<<<<<<<<<<");
        }
    }

    @Before("execution(* com.example.demo..*.*(..))")
    public void logger(JoinPoint joinPoint) {
        log.info("Method Called -- {}", joinPoint.getSignature().toShortString());
    }
}
