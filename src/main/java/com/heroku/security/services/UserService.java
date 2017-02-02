package com.heroku.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.heroku.security.entities.Role;
import com.heroku.security.entities.UserAccount;
import com.heroku.security.repositories.UserRepository;
import com.heroku.security.repositories.UserRolesRepository;

@Service("customUserDetailsService")
public class UserService implements UserDetailsService{
	private final UserRepository userRepository;
	private final UserRolesRepository userRolesRepository;
	
	
	
	@Autowired
    public UserService(UserRepository userRepository,UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository=userRolesRepository;
    }
	
	public UserAccount findByUsername(String username){
		return userRepository.findByUsername(username);
	}
    
	public List<Role> findRolesByUsername(String username){
		return userRolesRepository.findUserRolesByUserName(username);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user=userRepository.findByUsername(username);
		if(null == user){
			throw new UsernameNotFoundException("No user present with username: "+username);
		}else{
			List<String> userRoles=userRolesRepository.findRoleByUserName(username);
			return new CustomUserDetails(user,userRoles);
		}
	}
	
	public UserDetails save(UserAccount u){
		UserAccount ua = userRepository.save(u);
		
		return loadUserByUsername(ua.getUsername());
	}
	
		
}
