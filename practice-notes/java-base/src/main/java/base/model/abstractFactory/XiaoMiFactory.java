package base.model.abstractFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoMiFactory implements ProductFactory{

    @Override
    public IphoneProduct productIphone() {
        return new XiaoMiPhone();
    }

    @Override
    public RouterProduct productRouter() {
        return new XiaoMiRouter();
    }
}
