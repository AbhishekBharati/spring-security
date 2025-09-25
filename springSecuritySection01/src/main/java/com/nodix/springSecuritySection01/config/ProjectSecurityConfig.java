package com.nodix.springSecuritySection01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        This will permit all the requests to the api without authentication
//        http.authorizeHttpRequests((requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).permitAll());
//        This will deny all the requests to the API irrespective of authentication.
//        http.authorizeHttpRequests((requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).denyAll());
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> (requests.
                requestMatchers("/myBalance", "/myAccount", "/myLoans", "/myCards")).authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll()
        );
//        Form Login is responsible to give us that login form in beginning.
//        http.formLogin(Customizer.withDefaults());
//        This will disable the Form Login.
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }

//    Now suppose I want to add many users over here itself then we will be doing it this way.
//    @Bean
//    public UserDetailsService userDetailsService(){
////        Here we're putting the bcrypt hashed value so that no one in github can determine what our password is :-
//        UserDetails user = User.withUsername("nodix")
//                .password("{bcrypt}$2a$12$KTFzTpiHvl/UoCaUqbF3QOETfXZQpe2iZfb4h6jrdXV5nzF0iyvX.")
//                .authorities("read")
//                .build();
////        By noop We're specifying that we don't wanna encode our password and want to keep it as plain text.
//        UserDetails admin = User.withUsername("admin").password("{noop}1212").authorities("admin").build();
////        This InMemoryUserDetailsManager is implemented by SpringTeam and it helps us to store the users in Memory
////        Also This will store the users in a HashMap you can go inside and see it's implementation.
//        return new InMemoryUserDetailsManager(user, admin);
//}

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

//    Because of this bean we don't need to write {noop} anymore before the password coz this time it'll use the encryption method that this bean returns.
    @Bean
    public PasswordEncoder passwordEncoder(){
//        This FactoryMethod will by default consider bcrypt for the Password Hashing purposes.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    This bean Checks the password and let us know that If that password is ever been Leaked in a DataBreach or not.
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
