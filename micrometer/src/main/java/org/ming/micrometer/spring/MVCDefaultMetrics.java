package org.ming.micrometer.spring;

import java.util.Random;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Default Metrics Name => "http.server.requests"
 */
@RestController
public class MVCDefaultMetrics {

    @GetMapping("/mvc/user")
    public String getUser(){

        return "Default User";

    }

    @Timed(value="user.longtask",extraTags = {"longTask", "true"}, longTask = true)
    @GetMapping("/mvc/user/longtask")
    public String getUserLong() throws InterruptedException {

        //Sleep 10s + (0 - 30) s
        Thread.sleep( 10000 + new Random().nextInt(300000));
        return "Default Long User";

    }

}
