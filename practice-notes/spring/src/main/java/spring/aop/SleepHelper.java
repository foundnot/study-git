package spring.aop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

@Slf4j
public class SleepHelper implements MethodBeforeAdvice,AfterReturningAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("开始记录日志 请求方法:{}", method.getName());
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.info("方法执行结束 方法名：{}===执行结果:{}", method.getName(), returnValue);
    }

}
