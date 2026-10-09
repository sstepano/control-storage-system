package org.code_studio.system;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import jakarta.annotation.PostConstruct;

public abstract class BaseScheduler implements SchedulingConfigurer {
	
	@SuppressWarnings("unused")
	@Autowired
    private ApplicationContext ctx;	
	private String cron = "*/10 * * * * *";
	
	Logger log = LoggerFactory.getLogger(BaseScheduler.class);
	
	public BaseScheduler() {}

	@PostConstruct
	private void getClassBean() {}
	
	private Runnable getTask() {
		return new Runnable() {
            public void run() {
            	System.out.println("Schedule has just ran.");
            }
		};
	}
	
	private Trigger getTrigger() {
		return new Trigger() {
            public Instant nextExecution(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(cron);
                Instant nextExecution = cronTrigger.nextExecution(triggerContext);
                return nextExecution;
            }
        };
	}
	
	@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(getTask(), getTrigger());		
	}	
	

	/**
	@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

		taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //log.info("Current time: {}", LocalDateTime.now());
            	System.out.println("Just has ran");
            }
        }, new Trigger() {
            @Override
            public Instant nextExecution(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(cron);
                Instant nextExecution = cronTrigger.nextExecution(triggerContext);
                return nextExecution;
            }
        });		
	}
	**/
	
}
