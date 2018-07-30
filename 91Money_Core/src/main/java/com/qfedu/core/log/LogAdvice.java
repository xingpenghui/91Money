package com.qfedu.core.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@Author feri
 *@Date Created in 2018/7/27 10:27
 */
public class LogAdvice {
    Logger logger=LoggerFactory.getLogger(LogAdvice.class);
    public Object weriteLog(ProceedingJoinPoint joinPoint){
        Object obj= null;
        try {
            obj = joinPoint.proceed();
            logger.info(joinPoint.getSignature().getName()+" 执行成功");
        } catch (Throwable throwable) {
            logger.error(joinPoint.getSignature().getName()+" 执行失败："+throwable.getMessage());
            throwable.printStackTrace();
        }
        return obj;
    }
}
