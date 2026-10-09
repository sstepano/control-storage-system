package org.code_studio.system;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	@Autowired
    private ApplicationContext ctx;
	private FolderWatcher folderWatcher;
	
	public Scheduler() {}
	
	@PostConstruct
	private void getClassBean() {
		folderWatcher = ctx.getBean(FolderWatcher.class);
	}
	
	//@Scheduled(fixedDelay = 5000)
	@Scheduled (cron = "*/15 * * * * *") // @hourly
	public void schedule() {
		folderWatcher.pollDirectoryChanges();
	}
	
}
