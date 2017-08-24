/**
 * 
 */
package com.aequalis.fundraising.service;

import java.util.List;

import com.aequalis.fundraising.model.Type;
import com.aequalis.fundraising.model.User;

/**
 * @author anand
 *
 */
public interface UserService {
	public void addUser(User user);
	
	public User loginUser(String username, String password);
	
	public User findByUserid(Long userid);
	
	public User findByUsername(String username);
	
	public 	List<User> findAll();
	
	public User findByBcaddress(String bcaddress);
	
	public List<User> findByType(Type type);
	
}
