package org.code_studio.main;

import java.util.Locale;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@Component
public class JfxMainApplication extends Application {
	
	//@Autowired
	private ConfigurableApplicationContext applicationContext;
	
	@Override
	public void init() {
		try {
		applicationContext = new SpringApplicationBuilder(SpringBootJfxApplication.class).run();
		} catch (Exception ex) {
			Common.logMessage(Common.class, ex, "ERROR");
		}
	}
	
	@Override
	public void start(Stage stage) {
		
		/*******************************************
		 * Set LOCALE to the entire application
		 * This way we control date and time formats
		 *******************************************/
		Locale locale = new Locale.Builder()
				  .setLanguage("sr")
				  .setLanguageTag("sr-RS")
				  .setScript("Latn")
				  .build();
		Locale.setDefault(locale);
		
		applicationContext.publishEvent(new StageReadyEvent(stage));
	}
	
	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}
	
	static class StageReadyEvent extends ApplicationEvent {
		private static final long serialVersionUID = 1L;

		public StageReadyEvent (Stage stage) {
			super(stage);
		}
		
		public Stage getStage() {
			return ((Stage) getSource());
		}
	}
}
