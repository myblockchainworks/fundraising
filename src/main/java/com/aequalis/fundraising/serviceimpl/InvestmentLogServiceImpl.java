/**
 * 
 */
package com.aequalis.fundraising.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aequalis.fundraising.model.InvestmentLog;
import com.aequalis.fundraising.model.User;
import com.aequalis.fundraising.repository.InvestmentLogRepository;
import com.aequalis.fundraising.service.InvestmentLogService;

/**
 * @author anand
 *
 */
@Service
@Qualifier("investmentLogService")
@Transactional
public class InvestmentLogServiceImpl implements InvestmentLogService {

	@Autowired
	InvestmentLogRepository investmentLogRepository;
	
	@Override
	public void addInvestmentLog(InvestmentLog investmentLog) {
		investmentLogRepository.saveAndFlush(investmentLog);
	}

	@Override
	public List<InvestmentLog> findByReferencenumberAndCreator(String referencenumber, User creator) {
		return investmentLogRepository.findByReferencenumberAndCreator(referencenumber, creator);
	}

	@Override
	public List<InvestmentLog> findByReferencenumberAndRefundstatus(String referencenumber, Boolean refundstatus) {
		return investmentLogRepository.findByReferencenumberAndRefundstatus(referencenumber, refundstatus);
	}

	@Override
	public InvestmentLog findByInvestmentid(Long investmentid) {
		return investmentLogRepository.findByInvestmentid(investmentid);
	}

}
