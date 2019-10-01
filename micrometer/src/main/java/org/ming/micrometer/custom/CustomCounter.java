package org.ming.micrometer.custom;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Async("agentThreadPool")
@Component
public class CustomCounter {


    @Autowired
    private MeterRegistry meterRegistry;

    private Counter counter;
    private Counter counter2;

    private List<Integer> moniteredList;

    /****
     *  - meterRegistry.counter() will register ( register if necessary, or return the existing Counter )
     *  - Save the counter as local env will save more resource to lookup the existing counter
     */
    @PostConstruct
    public void init(){
       counter = meterRegistry.counter("custom.counter","counter.name","counter1");
       counter2 = meterRegistry.counter("custom.counter","counter.name","counter2");

        monitorListSize();
    }

    private void monitorListSize() {

        moniteredList = new ArrayList<>();
        FunctionCounter counter = FunctionCounter
                .builder("list.size.counter", moniteredList, coll -> coll.size())
                .register(meterRegistry);


    }

    @Scheduled(fixedRate = 100)
    public void addCounter(){
        counter.increment();
    }

    @Scheduled(fixedRate = 100)
    public void addCounterByRegistry(){
        counter2.increment(2.2);
    }

    @Scheduled(fixedRate = 1000)
    public void addList(){
        moniteredList.add(1);
    }

}
