package com.torchai.service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;



@Slf4j
@Aspect
@Component
public class AspectLayer {
    // Keys for MDC
    private static final String API = "serviceApi";
    private static final String LAYER = "serviceLayer";
    private static final String SERVICE_POINT = "servicePoint";
    private static final String ARGS = "args";
    private static final String RESULTS = "results";
    private static final String ELAPSED_TIME = "millisecondsElapsed";

    @Pointcut("@annotation(com.torchai.service.aspect.annotations.AspectLog) "
            + "|| @target(com.torchai.service.aspect.annotations.AspectLog)")
    public void methodOrClassLoggingEnabled() {
    }

    @Pointcut("!@annotation(com.torchai.service.aspect.annotations.AspectNoLog)")
    public void methodLoggingNotDisabled() {
    }

    @Pointcut("methodOrClassLoggingEnabled() && methodLoggingNotDisabled()")
    public void loggingEnabledAndNotDisabled() {
    }

    @Pointcut("within(org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController)")
    public void springStuff() {
    }

    /**
     * Any public methods in classes which are specifically annotated as a Controller
     */
    @Pointcut("(within(@org.springframework.web.bind.annotation.RestController *) ||"
            + "within(@org.springframework.stereotype.Controller *) ) &&"
            + "!springStuff()")
    public void allPublicControllerMethods() {
    }

    /**
     * Any public methods in classes which are specifically annotated as a Service
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void allPublicServiceMethods() {
    }

    // Controller classes are enabled by default, so don't need to be specifically enabled.
    @Pointcut("allPublicControllerMethods() "
            + "&& methodLoggingNotDisabled()")
    public void allPublicControllerMethodsNotDisabled() {
    }

    // Service classes are enabled by default, so don't need to be specifically enabled.
    @Pointcut("allPublicServiceMethods() "
            + "&& methodLoggingNotDisabled()")
    public void allPublicServiceMethodsNotDisabled() {
    }

    // Components which aren't Service classes, but in the service package need to be enabled with @AspectLog
    @Pointcut("execution(public * com.torchai.service..service..*(..)) "
            + "&& loggingEnabledAndNotDisabled()")
    public void allPublicServiceLayerMethodsNotDisabled() {
    }

    // Components which aren't Controller classes, but in the controller package need to be enabled with @AspectLog
    @Pointcut("execution(public * com.torchai.service..controller..*(..)) "
            + "&& loggingEnabledAndNotDisabled()")
    public void allPublicControllerLayerMethodsNotDisabled() {
    }

    @Around("allPublicControllerMethodsNotDisabled() "
            + "|| allPublicControllerLayerMethodsNotDisabled()")
    public Object logController(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return log(proceedingJoinPoint, "Controller");
    }

    @Around("allPublicServiceMethodsNotDisabled() "
            + "|| allPublicServiceLayerMethodsNotDisabled()")
    public Object logService(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return log(proceedingJoinPoint, "Service");
    }

    // Components which aren't in the Controller or Service layer, but have logging turned on
    @Around("execution(public * com.torchai..*.*(..)) "
            + "&& loggingEnabledAndNotDisabled()"
            + "&& !allPublicControllerMethodsNotDisabled()"
            + "&& !allPublicControllerLayerMethodsNotDisabled()"
            + "&& !allPublicServiceMethodsNotDisabled()"
            + "&& !allPublicServiceLayerMethodsNotDisabled()")
    public Object allPublicMethodsNotDisabledAndNotControllerOrService(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return log(proceedingJoinPoint, "Other");
    }

    public Object log(final ProceedingJoinPoint proceedingJoinPoint, final String layer) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        System.out.println("*** Logging: " + methodSignature);

        return proceedingJoinPoint.proceed();

    }

    @AfterThrowing(
            pointcut = "allPublicControllerMethodsNotDisabled() "
                    + "|| allPublicControllerLayerMethodsNotDisabled()",
            throwing = "t")
    public void logController(final JoinPoint joinPoint, final Throwable t) {
        onException(joinPoint, t, "Controller");
    }

    @AfterThrowing(
            pointcut = "allPublicServiceMethodsNotDisabled() "
                    + "|| allPublicServiceLayerMethodsNotDisabled()",
            throwing = "t")
    public void logService(final JoinPoint joinPoint, final Throwable t) {
        onException(joinPoint, t, "Service");
    }

    // Components which aren't in the Controller or Service layer, but have logging turned on
    @AfterThrowing(
            pointcut = "execution(public * com.torchai..*.*(..)) "
                    + "&& loggingEnabledAndNotDisabled()"
                    + "&& !allPublicControllerMethodsNotDisabled()"
                    + "&& !allPublicControllerLayerMethodsNotDisabled()"
                    + "&& !allPublicServiceMethodsNotDisabled()"
                    + "&& !allPublicServiceLayerMethodsNotDisabled()",
            throwing = "t")
    public void allPublicMethodsNotDisabledAndNotControllerOrService(final JoinPoint joinPoint, final Throwable t) {
        onException(joinPoint, t, "Other");
    }

    /**
     * Logs any exception thrown by method. This aspect is executed <b>AFTER</b> the exception has been thrown,
     * so one cannot swallow it over here.
     */
    public void onException(final JoinPoint joinPoint, final Throwable t, final String layer) {
        System.out.println("*** onException");
    }
}
