package com.heroku.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.security.entities.UserAccount;
import com.heroku.security.services.SecurityService;
import com.heroku.security.services.UserDetailsServiceImpl;

@RestController
@RequestMapping()
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private SecurityService securityService;

//    @Autowired
//    private UserValidator userValidator;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> doLogin(@RequestParam String username, @RequestParam String password) {
        securityService.autologin(username, password);

        return new ResponseEntity<>(HttpStatus.OK);
	}

    @RequestMapping(value = "/whoami", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> whoami() {

    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Map<String, Object> ret = new HashMap<String,Object>();
    	
    	Object username = auth.getPrincipal();
    	
 //   	UserDetails ua = userService.loadUserByUsername(username);
    	ret.put("user", username);
 //   	ret.put("details", ua);
    	ret.put("name", auth.getName());
    	ret.put("authorities", auth.getAuthorities());
    	ret.put("auth_details", auth.getDetails());
        return ret;
	}
    
    
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDetails> getUser(@RequestParam String username) {

    	UserDetails ua = userService.loadUserByUsername(username);

        return new ResponseEntity<UserDetails>(ua,HttpStatus.OK);
	}
//	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public void doLogout() {
//	}

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> registration(//@Valid 
    		@RequestBody UserAccount user) {
//        userValidator.validate(user, bindingResult);

//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		
        userService.save(user);

        securityService.autologin(user.getUsername(), user.getPassword());

        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
