package org.ming.schedule.async.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ScheduleAsyncPoolApplication {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleAsyncPoolApplication.class);

    public static void main(String[] args) {
        //disable web to avoid conflict
        SpringApplication springApp = new SpringApplication(ScheduleAsyncPoolApplication.class);
        springApp.setWebApplicationType(WebApplicationType.NONE);
        springApp.run(args);
    }

    @Bean
    public ExecutorService threadPool1(){
        return Executors.newFixedThreadPool(20);
    }


    /***
     * Lesson 4 : Due to the default threadPool is limited and shared in the application, specify a thread pool is a proper way to execute it
     * Specified a thread pool in the @Async
     */
    @Async("threadPool1")
    @Scheduled(fixedRate = 1000)
    public void printLogPerSecond() throws InterruptedException {
        logger.info("@Async should be printed every second");
        Thread.sleep(30000);

    }



}
