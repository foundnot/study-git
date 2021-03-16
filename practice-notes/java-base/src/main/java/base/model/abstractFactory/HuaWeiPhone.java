package base.model.abstractFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuaWeiPhone implements IphoneProduct{
    @Override
    public void startUp() {
        log.info("华为手机开机啦");
    }

    @Override
    public void sengMessage() {
        log.info("华为手机发信息");
    }

    @Override
    public void shutDown() {
        log.info("华为手机关机啦");
    }
}
