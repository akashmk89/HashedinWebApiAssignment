//package com.hashedin.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///***
// * Security configuration class
// */
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter
//{
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//       return NoOpPasswordEncoder.getInstance();
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated();
//    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        System.out.println("web security config line 58");
////        http.csrf().disable().authorizeRequests()
////                .antMatchers("/api/authenticate","/swagger-ui.html").permitAll()
////                .anyRequest().authenticated().and().exceptionHandling()
////                .and()
////                .sessionManagement().
////                sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////
////}
//
//
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("password")
//                .roles("USER");
//    }
//}