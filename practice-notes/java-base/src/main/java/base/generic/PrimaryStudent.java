package base.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrimaryStudent extends Student {

    @Override
    public String attendClass(String place, int duration) {
        log.info("小学生开始上课啦");
        return super.attendClass(place, duration);
    }
}
