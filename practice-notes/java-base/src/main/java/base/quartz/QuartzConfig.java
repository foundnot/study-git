package base.quartz;


import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QuartzConfig {

    /**
     * https://www.cnblogs.com/kyleinjava/p/10432168.html
     * */
    public static void main(String[] args) throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler hello = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity("hello", "testGroup")
                .ofType(HelloJob.class)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startNow()
                .startAt(new Date(1598437500000L))
                .endAt(new Date(1598438820000L))
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                .build();
        hello.scheduleJob(jobDetail, trigger);
        hello.start();
        TimeUnit.MINUTES.sleep(3);
        hello.shutdown();
    }
}
