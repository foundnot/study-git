package base.proxy.hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object obj){
        this.target = obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("请求：" + proxy.getClass().getName() + "类的" + method.getName() + "方法");
        Object invoke = method.invoke(target, args);
        System.out.println("结束记录日志，方法结束执行...");
        return invoke;
    }
}
