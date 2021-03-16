package base.model.abstractFactory;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuaWeiRouter implements RouterProduct{

    @Override
    public void startUp() {
        log.info("华为路由器开机啦");
    }

    @Override
    public void openWifi() {
        log.info("华为路由器开启WIFI");
    }

    @Override
    public void shutDown() {
        log.info("华为路由器关机啦");
    }
}
