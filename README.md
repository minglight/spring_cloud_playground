# spring_cloud_playground
This is the playground to test the spring cloud features

# Runtime change Log by calling Rest API

> Reference :[ Runtime change loglevel ](https://www.baeldung.com/spring-boot-changing-log-level-at-runtime)


## Setup : Enable actuator loggers

> management.endpoints.web.exposure.include=loggers

### Check current log levels

GET /actuator/loggers  # View all 
GET /actuator/loggers/org.ming # view by package name "org.ming"

### Change log level

POST -H 'Content-Type: application/json' -d '{"configuredLevel": "TRACE"}
/actuator/logger/org.ming

## Step to prove 

1. GET <b>/log</b> to show current log level 
2. Change log level by REST API
3. GET <b>/log</b> again to show log level