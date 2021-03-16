package spring.aop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Student implements SleepAble{

    @Override
    public void sleep(){
        log.info("学生要按时睡觉");
    }

}
