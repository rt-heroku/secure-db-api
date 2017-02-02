package com.heroku.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@ComponentScan(basePackageClasses = UserService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

 @Autowired 
 private UserDetailsService userDetailsService;
 
 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
 }
 
 @Override
 protected void configure(HttpSecurity http) throws Exception {
     http
     	.authorizeRequests()
     		.antMatchers("/login").permitAll()
//     .antMatchers(HttpMethod.GET, "/contacts").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//     .antMatchers(HttpMethod.GET, "/product2s").hasAnyAuthority("ROLE_ADMIN")
//     .antMatchers(HttpMethod.GET, "/api/v1/profiles/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//     .antMatchers(HttpMethod.POST, "/api/v1/profiles/**").hasAnyAuthority("ROLE_ADMIN")
//     .antMatchers(HttpMethod.PUT, "/api/v1/profiles/**").hasAnyAuthority("ROLE_ADMIN")
//     .antMatchers(HttpMethod.DELETE, "/api/v1/profiles/**").hasAnyAuthority("ROLE_ADMIN")
     		.anyRequest().authenticated()
//     	.and()
//     		.logout()
//	     		.permitAll()
	     .and()
	     	.httpBasic()
	     .and()
	     	.csrf().disable();
 }
 
 @Bean(name="passwordEncoder")
    public PasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
    }
}
