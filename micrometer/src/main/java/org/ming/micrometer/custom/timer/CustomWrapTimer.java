package org.ming.micrometer.custom.timer;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Async("agentThreadPool")
@Component
public class CustomWrapTimer {

    private static final Logger logger = LoggerFactory.getLogger(CustomWrapTimer.class);

    @Autowired
    private MeterRegistry meterRegistry;

    private ExecutorService threadPool;

    private Timer wrappingTimer;

    @PostConstruct
    public void init(){
        threadPool = Executors.newFixedThreadPool(5);

        wrappingTimer = Timer.builder("custom.wrap.timer")
                .register(meterRegistry);
    }

    @Scheduled(fixedRate = 1000)
    public void addJob(){
        Runnable wrappedRunnable = wrappingTimer.wrap(()-> executeJob());
        threadPool.execute(wrappedRunnable);
    }

    private void executeJob(){
        try {

            Thread.sleep(new Random().nextInt(10000));
            logger.info("This is a delayed task");
        } catch (InterruptedException theE) {
            logger.error(theE.getMessage(), theE);
        }
    }






}
