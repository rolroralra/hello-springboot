package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
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
    // Aspect is using in Service, Repository.
    // Interceptor is using in Controller.
    // ControllerAdvice, ExceptionHandler is using for handling Exception.

    @Around("execution(* com.example.demo.service.*Service.*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();
        Object result = null;

        try {
            log.info(">>>>>>>>>>>> Service Started >>>>>>>>>>>>");
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

            log.info("[{}] {}.{}{} running",
                    kind, targetClassName, targetMethodName, arguments
            );
            log.info("Service Time spent : {} ms", (end - start) / 1000 / 1000);
            log.info("Result : {}", result);
            log.info("<<<<<<<<<<<< Service Completed <<<<<<<<<<<<");
        }
    }

    // Before, FIFO
    @Before("execution(* com.example.demo..*.*(..))")
    public void logger(JoinPoint joinPoint) {
        log.info("Method Called -- {}", joinPoint.getSignature().toShortString());
    }

//    @Before("execution(* com.example.demo..*.*(..))")
//    public void logger2(JoinPoint joinPoint) {
//        log.info("Method Called2 -- {}", joinPoint.getSignature().toShortString());
//    }


    // After, LIFO
    @After("execution(* com.example.demo..*.*(..))")
    public void logger3(JoinPoint joinPoint) {
        log.info("Method Completed -- {}", joinPoint.getSignature().toShortString());
    }

//    @After("execution(* com.example.demo..*.*(..))")
//    public void logger4(JoinPoint joinPoint) {
//        log.info("Method Called4 -- {}", joinPoint.getSignature().toShortString());
//    }
}
