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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.heroku.security.entities.Role;
import com.heroku.security.entities.UserAccount;
import com.heroku.security.repositories.UserRepository;
import com.heroku.security.repositories.UserRolesRepository;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = WebMvcAutoConfiguration.class)
public class ApplicationManager {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationManager.class, args);
    }
    
    @Autowired
    UserDetailsService customerUserDetailsService;
    
    @Autowired 
    UserRolesRepository userRolesRepository;
    
    @Bean
    CommandLineRunner init(final UserRepository userRepository) {
      
      return new CommandLineRunner() {

        @SuppressWarnings("serial")
		@Override
        public void run(String... arg0) throws Exception {
    		
    		for (int i = 0; i< arg0.length; i++)
    			System.out.println("arg[" + i + "]=" + arg0[i] );

        	if (arg0.length == 2){
	        	System.out.println("EXECUTING ApplicationManager this will create or replace the SUPER USER admin !!!! - " + arg0.length);
	        	String password = arg0[0];
	        	String email = arg0[1];
	        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    		String hashedPassword = passwordEncoder.encode(password);
	    		
	    		UserAccount u = userRepository.findByUsername("admin");
	    		Set<Role> r = new HashSet<Role>(){{
                    add(new Role("ROLE_ADMIN"));
                }};
                
	    		if (u == null){
	    			u = new UserAccount("admin", hashedPassword, email,r);
	    		}else{
	    			u.setEmail(email);
	    			u.setPassword(hashedPassword);
	    			u.setEnabled(1);
	    			u.setRoles(r);
	    		}
	    		
	    		userRepository.save(u);
	        	
	        	System.out.println("\n\nUser admin has been reset!\n\n");
	        	System.out.println("Press CTRL+C to end application...\n\n");
        	}
        }
        
      };

    }
    
}