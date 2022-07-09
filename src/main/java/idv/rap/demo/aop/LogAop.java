package idv.rap.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @author raphael.wong
 * @since 28 June 2022
 */
@Slf4j
@Aspect
@Component
public class LogAop {

    /**
     * execution(方法修饰符(可选) 返回类型 类路径 方法名 参数 异常模式(可选))
     * <p>
     * 在execution中，* 表示全部， ()匹配没有参数； (. .)代表任意多个参数； (*)代表一个参数，但可以是任意型； ( *,String)代表第一个参数为任何值,第二个为String类型。
     */

    @Pointcut("execution(* idv.rap.demo.api.*.*(..))")
    public void controlLog() {
    }

    @Pointcut("execution(* idv.rap.demo.service..*.*(..))")
    public void testLog() {
    }

    @Before("controlLog() || testLog()")
    public void logBefore(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getDeclaringType() + "request time=[{}]", new Date());
        Signature signature = joinPoint.getSignature();

        // e.g. com.example.demo.aop.Interceptor
        String name = joinPoint.getSignature().getName();

        // e.g. class
        Class type = joinPoint.getSignature().getDeclaringType();


    }

    @Around("controlLog() || testLog()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("aop - around: start current time=[{}]", new Date());
        log.info("aop - around: signature=[{}]", joinPoint.getSignature());
        log.info("aop - around: kind=[{}]", joinPoint.getKind());
        log.info("aop - around: sourceLocation=[{}]", joinPoint.getSourceLocation());
        Arrays.stream(joinPoint.getArgs()).forEach(object -> {
            log.info("arg=[{}]", object.toString());
        });
        log.info("aop - around: target=[{}]", joinPoint.getTarget());
        log.info("aop - around: this=[{}]", joinPoint.getThis());
        log.info("aop - around: class=[{}]", joinPoint.getClass());
        log.info("aop - around: staticPart=[{}]", joinPoint.getStaticPart());
        joinPoint.proceed();
        log.info("aop - around: end current time=[{}]", new Date());
    }

}
