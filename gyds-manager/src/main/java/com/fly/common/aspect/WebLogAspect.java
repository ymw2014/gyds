package com.fly.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 控制器类日志
 * 声明切面，标记类
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 定义切点
     */
    @Pointcut("execution( * com.palmble..controller.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {
    }


    /**
     * 切点之前执行
     * @param joinPoint
     * @throws Throwable
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("请求地址 : " + request.getRequestURL().toString());
        logger.info("HTTP METHOD : " + request.getMethod());
        // 获取真实的ip地址
        //logger.info("IP : " + IPAddressUtil.getClientIpAddress(request));
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
//        loggger.info("参数 : " + joinPoint.getArgs());

    }

    /**
     * 切点执行之后执行，无论执行成功与否
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
        logger.debug("返回值 : " + ret);
    }

    /**
     * 日志打印
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        logger.info("耗时 : " + (System.currentTimeMillis() - startTime));
        return ob;
    }
}
