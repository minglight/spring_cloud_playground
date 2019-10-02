# Micrometer
  
Though Micrometer is not one of the Spring project, but it is official metrics support for Spring, because it is one of the Pivotal project

# Goal of Demo

In this demo project, I'll show how micrometer integrate with spring project through Spring Actuator

The target will be

* Default Spring integrated metrics

Custom
* Counter
* Gauge
* Timer
* Functional Counter
* Functional Gauge
* Summary
* Long Task Timer
* Monitor Thread


# Reference Pages

* [Micrometer Document](https://micrometer.io/docs/concepts#_counters)
* [Spring Metrics](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-metrics.html)


## TODO : 

-[x] Setup Env ( Gradle, application.yml )
-[x] Impl and check spring default metrics 
-[x] Impl RestClient to 
  -[x] Call the API above
  -[x] Read the metrics 
-[ ] Impl basic customized metrics
  -[x] Counter
  -[x] Gauge
  -[x] Timer
  -[x] Summary
  -[x] Long Task Timer
-[x] Impl ThreadPool monitoring