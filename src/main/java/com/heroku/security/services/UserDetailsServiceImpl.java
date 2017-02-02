package com.heroku.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heroku.security.entities.Role;
import com.heroku.security.entities.UserAccount;
import com.heroku.security.repositories.UserRepository;
import com.heroku.security.repositories.UserRolesRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserRolesRepository userRolesRepository;
    
	public UserAccount findByUsername(String username){
		return userRepository.findByUsername(username);
	}
    
	public void save(UserAccount user){
		userRepository.save(user);
	}
	
	public List<Role> findRolesByUsername(String username){
		return userRolesRepository.findUserRolesByUserName(username);
	}
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
