package nl.avans.ivh11.DemoApplication.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@SuppressWarnings("squid:S00112")//prevents SonarCube from detecting false positive "throws Throwable" in this class.
public class MyLoggingAspect {
    @Pointcut("execution(* nl.avans.ivh11.DemoApplication..*(..))") // the pointcut expression
    public void dummyMethod() { // the pointcut signature
    }

    @Before("dummyMethod()")
    public void loggingBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("(AOP-myLogger) Executing: "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "."
                + joinPoint.getSignature().getName());
    }

    @Around("dummyMethod()")
    public Object loggingAroundAdvice(ProceedingJoinPoint pjp)
            throws Throwable {
        System.out.println("(AOP-myLogger) Before execution: "
                + pjp.getSignature().getDeclaringTypeName() + "."
                + pjp.getSignature().getName());
        Object retVal = pjp.proceed();
        System.out.println("(AOP-myLogger) After execution: "
                + pjp.getSignature().getDeclaringTypeName() + "."
                + pjp.getSignature().getName());
        return retVal;
    }
}

