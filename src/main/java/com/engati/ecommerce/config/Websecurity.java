//package com.engati.ecommerce.config;
//
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//
//public abstract class Websecurity implements
//        WebSecurityConfigurer<WebSecurity> {
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()  // (1)
//                .and();
//        // (3)
//    }
//}
