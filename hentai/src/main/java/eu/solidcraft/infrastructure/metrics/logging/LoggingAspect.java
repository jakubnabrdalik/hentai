package eu.solidcraft.infrastructure.metrics.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import static java.lang.String.format;

@Aspect
class LoggingAspect {

    @Pointcut("target(org.springframework.data.repository.Repository)")
    void allRepositories() {}

    @Pointcut("@within(eu.solidcraft.infrastructure.metrics.logging.Log)")
    void logAnnotation() {}

    @Around("allRepositories() || logAnnotation()")
    Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        log(joinPoint, stopWatch, result);
        return result;
    }

    private void log(ProceedingJoinPoint joinPoint, StopWatch stopWatch, Object result) {
        String operationName = getOperationName(joinPoint);
        String timerString = createTimerString(stopWatch);
        String resultString = createResultString(result);
        if (!timerString.isEmpty() || !resultString.isEmpty()) {
            Logger logger = getLogger(joinPoint);
            logger.info("{}{} {}", operationName, timerString, resultString);
        }
    }

    private Logger getLogger(ProceedingJoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
    }

    private String createResultString(Object result) {
        return format("operation result: %s", result);
    }

    private String createTimerString(StopWatch stopWatch) {
        long millis = stopWatch.getTotalTimeMillis();
        return format(" [%d ms]", millis);
    }

    private String getOperationName(ProceedingJoinPoint joinPoint) {
        String classWithPackageName = joinPoint.getSignature().getDeclaringTypeName();
        String className = classWithPackageName.substring(classWithPackageName.lastIndexOf('.') + 1);
        return className + "." + joinPoint.getSignature().getName();
    }
}

