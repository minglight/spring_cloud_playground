package org.ming.micrometer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableAsync
@Configuration
public class ScheduleConfiguration {




    @Bean("agentThreadPool")
    public ExecutorService agentThreadPool(){
        return Executors.newFixedThreadPool(50);
    }


}
