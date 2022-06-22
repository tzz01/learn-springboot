package aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FlushLoginAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @After("@within(flushLogin)")
    public void flushLogin(JoinPoint joinPoint, FlushLogin flushLogin) {
        System.out.println("刷新登录时间了");
        Object target = joinPoint.getTarget();
        logger.debug("谁访问了{}，刷新登录时间", target.getClass().getName());
        System.out.println(target.getClass().getName());
    }
}
