package base.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = dateTimeFormatter.format(LocalDateTime.now());
        log.info("hello，当前时间:{}", time);

    }
}
