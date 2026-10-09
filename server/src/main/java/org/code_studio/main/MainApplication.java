package org.code_studio.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
//
/* 
 * import org.h2.tools.Server;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.cache.annotation.EnableCaching;
 * import org.springframework.context.annotation.Configuration;
 */
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EntityScan (basePackages = "org.code_studio.database")
@ComponentScan ({
	"org.code_studio.main", 
	"org.code_studio.controller", 
	"org.code_studio.system",
	"org.code_studio.service",
	"org.code_studio.security"
})
@EnableJpaRepositories (basePackages = "org.code_studio.model")
//@Configuration
//@EnableCaching
public class MainApplication {

	public static void main(String[] args) {
		// H2: This must be done before loading any classes of this database (before loading the JDBC driver)
		//System.setProperty("h2.bindAddress", "localhost");
		SpringApplication.run(MainApplication.class, args);
	}
	
	/*
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        // return Server.createTcpServer("-tcp", "-tcpPort", "9998", "-baseDir", "D:\\dev\\java-ws\\repro-market\\db");
    	return Server.createTcpServer("-tcp", "-tcpPort", "9998", "-baseDir", "../../db");
    }
    */
}
