package spring.aop.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class CarDynamicProxy implements InvocationHandler {

    private final Object target;

    public CarDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("before===增加日志");
        Object invoke = method.invoke(target, args);
        log.info("after==增加日志");
        return invoke;
    }

    public Object proxy(){
//        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Car.class}, this);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }


}
