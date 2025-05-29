/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.aop;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@Aspect
@Slf4j
public class TransactionLoggerAspect {
    private Marker transactionMarker = MarkerFactory.getMarker("TRANSACTION");
    private Marker endpointCallMarker = MarkerFactory.getMarker("ENDPOINT_CALL");

    @Around("execution(* com.epam.dalvaradoc.mod2_spring_core_task.services.*.*(..))")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        String transactionId = UUID.randomUUID().toString();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        MDC.put("txID", transactionId);

        LOGGER.info(transactionMarker, "Transaction Started - {}.{}", className, methodName);
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();

            LOGGER.info(
                    transactionMarker,
                    "Transaction Completed - {}.{} - Duration: {}ms",
                    className,
                    methodName,
                    (endTime - startTime));

            return result;
        } catch (Exception e) {
            LOGGER.error(
                    "Transaction Failed - {}.{} - Error: {}",
                    className,
                    methodName,
                    e.getMessage());
            throw e;
        } finally {
            MDC.remove("txID");
        }
    }

    @Around("execution(* com.epam.dalvaradoc.mod2_spring_core_task.controllers.*.*(..))")
    public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        String endpoint = extractEndpoint(joinPoint);
        LOGGER.info(endpointCallMarker, "Call Started - Endpoint: {}", endpoint);
        ResponseEntity<?> result = null;
        try {
            result = (ResponseEntity<?>) joinPoint.proceed();

            LOGGER.info(
                    endpointCallMarker,
                    "Call Completed - Response status: {}",
                    result.getStatusCode());

            return result;
        } catch (Exception e) {
            LOGGER.error("Call Failed - {}.{} - Error: {}", e.getMessage());
            throw e;
        }
    }

    private String extractEndpoint(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> controllerClass = joinPoint.getTarget().getClass();

        // Get base path from class level RequestMapping
        String basePath = "";
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
            if (classMapping.value().length > 0) {
                basePath = classMapping.value()[0];
            }
        }

        // Get HTTP method and path from method level annotations
        String methodPath = "";
        String httpMethod = "";

        if (signature.getMethod().isAnnotationPresent(GetMapping.class)) {
            GetMapping mapping = signature.getMethod().getAnnotation(GetMapping.class);
            httpMethod = "GET";
            methodPath = mapping.value().length > 0 ? mapping.value()[0] : "";
        } else if (signature.getMethod().isAnnotationPresent(PostMapping.class)) {
            PostMapping mapping = signature.getMethod().getAnnotation(PostMapping.class);
            httpMethod = "POST";
            methodPath = mapping.value().length > 0 ? mapping.value()[0] : "";
        } else if (signature.getMethod().isAnnotationPresent(PutMapping.class)) {
            PutMapping mapping = signature.getMethod().getAnnotation(PutMapping.class);
            httpMethod = "PUT";
            methodPath = mapping.value().length > 0 ? mapping.value()[0] : "";
        } else if (signature.getMethod().isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping mapping = signature.getMethod().getAnnotation(DeleteMapping.class);
            httpMethod = "DELETE";
            methodPath = mapping.value().length > 0 ? mapping.value()[0] : "";
        } else if (signature.getMethod().isAnnotationPresent(PatchMapping.class)) {
            PatchMapping mapping = signature.getMethod().getAnnotation(PatchMapping.class);
            httpMethod = "PATCH";
            methodPath = mapping.value().length > 0 ? mapping.value()[0] : "";
        }

        return String.format("%s %s%s", httpMethod, basePath, methodPath);
    }
}
