package org.code_studio.main;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class ApplicationEventListener {

	@Value("${request.enabled:false}")
	private boolean isEnabled;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	//System.out.println("ContextRefreshedEvent");

    }
    
    /* TODO: Kako koristiti ???
	@Bean
	FilterRegistrationBean<RequestFilter> loggingFilter() {
	    FilterRegistrationBean<RequestFilter> registrationBean
	      = new FilterRegistrationBean<>();

	    registrationBean.setFilter(new RequestFilter(isEnabled));
	    registrationBean.addUrlPatterns("/**");
	    registrationBean.setOrder(0);
	    System.out.println("BEAN CREATED");
	    return registrationBean;
	}
	*/
}