/**
 * 
 */
package com.aequalis.fundraising.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aequalis.fundraising.model.Type;

import java.lang.Long;
import java.lang.String;

/**
 * @author anand
 *
 */

public interface TypeRepository extends JpaRepository<Type, Long> {

	Type findByTypeid(Long typeid);
	
	Type findByName(String name);
}
