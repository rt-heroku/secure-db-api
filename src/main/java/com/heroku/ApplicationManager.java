package com.heroku;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.heroku.security.entities.Role;
import com.heroku.security.entities.UserAccount;
import com.heroku.security.repositories.UserRolesRepository;
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

    @Autowired 
    UserRolesRepository userRolesRepository;
    
    @Bean
    CommandLineRunner init(final UserService userService) {
      
      return new CommandLineRunner() {

        @Override
        public void run(String... arg0) throws Exception {
    		
    		for (int i = 0; i< arg0.length; i++)
    			System.out.println("arg[" + i + "]=" + arg0[i] );

        	if (arg0.length == 2){
	        	System.out.println("EXECUTING ApplicationManager this will create or replace the SUPER USER admin !!!! - " + arg0.length);
	        	String password = arg0[0];
	        	String email = arg0[1];
	        	UserAccount u = new UserAccount();
	        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    		String hashedPassword = passwordEncoder.encode(password);
	    		Role r = new Role("ROLE_ADMIN");

	    		u.setUsername("admin");
	    		u.setPassword(hashedPassword);
	        	u.setEmail(email);
	        	u.setEnabled(1);
	        	userService.save(u);
	        	
	        	UserAccount admin = userService.findByUsername("admin");
	        	
	        	Set<UserAccount> users = new HashSet<UserAccount>();
	        	users.add(admin);
	        	r.setUsers(users);
	        	
	        	userRolesRepository.save(r);
	        	
	        	System.out.println("User admin has been reset!");
	        	System.out.println("Press CTRL+C to end application...");
        	}
        }
        
      };

    }
    
}