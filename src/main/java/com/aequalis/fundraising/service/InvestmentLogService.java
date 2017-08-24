/**
 * 
 */
package com.aequalis.fundraising.service;

import java.util.List;

import com.aequalis.fundraising.model.InvestmentLog;
import com.aequalis.fundraising.model.User;


/**
 * @author anand
 *
 */
public interface InvestmentLogService {
	
	InvestmentLog findByInvestmentid(Long investmentid);
	
	public void addInvestmentLog(InvestmentLog investmentLog);
	
	List<InvestmentLog> findByReferencenumberAndCreator(String referencenumber, User creator);
	
	List<InvestmentLog> findByReferencenumberAndRefundstatus(String referencenumber, Boolean refundstatus);
}
