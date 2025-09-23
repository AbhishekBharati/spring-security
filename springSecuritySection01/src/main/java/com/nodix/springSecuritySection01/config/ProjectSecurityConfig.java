package com.nodix.springSecuritySection01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        This will permit all the requests to the api without authentication
//        http.authorizeHttpRequests((requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).permitAll());
//        This will deny all the requests to the API irrespective of authentication.
//        http.authorizeHttpRequests((requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).denyAll());
        http.authorizeHttpRequests((requests) -> (requests.
                requestMatchers("/myBalance", "/myAccount", "/myLoans", "myCards")).authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll()
        );
//        Form Login is responsible to give us that login form in beginning.
//        http.formLogin(Customizer.withDefaults());
//        This will disable the Form Login.
        http.formLogin(flc -> flc.disable());
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }
}
