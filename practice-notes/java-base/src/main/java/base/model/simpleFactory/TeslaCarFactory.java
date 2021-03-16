package base.model.simpleFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeslaCarFactory {

    // 创建某种Car对象的工厂  23种单例模式 简单工厂模式
    public static Car getInstance(String name){
        return new TeslaCar();
    }

}
