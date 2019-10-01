package org.ming.micrometer;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MicrometerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrometerApplication.class, args);
	}

	@Bean
	public TimedAspect timedAspect(@Autowired MeterRegistry registry) {
		return new TimedAspect(registry);
	}


}
