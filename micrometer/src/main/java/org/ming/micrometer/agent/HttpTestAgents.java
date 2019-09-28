package org.ming.micrometer.agent;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@Component
public class HttpTestAgents {

    private static final Logger logger = LoggerFactory.getLogger(HttpTestAgents.class);

    private RestTemplate restTemplate;

    @Value("${http.agent.root.uri}")
    private String httpAgentRootUri;

    @Value("${server.port}")
    private Integer serverPort;

    @PostConstruct
    private void init(){
        restTemplate = new RestTemplateBuilder()
                .rootUri(httpAgentRootUri+":"+serverPort)
                 .build();
    }



    @Scheduled(fixedRate = 1)
    public void pingUser(){
        String value = restTemplate.getForObject("/mvc/user", String.class);
        logger.debug("ping /mvc/user, return={}", value);
    }
//
    @Scheduled(fixedRate = 1)
    public void pingUserLongTask(){
        String value = restTemplate.getForObject("/mvc/user/longtask", String.class);
        logger.debug("ping /mvc/user/longtask, return={}", value);
    }

    @Scheduled(fixedRate = 1000)
    public void printSchedule(){
        logger.info("Hello");
    }





}
