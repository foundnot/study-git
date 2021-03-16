package base.model.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCar {

    public static void main(String[] args) {

        /**
         * 统一创建Car对象的工厂  23种单例模式 一般工厂模式
         * 优点：简单方便
         * 缺点：如果需要增加车型  需要改变之前的代码  不符合闭环原则
         * */
        Car car1 = CarFactory.getInstance("大众");
        car1.carName();
        log.info("car1:{}", car1.getClass());

        Car car2 = CarFactory.getInstance("特斯拉");
        car2.carName();
        log.info("car2:{}", car2.getClass());

        Car car3 = CarFactory.getInstance("hah");
        car3.carName();
        log.info("car3:{}", car3.getClass());
    }

}
