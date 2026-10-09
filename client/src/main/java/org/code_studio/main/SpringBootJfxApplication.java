package org.code_studio.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;

@SpringBootApplication
@ComponentScan(basePackages = { "org.code_studio" })
public class SpringBootJfxApplication {

	public static void main(String[] args) {
		Application.launch(JfxMainApplication.class, args);
	}
}
