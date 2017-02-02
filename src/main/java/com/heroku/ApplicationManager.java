package com.heroku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.heroku.security.repositories.UserRepository;
import com.heroku.security.services.UserService;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = WebMvcAutoConfiguration.class)
public class ApplicationManager {

    public static void main(String[] args) {
        //SpringApplication.run(ApplicationManager.class, args);
    	ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ApplicationManager.class)
                .web(false)
                .run(args);
    }
    
    @Autowired
    UserService customerUserDetailsService;

    @Bean
    CommandLineRunner init(final UserRepository userRepository) {
      
      return new CommandLineRunner() {

        @Override
        public void run(String... arg0) throws Exception {
//        	customerUserDetailsService.save(new UserAccount("admin", "admin"));
        	System.out.println("EXECUTING ApplicationManager - CommandLineRunner!!!! - " + arg0);
        }
        
      };

    }
    
}