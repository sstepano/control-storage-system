package org.code_studio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity//(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	//@Value("${spring.mvc.servlet.path}" + "/" + "${url.authenticate}") // ovde mora full url sa servlet pathom, sto je /api/
	@Value("${url.authenticate}")
	private String urlAuthenticate;

	@Value("${security.disable}")
	private Boolean securityDisable;
	
	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
    @Bean
    BCryptPasswordEncoder  passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(jwtUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		if (securityDisable) {
            http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable) //.csrf(csrf -> csrf.disable()) ovo je isti kod ali malo drugacije napisan
                .authorizeHttpRequests(auth -> {
                  auth.requestMatchers("/**").permitAll(); // Allow all endpoints
                });
		} else {
			http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(urlAuthenticate).permitAll().anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		}
        return http.build();
        
        /***
        return http.csrf(AbstractHttpConfigurer::disable)   // disable CSRF protection
                .authorizeHttpRequests(httpRequest -> {
                    httpRequest.requestMatchers("/**").permitAll(); // Allow all endpoints
        }).build(); // build & return DefaultSecurityFilterChain
        ***/
    	
    }
    
    /***
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	System.out.println("usooo");
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
    ***/

}