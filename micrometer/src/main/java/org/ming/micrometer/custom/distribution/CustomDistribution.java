package org.ming.micrometer.custom.distribution;

import java.util.Random;

import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.DistributionSummary;
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
public class CustomDistribution {

    private static final Logger logger = LoggerFactory.getLogger(CustomDistribution.class);

    @Autowired
    private MeterRegistry meterRegistry;

    private DistributionSummary summary;

    @PostConstruct
    public void init(){
        summary = DistributionSummary
                .builder("custom.distribution")
                .description("a description of what this summary does") // optional
                .baseUnit("bytes") // optional (1)
                .tags("region", "test") // optional
//                .scale(1000) // optional (2)
                .sla(7000,8000,9000)
                .publishPercentileHistogram()
                .maximumExpectedValue((long)10000)
                .register(meterRegistry);
    }


    @Scheduled(fixedRate = 100)
    public void inlineTimer(){
        int amount = new Random().nextInt(10000);
        summary.record(amount);
        logger.info("record in summary : {}",amount);
    }

}
