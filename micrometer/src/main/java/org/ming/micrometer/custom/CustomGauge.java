package org.ming.micrometer.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * Note!!
 *
 * Gauges are useful for monitoring things with natural upper bounds.
 * We don’t recommend using a gauge to monitor things like request count, as they can grow without bound for the duration of an application instance’s life.
 */
@Async("agentThreadPool")
@Component
public class CustomGauge {


    @Autowired
    private MeterRegistry meterRegistry;

    private List<Integer> moniteredList1;
    private List<Integer> moniteredList2;

    private AtomicInteger rawIntegerGauge;


    /****
     *  - meterRegistry.counter() will register ( register if necessary, or return the existing Counter )
     *  - Save the counter as local env will save more resource to lookup the existing counter
     */
    @PostConstruct
    public void init(){

        //weak reference, only count by reference
        moniteredList1  = meterRegistry.gauge("custom.weak.ref.gauge", Tags.of("list","list1"), new ArrayList(), List::size);
        moniteredList2  = meterRegistry.gaugeCollectionSize("custom.weak.ref.gauge", Tags.of("list","list2"), new ArrayList());

        //Manually increase/decrease gauge num by call  T gauge(String name, T number);
        rawIntegerGauge = meterRegistry.gauge("custom.raw.gauge", new AtomicInteger(0));


    }


    @Scheduled(fixedRate = 500)
    public void playGauge1(){
        playListRandomly(moniteredList1);
    }

    @Scheduled(fixedRate = 1000)
    public void playGauge2(){
        playListRandomly(moniteredList2);
    }

    @Scheduled(fixedRate = 400)
    public void playRawGauge(){
        int randomNum = new Random().nextInt(100);
        rawIntegerGauge.set(randomNum);

    }

    private void playListRandomly(final List<Integer> moniteredList) {
        int randomNum = new Random().nextInt(10);
        if (new Random().nextBoolean()) {
            IntStream.range(0, randomNum).forEach(i -> moniteredList.add(i));
        } else {
            IntStream.range(0, randomNum).forEach(i -> {
                if (moniteredList.size() > 0) {
                    moniteredList.remove(moniteredList.size() - 1);
                }
            });
        }
    }

}
