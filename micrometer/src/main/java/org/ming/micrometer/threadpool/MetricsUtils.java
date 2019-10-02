package org.ming.micrometer.threadpool;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.internal.TimedExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricsUtils {

    private static final Logger logger = LoggerFactory.getLogger(MetricsUtils.class);

    @Autowired
    private MeterRegistry meterRegistry;

    private static MetricsUtils INSTANCE;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }

    /***
     * Set metrics on the executor service of type ThreadPoolExecutor, ScheduledThreadPoolExecutor and ForkJoinPool
     * If the input theExecutorService is not the type above, it will not set any metrics on it
     *
     * If the input type of ExecutorService is ThreadPoolExecutor, it will wrap the
     *
     * @param theExecutorService
     * @param metricName
     * @param tags
     * @return
     */
    public static <T extends ExecutorService> T setMetricsOnExecutorService(final T theExecutorService,
            final String metricName, final Tag... tags) {

        if (theExecutorService instanceof ScheduledThreadPoolExecutor) {
            Gauge.builder(metricName + ".queued", (ThreadPoolExecutor) theExecutorService,
                    tpRef -> tpRef.getQueue().size())
                    .tags(Arrays.asList(tags))
                    .description("The approximate number of threads that are queued for execution")
                    .baseUnit("threads")
                    .register(INSTANCE.meterRegistry);
            return theExecutorService;
        } else if (theExecutorService instanceof ThreadPoolExecutor) {
            Gauge.builder(metricName + ".queued", (ThreadPoolExecutor) theExecutorService,
                    tpRef -> tpRef.getQueue().size())
                    .tags(Arrays.asList(tags))
                    .description("The approximate number of threads that are queued for execution")
                    .baseUnit("threads")
                    .register(INSTANCE.meterRegistry);
            return (T) new TimedExecutorService(INSTANCE.meterRegistry, theExecutorService, metricName,
                    Arrays.asList(tags));

        } else if (theExecutorService instanceof ForkJoinPool) {
            Gauge.builder(metricName + ".queued", (ForkJoinPool) theExecutorService,
                    ForkJoinPool::getQueuedTaskCount)
                    .tags(Arrays.asList(tags))
                    .description("An estimate of the total number of tasks currently held in queues by worker threads")
                    .register(INSTANCE.meterRegistry);
            return theExecutorService;
        } else {
            logger.warn("Cannot set metrics on the target ExecutorService : metricName={}, tags={}", metricName,
                    Arrays.asList(tags));
            return theExecutorService;
        }

    }

}
