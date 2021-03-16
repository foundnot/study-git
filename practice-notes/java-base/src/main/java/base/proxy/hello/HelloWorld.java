package base.proxy.hello;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;

@Slf4j
public class HelloWorld implements Hello {

    @Override
    public void name(String name) {
        log.info("method name");
    }

    @Override
    public String getName() {
        log.info("method getName");
        return "世界美好和你环环相绕";
    }

//    InvocationHandler handler;
//
//    @SneakyThrows
//    @Override
//    public void name(String name) {
//        Hello h = (Hello)handler.invoke(this,
//                Hello.class.getMethod("name", String.class),
//                new Object[] { name });
//        h.name("xxx");
//        log.info("method name");
//    }
//
//    @SneakyThrows
//    @Override
//    public String getName() {
//        handler.invoke(this,
//                Hello.class.getMethod("getName"),
//                new Object[] {});
//        log.info("method getName");
//        return "oo";
//    }
}
