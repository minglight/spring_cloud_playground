package org.ming.schedule.pure.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ScheduleAsyncApplication {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleAsyncApplication.class);

    public static void main(String[] args) {
        //disable web to avoid conflict
        SpringApplication springApp = new SpringApplication(ScheduleAsyncApplication.class);
        springApp.setWebApplicationType(WebApplicationType.NONE);
        springApp.run(args);
    }

    /***
     * Lesson 2 :
     * Check the log is printed every second
     *
     * Log :
     * 2019-09-27 17:43:55.571  INFO 84447 --- [         task-1] o.m.s.async.ScheduleAsyncApplication     : @Async should be printed every second
     * 2019-09-27 17:43:56.554  INFO 84447 --- [         task-2] o.m.s.async.ScheduleAsyncApplication     : @Async should be printed every second
     * 2019-09-27 17:43:57.550  INFO 84447 --- [         task-3] o.m.s.async.ScheduleAsyncApplication     : @Async should be printed every second
     * 2019-09-27 17:43:58.547  INFO 84447 --- [         task-4] o.m.s.async.ScheduleAsyncApplication     : @Async should be printed every second
     * 2019-09-27 17:43:59.548  INFO 84447 --- [         task-5] o.m.s.async.ScheduleAsyncApplication     : @Async should be printed every second
     * 2019-09-27 17:44:00.547  INFO 84447 --- [         task-6] o.m.s.async.ScheduleAsyncApplication     : @Async should be printed every second
     *
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void printLogPerSecond() throws InterruptedException {
        logger.info("@Async should be printed every second");
        Thread.sleep(3000);

    }

    /***
     * Lesson 3 :
     * @Async will use default thread pool, and it has limitation (default use cores * 2, and shared with other Java Async Job (include Lambda)  )
     *
     * Log :
     * 2019-09-27 17:46:31.452  INFO 84494 --- [         task-2] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:32.432  INFO 84494 --- [         task-4] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:33.432  INFO 84494 --- [         task-6] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:34.435  INFO 84494 --- [         task-8] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:35.435  INFO 84494 --- [         task-3] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:37.439  INFO 84494 --- [         task-7] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:39.439  INFO 84494 --- [         task-5] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:46:44.442  INFO 84494 --- [         task-1] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:47:01.456  INFO 84494 --- [         task-2] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- end
     * 2019-09-27 17:47:02.437  INFO 84494 --- [         task-4] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- end
     * 2019-09-27 17:47:02.438  INFO 84494 --- [         task-4] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- start
     * 2019-09-27 17:47:03.437  INFO 84494 --- [         task-6] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- end
     * 2019-09-27 17:47:04.440  INFO 84494 --- [         task-8] o.m.s.async.ScheduleAsyncApplication     : @Async sleep 30 seconds -- end
     *
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void printLogSleep30Seconds() throws InterruptedException {
        logger.info("@Async sleep 30 seconds -- start ");
        Thread.sleep(30000);
        logger.info("@Async sleep 30 seconds -- end ");
    }

}
