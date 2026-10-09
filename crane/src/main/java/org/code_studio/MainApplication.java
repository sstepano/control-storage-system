package org.code_studio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.code_studio, org.code_studio.model"})
public class MainApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
