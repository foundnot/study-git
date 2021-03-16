package base.model.abstractFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoMiPhone implements IphoneProduct{
    @Override
    public void startUp() {
        log.info("小米手机开机啦");
    }

    @Override
    public void sengMessage() {
        log.info("小米手机发信息");
    }

    @Override
    public void shutDown() {
        log.info("小米手机关机啦");
    }
}
