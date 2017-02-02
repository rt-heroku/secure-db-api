package com.heroku.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heroku.security.entities.UserAccount;
import com.heroku.security.services.SecurityService;
import com.heroku.security.services.UserService;

@RestController
@RequestMapping()
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

//    @Autowired
//    private UserValidator userValidator;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> doLogin(@RequestParam String username, @RequestParam String password) {
        securityService.autologin(username, password);

        return new ResponseEntity<>(HttpStatus.OK);
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
