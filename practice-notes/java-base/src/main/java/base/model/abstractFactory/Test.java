package base.model.abstractFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

    public static void main(String[] args) {
        RouterProduct routerProduct = new HuaWeiFactory().productRouter();
        routerProduct.openWifi();
        routerProduct.shutDown();
        IphoneProduct iphoneProduct = new XiaoMiFactory().productIphone();
        iphoneProduct.startUp();
        iphoneProduct.sengMessage();
        log.info("==================================================");
        iphoneProduct = new HuaWeiFactory().productIphone();
        iphoneProduct.startUp();
        iphoneProduct.sengMessage();
    }


}
