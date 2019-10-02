package org.ming.micrometer.threadpool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * This class demo 2 types of thread metrics
 * 1. The customized one, only monitor time and queued task, metrics name = given name
 * 2. Use Micrometer ExecutorServiceMetrics to wrap the monitored ExecutorService, the metrics name is the same for all the monitored pool with the tag name={given name}
 *
 */
@DependsOn("metricsUtils")
@Async("agentThreadPool")
@Component
public class CustomThreadPoolMetrics {


    @Autowired
    private MeterRegistry meterRegistry;


    private ExecutorService monitoredExecutorService;
    private ExecutorService fullMonitoredExecutorService;

    @PostConstruct
    public void init(){
        monitoredExecutorService = Executors.newFixedThreadPool(1);
        fullMonitoredExecutorService = Executors.newFixedThreadPool(1);
        monitoredExecutorService = monitorTimeAndQueue(monitoredExecutorService, "custom.thread");
        fullMonitoredExecutorService = fullMonitorThreadPool(fullMonitoredExecutorService, "custom.full.thread");

    }

    private ExecutorService fullMonitorThreadPool(final ExecutorService theExecutorService, final String theMetricName) {
        return ExecutorServiceMetrics.monitor(meterRegistry, theExecutorService, theMetricName, Tags.empty());
    }

    private ExecutorService monitorTimeAndQueue(final ExecutorService theExecutorService, final String theMetricName) {
        return MetricsUtils.setMetricsOnExecutorService(theExecutorService, theMetricName);
    }

    @Scheduled(fixedRate = 100)
    public void executeJobs(){
        monitoredExecutorService.execute(() -> sleep(new Random().nextInt(10_000)));
        fullMonitoredExecutorService.execute(() -> sleep(new Random().nextInt(10_000)));
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException theE) {
            theE.printStackTrace();
        }
    }





}
