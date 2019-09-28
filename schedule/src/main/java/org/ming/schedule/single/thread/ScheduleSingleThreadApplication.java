package org.ming.schedule.single.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class ScheduleSingleThreadApplication {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleSingleThreadApplication.class);

    public static void main(String[] args) {
        //disable web to avoid conflict
        SpringApplication springApp = new SpringApplication(ScheduleSingleThreadApplication.class);
        springApp.setWebApplicationType(WebApplicationType.NONE);
        springApp.run(args);
    }

    /***
     * Lesson 1 :
     *
     * To verify only @EnableScheduling makes the @Scheduled run in single thread
     *
     *
     * "fixRate" should be executed even if the job is not done. However it is single threaded,
     *
     * log is printed every three seconds, not one second
     *
     * 2019-09-27 17:49:46.361  INFO 84540 --- [   scheduling-1] .m.s.s.t.ScheduleSingleThreadApplication : Single Thread...
     * 2019-09-27 17:49:49.366  INFO 84540 --- [   scheduling-1] .m.s.s.t.ScheduleSingleThreadApplication : Single Thread...
     * 2019-09-27 17:49:52.371  INFO 84540 --- [   scheduling-1] .m.s.s.t.ScheduleSingleThreadApplication : Single Thread...
     * 2019-09-27 17:49:55.376  INFO 84540 --- [   scheduling-1] .m.s.s.t.ScheduleSingleThreadApplication : Single Thread...
     *
     */
    @Scheduled(fixedRate = 1000)
    public void printLogPerSecond() throws InterruptedException {
        logger.info("Single Thread...");
        Thread.sleep(3000);
    }

}
