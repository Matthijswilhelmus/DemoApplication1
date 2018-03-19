package nl.avans.ivh11.DemoApplication.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyExecutionTimeAspect {
    @Pointcut("@annotation(nl.avans.ivh11.DemoApplication.logging.MyExecutionTime) && execution(* nl.avans.ivh11.DemoApplication..*(..))") // the pointcut expression
    public void myExecutionTimeAnnotation() { // the pointcut signature
    }

    @Around("myExecutionTimeAnnotation()")
    public Object myExecutionTimeAdvice(
            ProceedingJoinPoint joinPoint /*, MyExecutionTime annotation */) throws Throwable {
        long startMillis = System.currentTimeMillis();
        System.out.println("(AOP-myExecTime) Starting timing method " + joinPoint.getSignature());
        Object retVal = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startMillis;
        System.out.println("(AOP-myExecTime) Call to " + joinPoint.getSignature() + " took " + duration + " ms");
        return retVal;

    }
}

