package org.code_studio.system;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import jakarta.annotation.PostConstruct;

import org.code_studio.main.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FolderWatcher {
	
	@Autowired
    private ApplicationContext ctx;
	
	@Value("${server.bankStatementWatchFolder}")
	private String bankStatementWatchFolder;
	
	private BankStatementLoader bankStatementLoader;
	
	private WatchService watchService;
	private Path         path;
	private WatchKey     watchKey;
	
	
	public FolderWatcher() {}
	
	@PostConstruct
	private void initWatchService () {
		try {
			watchService = FileSystems.getDefault().newWatchService();
			path = Paths.get(Common.getApplicationHome() + "/" + bankStatementWatchFolder);
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			bankStatementLoader = ctx.getBean(BankStatementLoader.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	/***
	 * This method is being called by scheduler to check if there are changes on watched folder
	 * If there are, it calls bankStatementLoader to process the newly created file
	 */
	public void pollDirectoryChanges() {
		watchKey = watchService.poll();
		if (watchKey != null) {
			for (WatchEvent<?> event : watchKey.pollEvents()) {
				File newFile = path.resolve((Path)event.context()).toFile();
				if (validateFile(newFile)) {
					bankStatementLoader.loadFile(newFile);
				}
		    }
		    watchKey.reset();
		}
	}
	
	/***
	 * Validates if the file that was just created, is actually loadable bank statement file
	 * or some junk that we need to ignore
	 * @param File - newly created file in watched folder
	 * @return boolean
	 */
	private boolean validateFile(File file) {
		if (
			file.isFile()
			&& file.getName().length() > 4 // mora bar jedan karakter + extenzija, npr a.txt
			&& file.getName().substring(file.getName().length()-3, file.getName().length()).equals("txt")
		) { return true; }
		return false;
	}

}
