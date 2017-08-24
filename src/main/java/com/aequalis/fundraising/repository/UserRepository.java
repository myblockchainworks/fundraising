/**
 * 
 */
package com.aequalis.fundraising.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aequalis.fundraising.model.User;

import java.lang.String;
import com.aequalis.fundraising.model.Type;
import java.util.List;

/**
 * @author anand
 *
 */
public interface UserRepository extends JpaRepository<User, Long>  {
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUserid(Long userid);
	
	User findByUsername(String username);
	
	User findByBcaddress(String bcaddress);
	
	List<User> findByType(Type type);
	
}
