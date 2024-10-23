//package com.engati.ecommerce.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends Websecurity {
//
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers("/*").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .rememberMe(Customizer.withDefaults());
////
////        return http.build();
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {  // (2)
//        http
//                .authorizeRequests()// (3)
//                .anyRequest().authenticated() // (4)
//                .and()
//            // (5)
//                .loginPage("/") // (5)
//                .permitAll()
//                .and()
//                .logout() // (6)
//                .permitAll()
//                .and()
//                .httpBasic(); // (7)
//    }
//
//
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("admin")
////                .password("{noop}password123") // Password "password123" is used for development purposes (NoOpPasswordEncoder)
////                .roles("ADMIN")
////                .build());
////        return manager;
////    }
//
//
//
//}
