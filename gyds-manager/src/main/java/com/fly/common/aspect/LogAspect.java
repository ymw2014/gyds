package com.fly.common.aspect;



import com.fly.common.annotation.Log;
import com.fly.common.service.LogService;
import com.fly.domain.LogDO;
import com.fly.domain.UserDO;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.HttpContextUtils;
import com.fly.utils.IPUtils;
import com.fly.utils.JSONUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * [切点类，用于公共日志记录]
 *  {切点类，用于公共日志记录]
 * 【前置通知[Before advice]】：在连接点前面执行，前置通知不会影响连接点的执行，除非此处抛出异常。
 * 【正常返回通知[After returning advice]】：在连接点正常执行完成后执行，如果连接点抛出异常，则不会执行。
 * 【异常返回通知[After throwing advice]】：在连接点抛出异常后执行。
 * 【返回通知[After (finally) advice]】：在连接点执行完成后执行，不管是正常执行完成，还是抛出异常，都会执行返回通知中的内容。
 * 【环绕通知[Around advice]】：环绕通知围绕在连接点前后 ，比如一个方法调用的前后。这是最强大的通知类型，能在方法调用前后自定义一些操作。
 * 环绕通知还需要负责决定是继续处理joinPoint(调用ProceedingJoinPoint的proceed方法)还是中断执行。

 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    LogService logService;
    

    //AOP
    //定义切点--功能权限更新
    @Pointcut("@annotation(com.fly.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    /**
     * 保存
     * @param joinPoint
     * @param time
     * @throws InterruptedException
     */
    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONUtils.beanToJson(args[0]).substring(0, 4999);
            sysLog.setParams(params);
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        UserDO currUser = ShiroUtils.getUser();
        if (null == currUser) {
            if (null != sysLog.getParams()) {
                sysLog.setUserId(-1L);
                sysLog.setUsername(sysLog.getParams());
            } else {
                sysLog.setUserId(-1L);
                sysLog.setUsername("获取用户信息为空");
            }
        } else {
            sysLog.setUserId(ShiroUtils.getUserId());
            sysLog.setUsername(ShiroUtils.getUser().getUsername());
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        logService.save(sysLog);
    }
}
