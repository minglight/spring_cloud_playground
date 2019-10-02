package org.ming.micrometer.spring.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Async("agentThreadPool")
@Component
public class HttpClientMetrics {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientMetrics.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate globalRestTemplate;

    private RestTemplate newRestTemplate;

    @Value("${http.agent.root.uri}") String httpAgentRootUri;
    @Value("${server.port}") Integer serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @PostConstruct
    public void init(){
        globalRestTemplate = restTemplateFromBuilder();
        newRestTemplate = restTemplateFromNew();
    }

    public RestTemplate restTemplateFromBuilder(){
        String rootPath = httpAgentRootUri+":"+serverPort+"/"+contextPath;
        return restTemplateBuilder
                .rootUri(rootPath)
                .build();
    }

    public RestTemplate restTemplateFromNew(){
        String rootPath = httpAgentRootUri+":"+serverPort+"/"+contextPath;
        return new RestTemplateBuilder()
                .rootUri(rootPath)
                .build();
    }

    /***
     * Use Auto-config RestTemplateBuilder to build
     */
    @Scheduled(fixedRate = 1000)
    public void pingUserByGlobal(){
        String value = globalRestTemplate.getForObject("/mvc/user", String.class);
        logger.debug("ping /mvc/user, return={}", value);
    }

    /***
     * The query will be included
     *
     * Prometheus metrics :
     * http_client_requests_seconds_count{clientName="127.0.0.1",method="GET",status="200",<b>uri="/mvc/user?abc=123"</b>,} 5.0
     *
     */
    @Scheduled(fixedRate = 1000)
    public void pingUserByGlobalWithQueryString(){
        String value = globalRestTemplate.getForObject("/mvc/user?abc=123", String.class);
        logger.debug("ping /mvc/user, return={}", value);
    }

    /***
     * There is no metrics shown as "/mvc/user?instance=new"
     *
     * Use "newRestTemplate", not globalRestTemplate
     *
     * Only the @Autowired RestTemplateBuilder will record the client metrics
     */
    @Scheduled(fixedRate = 1000)
    public void pingUserWithNewRestTemplate(){
        String value = newRestTemplate.getForObject("/mvc/user?instance=new", String.class);
        logger.debug("ping /mvc/user, return={}", value);
    }

}
