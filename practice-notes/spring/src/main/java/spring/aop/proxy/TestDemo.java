package spring.aop.proxy;

public class TestDemo {

    public static void main(String[] args) {
        // 静态代理
//        CarStaticProxy carStaticProxy = new CarStaticProxy(new CarImpl());
//        carStaticProxy.proxy();

        // 动态代理
//        Car car = new TeslaCarImpl();
        Car car = new BaoMaCarImpl();
        CarDynamicProxy carDynamicProxy = new CarDynamicProxy(car);
        Car proxy = (Car) carDynamicProxy.proxy();
        proxy.add();
    }

}
