package base.proxy.userBean;



import base.proxy.UserBeanProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class ProxyDemo {

    @Test
    public void userBeanProxy(){
        UserBean targetObject = new UserBeanImpl("蕾蕾","1");
        UserBeanProxy proxy = new UserBeanProxy(targetObject);
        //生成代理对象
        UserBean object = (UserBean) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), proxy);

        String user = object.getUser();
        log.info("user:{}", user);
        String userName = object.toString();
        log.info("userName:{}", userName);
    }

}
