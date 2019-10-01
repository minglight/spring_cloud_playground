package org.ming.micrometer.custom.timer;

import java.util.Random;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * Timer.Sample can do dynamic tags
 *
 * Note : If you are using Prometheus, only the tag value can be dynamic.
 */
@Async("agentThreadPool")
@Component
public class CustomTimerInline {

    private static final Logger logger = LoggerFactory.getLogger(CustomTimerInline.class);

    @Autowired
    private MeterRegistry meterRegistry;


    @Scheduled(fixedRate = 1500)
    public void inlineTimer(){

        String dynamicTagValue = "inlineTimer" + new Random().nextInt(3);

        Timer.Sample sample = Timer.start();
        doJob();
        sample.stop(meterRegistry.timer("custom.inline.timer", "myTag",dynamicTagValue));

    }

    private void doJob(){
        try {
            Thread.sleep(new Random().nextInt(10000));
            logger.info("do inline Job");
        } catch (InterruptedException theE) {
            theE.printStackTrace();
        }
    }





}
