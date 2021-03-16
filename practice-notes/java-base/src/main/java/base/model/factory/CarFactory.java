package base.model.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarFactory {

    public static Car getInstance(String name){
        if("大众".equals(name)){
            return new DaZhongCar();
        }else if("特斯拉".equals(name)){
            return new TeslaCar();
        }else{
            return () -> log.info("这是一台摩托车");
        }
    }

}
