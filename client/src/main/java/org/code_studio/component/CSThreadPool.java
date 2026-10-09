package org.code_studio.component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// SINGLETON PATTERN IMPLEMENTED
public class CSThreadPool {
	
	private static int MAX_THREAD_COUNT = 2;
	//private int MAX_THREAD_WAIT_SECONDS = 10;
	private ExecutorService executorService;
	
	private static CSThreadPool INSTANCE;
	
	private CSThreadPool() {
		executorService = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
	}
	
	public static CSThreadPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CSThreadPool();
		}
		return INSTANCE;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	/* Ne znam sta sam amislio ovim, trenutno ne koristim
	public int getMAX_THREAD_WAIT_SECONDS() {
		return MAX_THREAD_WAIT_SECONDS;
	}

	protected void setMAX_THREAD_WAIT_SECONDS(int MAX_THREAD_WAIT_SECONDS) {
		this.MAX_THREAD_WAIT_SECONDS = MAX_THREAD_WAIT_SECONDS;
	}
	*/
}
