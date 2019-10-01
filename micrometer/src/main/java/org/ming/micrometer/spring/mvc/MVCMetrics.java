package org.ming.micrometer.spring.mvc;

import java.util.Random;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Default Metrics Name => "http.server.requests"
 */
@RestController
public class MVCMetrics {

    @GetMapping("/mvc/user")
    public String getUser(){

        return "Default User";

    }

    @Timed(histogram = true)
    @GetMapping("/mvc/user/histogram")
    public String getUserHistogram() throws InterruptedException {
        Thread.sleep(new Random().nextInt(10000));
        return "Default User Histogram";

    }

    /***
     * @Timed( longTask = true ) doesn't work for Prometheus. Prometheus requires that all meters with the same name have the same set of tag keys
     *
     * Micrometer will run both @Timed with default @Timed tags [exception, method, outcome, status, uri] and longTask tags [method, uri]
     *
     *
     *
     * Note:
     * 1. If you google it, this doesn't work
     *  <b>management.metrics.web.server.auto-time-requests=false</b>
     *
     * 2. The Exception is as follow
     * Exception :
     * java.lang.IllegalArgumentException: Prometheus requires that all meters with the same name have the same set of tag keys. There is already an existing meter named 'user_longtask_hello_seconds' containing tag keys [method, uri]. The meter you are attempting to register has keys [exception, method, outcome, status, uri].
     * 	at io.micrometer.prometheus.PrometheusMeterRegistry.lambda$collectorByName$9(PrometheusMeterRegistry.java:382)
     * 	at java.base/java.util.concurrent.ConcurrentHashMap.compute(ConcurrentHashMap.java:1932)
     * 	at io.micrometer.prometheus.PrometheusMeterRegistry.collectorByName(PrometheusMeterRegistry.java:369)
     * 	at io.micrometer.prometheus.PrometheusMeterRegistry.newTimer(PrometheusMeterRegistry.java:175)
     * 	at io.micrometer.core.instrument.MeterRegistry.lambda$timer$2(MeterRegistry.java:270)
     * 	at io.micrometer.core.instrument.MeterRegistry.getOrCreateMeter(MeterRegistry.java:575)
     * 	at io.micrometer.core.instrument.MeterRegistry.registerMeterIfNecessary(MeterRegistry.java:528)
     * 	at io.micrometer.core.instrument.MeterRegistry.timer(MeterRegistry.java:268)
     * 	at io.micrometer.core.instrument.Timer$Builder.register(Timer.java:464)
     * 	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.stop(WebMvcMetricsFilter.java:180)
     * 	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.record(WebMvcMetricsFilter.java:174)
     * 	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.filterAndRecordMetrics(WebMvcMetricsFilter.java:130)
     * 	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:104)
     * 	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:118)
     * 	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
     * 	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
     * 	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
     * 	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:118)
     * 	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
     * 	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
     * 	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)
     * 	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
     * 	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:526)
     * 	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
     * 	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
     * 	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
     * 	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
     * 	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)
     * 	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
     * 	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:860)
     * 	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1587)
     * 	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
     * 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
     * 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
     * 	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
     * 	at java.base/java.lang.Thread.run(Thread.java:834)
     *
     *
     * @return
     * @throws InterruptedException
     */
//    @Timed(value="user.longtask.hello",histogram = true,longTask = true)
    @GetMapping("/mvc/user/longtask")
    public String getUserLong() throws InterruptedException {

        //Sleep 10s + (0 - 30) s
        Thread.sleep( 10 + new Random().nextInt(1));
        return "Default Long User";

    }

}
