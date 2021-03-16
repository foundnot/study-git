package base.proxy;



import base.proxy.userBean.UserBeanImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserBeanProxy implements InvocationHandler {


    private final Object targetObject;

    public UserBeanProxy(Object targetObject)
    {
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        UserBeanImpl userBean = (UserBeanImpl) targetObject;
        String flag = userBean.getFlag();
        Object result = null;
        System.out.println("method==" + method.getName());
        //权限判断
        if("1".equals(flag) ){
            result = method.invoke(targetObject, args);
            System.out.println(" You have permission");
        }else{
            System.out.println("sorry , You don't have permission");
        }
        return result;
    }
}
