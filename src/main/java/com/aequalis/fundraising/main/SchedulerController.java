/**
 * 
 */
package com.aequalis.fundraising.main;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.aequalis.fundraising.blockchainapi.WebAPICall;
import com.aequalis.fundraising.dto.JobDTO;
import com.aequalis.fundraising.service.InvestmentLogService;

/**
 * @author anand
 *
 */
public class SchedulerController {
	
	@Autowired
	InvestmentLogService investmentLogService;

	@Scheduled(fixedDelay = 3600000)
    public void demoServiceMethod()
    {
		System.out.println("Triggered Alert at :: "+ new Date());
		//WebAPICall.triggerStatus();
		WebAPICall.setProperties();
		List<JobDTO> jobs = WebAPICall.listJobs();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getStatus() == 0 || jobs.get(i).getStatus() == 1) {
				if (jobs.get(i).getAchievedAmount() >= jobs.get(i).getFundRequired()) {
					WebAPICall.updateJobStatus(i, 2);
				} else {
					String date = jobs.get(i).getTargetDate();
					long milliSeconds= Long.parseLong(date);
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(milliSeconds);
					Calendar nowCalender = Calendar.getInstance();
					if (nowCalender.getTimeInMillis() > calendar.getTimeInMillis()) {
						WebAPICall.updateJobStatus(i, 3);
					}
				}
			} /*else if (jobs.get(i).getStatus() == 3) {
				BigDecimal wei = new BigDecimal("1000000000000000000");
				List<InvestmentLog> investments = investmentLogService.findByReferencenumberAndRefundstatus(jobs.get(i).getReferenceNumber(), false);
				Boolean refundStatus = true;
				for (InvestmentLog investment : investments) {
					BigDecimal myValue = new BigDecimal(investment.getAmount());
					BigDecimal etherValue = myValue.multiply(wei);
					String value = "0x" + etherValue.toBigInteger().toString(16);
					
					String result = WebAPICall.sendTransaction(investment.getCreator().getBcaddress(), investment.getBacker().getBcaddress(), value);
					if (result == null) {
						refundStatus = false;
					} else {
						investment.setRefundstatus(true);
						investmentLogService.addInvestmentLog(investment);
					}
				}
				if (refundStatus) {
					WebAPICall.updateJobStatus(i, 4);
				}
			}*/
			
		}
    }
}
