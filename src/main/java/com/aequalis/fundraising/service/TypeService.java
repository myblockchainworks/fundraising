/**
 * 
 */
package com.aequalis.fundraising.service;

import java.util.List;

import com.aequalis.fundraising.model.Type;


/**
 * @author anand
 *
 */
public interface TypeService {
	public List<Type> findAllType();
	
	public Type findByTypeid(Long typeid);
	
	public Type findByName(String name);
}
