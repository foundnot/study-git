package spring.aop.proxy;

public class CarStaticProxy {

    private final Car target;

    public CarStaticProxy(Car target){
        this.target = target;
    }

    public void proxy(){
        System.out.println("before--增加日志");
        target.add();
        System.out.println("after--增加日志");
    }

}
