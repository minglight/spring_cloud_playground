package org.ming.micrometer.custom.timer;

import java.util.Random;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * Must have TimedAspect as a bean see https://micrometer.io/docs/concepts#_registry
 *
 */
@Async("agentThreadPool")
@Component
public class CustomTimedAnnotation {

    private static final Logger logger = LoggerFactory.getLogger(CustomTimedAnnotation.class);

    @Scheduled(fixedRate = 2000)
    @Timed(value="custom.annotated.timer")
    public void timed(){
        try {
            Thread.sleep(new Random().nextInt(10000));
            logger.info("do annotated timer Job");
        } catch (InterruptedException theE) {
            theE.printStackTrace();
        }
    }




}
