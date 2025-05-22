package com.epam.dalvaradoc.mod2_spring_core_task.aop;

import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.data.repository.util.TxUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

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

      LOGGER.info(transactionMarker, "Transaction Completed - {}.{} - Duration: {}ms",
          className, methodName, (endTime - startTime));

      return result;
    } catch (Exception e) {
      LOGGER.error("Transaction Failed - {}.{} - Error: {}",
          className, methodName, e.getMessage());
      throw e;
    } finally {
      MDC.remove("txID");
    }
  }

  @Around("execution(* com.epam.dalvaradoc.mod2_spring_core_task.controllers.*.*(..))")
  public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();
    String className = joinPoint.getTarget().getClass().getSimpleName();

    LOGGER.info(endpointCallMarker, "Call Started - {}.{}", className, methodName);
    ResponseEntity<?> result = null;
    try {
      result = (ResponseEntity<?>) joinPoint.proceed();

      LOGGER.info(endpointCallMarker, "Call Completed - {}.{} - Response status: {}",
          className, methodName, result.getStatusCode());

      return result;
    } catch (Exception e) {
      LOGGER.error("Call Failed - {}.{} - Error: {}",
          className, methodName, e.getMessage());
      throw e;
    }
  }
}
