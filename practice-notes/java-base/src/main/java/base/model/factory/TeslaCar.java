package base.model.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeslaCar implements Car{
    @Override
    public void carName() {
        log.info("这是一台特斯拉汽车");
    }
}
