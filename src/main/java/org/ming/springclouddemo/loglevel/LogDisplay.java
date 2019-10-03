package org.ming.springclouddemo.loglevel;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogDisplay {

    private static final Logger logger = LoggerFactory.getLogger(LogDisplay.class);

    @GetMapping("/log")
    public String printLog(){

        if(logger.isTraceEnabled()){
            logger.trace("Trace");
            return "trace";
        }else if(logger.isDebugEnabled()){
            logger.debug("Debug");
            return "debug";
        }else if(logger.isInfoEnabled()){
            logger.info("Info");
            return "info";
        }else if(logger.isWarnEnabled()){
            logger.warn("Warn");
            return "warn";
        }else if(logger.isErrorEnabled()){
            logger.error("Error");
            return "error";
        }else{
            return "disabled";
        }

    }

}
