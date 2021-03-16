package base.model.abstractFactory;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoMiRouter implements RouterProduct{

    @Override
    public void startUp() {
        log.info("小米路由器开机啦");
    }

    @Override
    public void openWifi() {
        log.info("小米路由器开启WIFI");
    }

    @Override
    public void shutDown() {
        log.info("小米路由器关机啦");
    }
}
