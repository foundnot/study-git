package base.proxy.hello;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Slf4j
public class DynamicProxy {

    public static void main(String[] args) {

        InvocationHandler handler = (proxy, method, args1) -> {
            // 代理类的内部操作
            log.info("method:{}", method.getName());
            if("name".equals(method.getName())){
                log.info("request name method");
            }else if ("setWorld".equals(method.getName())){
                log.info("request setWorld method");
            }else{
                log.info("request ===");
            }
            log.info("params:{}", Arrays.toString(args1));
            return null;
        };

        Class<Hello> helloClass = Hello.class;
        Object obj = Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[]{helloClass, World.class},
                handler); // 传入处理调用方法的InvocationHandler

        // 代理对象
        Hello hello = (Hello) obj;
        // 使用代理对象调用方法
        hello.name("hah");

        World world = (World)obj;

//        World world = (World)Proxy.newProxyInstance(
//                Hello.class.getClassLoader(), // 传入ClassLoader
//                new Class[]{Hello.class, World.class}, // 传入要实现的接口
//                handler);  // 传入处理调用方法的InvocationHandler

        world.setWorld();
        world.setWorld1("hha", 12, "01-01");

    }

    @Test
    public void proxy() throws Exception{
        this.helloProxy();
//        this.helloProxy2();
    }

    // 方式一生成代理
    private void helloProxy(){

        InvocationHandler handler = (proxy, method, args) -> {
            log.info("method:{}", method.getName());
            if("name".equals(method.getName())){
                log.info("request name method");
            }else{
                log.info("request getName method");
            }
            return null;
        };

        Hello hello = (Hello) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), // 传入ClassLoader
                new Class[] { Hello.class }, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler

        hello.getName();
    }

    // 方式二生成代理
    private void helloProxy2() throws Exception {
        // 编写Invocationhandler 实现类对象
        InvocationHandler invocationHandler = new MyInvocationHandler(new HelloWorld());
        // 获取需要代理的接口
        Class<?> proxyClass = Proxy.getProxyClass(this.getClass().getClassLoader(), Hello.class);
        // 通过代理类生成对象
        Hello hello = (Hello) proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        hello.name("lalaa");
        String name = hello.getName();
        log.info("name:{}", name);


    }

    // 生成代理类class文件
    @Test
    public void testss(){
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", World.class.getInterfaces());
        String path = "WorldProxy.class";
        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }
    }

}
