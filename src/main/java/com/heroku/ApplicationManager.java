package com.heroku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.heroku.security.entities.UserAccount;
import com.heroku.security.services.UserService;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = WebMvcAutoConfiguration.class)
public class ApplicationManager {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationManager.class, args);
    }
    
    @Autowired
    UserService customerUserDetailsService;

    @Bean
    CommandLineRunner init(final UserService userService) {
      
      return new CommandLineRunner() {

        @Override
        public void run(String... arg0) throws Exception {
//        	customerUserDetailsService.save(new UserAccount("admin", "admin"));
    		System.out.println("Length!" + arg0.length);
    		
    		for (int i = 0; i< arg0.length; i++)
    			System.out.println("arg[" + i + "]=" + arg0[i] );

        	if (arg0.length != 2)
        		System.out.println("Usage ApplicationManager <password> <email>!");
        	else {
	        	System.out.println("EXECUTING ApplicationManager this will create or replace the SUPER USER admin !!!! - " + arg0.length);
	        	String password = arg0[0];
	        	String email = arg0[1];
	        	UserAccount u = new UserAccount();
	        	u.setUsername("admin");
	        	u.setPassword(password);
	        	u.setEmail(email);
	        	u.setEnabled(1);
	        	userService.save(u);
	        	
	        	System.out.println("User admin has been reset!");
	        	System.out.println("Press CTRL+C to end application...");
        	}
        }
        
      };

    }
    
}