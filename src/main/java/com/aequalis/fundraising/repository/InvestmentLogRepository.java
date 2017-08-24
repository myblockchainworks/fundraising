/**
 * 
 */
package com.aequalis.fundraising.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aequalis.fundraising.model.InvestmentLog;
import java.lang.String;
import java.util.List;
import com.aequalis.fundraising.model.User;
import java.lang.Boolean;
import java.lang.Long;

/**
 * @author anand
 *
 */
public interface InvestmentLogRepository extends JpaRepository<InvestmentLog, Long>  {
	
	InvestmentLog findByInvestmentid(Long investmentid);
	
	List<InvestmentLog> findByReferencenumber(String referencenumber);
	
	List<InvestmentLog> findByCreator(User creator);
	
	List<InvestmentLog> findByReferencenumberAndCreator(String referencenumber, User creator);
	
	List<InvestmentLog> findByReferencenumberAndRefundstatus(String referencenumber, Boolean refundstatus);
}
