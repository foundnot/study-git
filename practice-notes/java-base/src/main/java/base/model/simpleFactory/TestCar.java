package base.model.simpleFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCar {

    /**
     * 每种车型对应一个工厂  新增车型 不用更改之前代码  符合闭环原则
     * */
    public static void main(String[] args) {
        Car car1 = DaZhongCarFactory.getInstance("大众");
        car1.carName();
        log.info("car1:{}", car1.getClass());

        Car car2 = TeslaCarFactory.getInstance("特斯拉");
        car2.carName();
        log.info("car2:{}", car2.getClass());

        Car car3 = TeslaCarFactory.getInstance("hah");
        car3.carName();
        log.info("car3:{}", car3.getClass());
    }

}
