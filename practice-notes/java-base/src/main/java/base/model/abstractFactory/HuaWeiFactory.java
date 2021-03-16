package base.model.abstractFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuaWeiFactory implements ProductFactory{

    @Override
    public IphoneProduct productIphone() {
       return new HuaWeiPhone();
    }

    @Override
    public RouterProduct productRouter() {
        return new HuaWeiRouter();
    }
}
