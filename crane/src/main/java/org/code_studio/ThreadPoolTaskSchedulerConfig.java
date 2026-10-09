package org.code_studio;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan( basePackages = "org.code_studio" )
public class ThreadPoolTaskSchedulerConfig {

	@Bean(name = "taskScheduler")
    public Executor threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(2); // koristimo za kran i COM barcode konekcije
		scheduler.setThreadNamePrefix("--crane-thread-executor");
        return scheduler;
    }
}
