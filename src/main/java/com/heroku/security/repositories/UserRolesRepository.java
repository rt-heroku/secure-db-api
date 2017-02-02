package com.heroku.security.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.heroku.security.entities.Role;

@Repository
public interface UserRolesRepository extends CrudRepository<Role, Long> {
	
	@Query("select a.role from Role a, UserAccount b where b.userName=?1 and a.userid=b.id")
    public List<String> findRoleByUserName(String username);
	
}