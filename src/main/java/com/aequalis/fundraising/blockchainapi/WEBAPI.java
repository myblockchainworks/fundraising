/**
 * 
 */
package com.aequalis.fundraising.blockchainapi;

/**
 * @author anand
 *
 */
public interface WEBAPI {
	
	
	// Server 104.236.31.128
	
	//static String coinbaseAddress = "0x7d6473274474ba11c432041bbc7e259a09496294";
	
	//static String BASE = "http://10.0.0.22:3001/";
	//static String BLOCKCHAIN_URL = "http://10.0.0.22:8545";
	
	//static Integer refundPercentage = 90;
	
	//static String passcode = "123456";
	
//	static String coinbaseAddress = "0xf6e87fe42a457bd7e77df22e67bc1428b60e6159";
//	
//	static String BASE = "http://192.168.2.79:3001/";
//	static String BLOCKCHAIN_URL = "http://192.168.2.79:8545";
	
	static String REGISTER_DATA = "{ \"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"personal_newAccount\", \"params\": [\"PARAM1\"] }";
	
	static String UNLOCK_DATA = "{\"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"personal_unlockAccount\", \"params\":  [ \"PARAM1\", \"PARAM2\", 0 ] }";
	
	static String SEND_TRANSACTION = "{\"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"eth_sendTransaction\", \"params\": [{ \"from\": \"PARAM1\", \"to\": \"PARAM2\", \"gas\": \"0x76c0\", \"gasPrice\": \"0x9184e72a000\", \"value\": \"PARAM3\" }] }";
		
	static String GET_BALANCE = "{\"id\":\"1\", \"jsonrpc\":\"2.0\", \"method\": \"eth_getBalance\", \"params\": [\"PARAM1\", \"latest\"] }";
	
	static String GETJOBCOUNT = "getJobCount";
	
	static String GETJOBCOUNTBYCREATOR = "getJobCountByCreator";
	
	static String GETJOBCOUNTBYCREATOR_DATA = "{ \"_creator\":PARAM1 }";
	
	static String CREATENEWJOB = "createNewJob";
	
	static String CREATENEWJOB_DATA = "{ \"_referenceNumber\":\"PARAM1\", \"_jobTitle\":\"PARAM2\", \"_creator\":\"PARAM3\", \"_description\":\"PARAM4\", \"_fundRequired\":PARAM5, \"_targetDate\":PARAM6 }";

	static String GETJOBDETAIL = "getJobDetail";
	
	static String GETJOBVENDOR = "getJobVendor";
	
	static String GETJOBDETAIL_DATA = "{ \"_currentIndex\":PARAM1 }";
	
	static String GETJOBDETAILBYJOBTITLE = "getJobDetailByJobTitle";
	
	static String PUBLISHJOB = "publishJob";
	
	static String CHECKAVAILABLEJOB = "checkAvailableJob";
	
	static String PUBLISHJOB_DATA = "{ \"_referenceNumber\":\"PARAM1\", \"_creator\":\"PARAM2\"}";
	
	static String SENDFUND = "sendFund";
	
	static String FORCEREFUNDFUND = "forceRefundFund";
	
	static String BUYPROJECTTOKEN = "buyProjectToken";
	
	static String SENDFUND_DATA = "{ \"_referenceNumber\":\"PARAM1\", \"_creator\":\"PARAM2\", \"_backer\":\"PARAM3\", \"amount\":\"PARAM4\"}";
	
	static String TRIGGERSTATUS = "triggerStatus";
	
	static String UPDATEJOBSTATUS = "updateJobStatus";
	
	static String UPDATEJOBSTATUS_DATA = "{ \"_currentIndex\":PARAM1, \"_statusIndex\":PARAM2 }";
	
	static String UPDATEJOBSTATUSBYJOBREFERENCENUMBER = "updateJobStatusByJobReferenceNumber";
	
	static String UPDATEJOBSTATUSBYJOBREFERENCENUMBER_DATE = "{ \"_referenceNumber\":\"PARAM1\", \"_creator\":\"PARAM2\", \"_statusIndex\":PARAM3}";
	
	static String GETMILESTONECOUNT = "getMilestoneCount";
	
	static String GETMILESTONEDETAIL = "getMilestoneDetail";
	
	static String CREATENEWMILESTONE = "createMilestone";
	
	static String CREATENEWMILESTONE_DATA = "{ \"_referenceNumber\":\"PARAM1\", \"_vendor\":\"PARAM2\", \"_description\":\"PARAM3\", \"_completionDate\":PARAM4, \"_paymentPercentage\":PARAM5 }";

	static String ASSIGNVENDOR = "assignVendor";
	
	static String ASSIGNVENDOR_DATA = "{ \"_referenceNumber\":\"PARAM1\", \"_creator\":\"PARAM2\", \"_vendor\":\"PARAM3\"}";
	
	static String UPDATEMILESTONESTATUS = "updateMilestoneStatus";
	
	static String GETMYTOKENBALANCEBYREFERENCENUMBER = "getMyTokenBalanceByReferenceNumber";
	
	static String GETMYTOKENBALANCEBYREFERENCENUMBER_DATA = "{ \"_referenceNumber\":\"PARAM1\", \"_creator\":\"PARAM2\", \"_backer\":\"PARAM3\"}";
	
	static String GETMYTOKENBALANCE = "getMyTokenBalance";
	
	static String GETMYTOKENBALANCE_DATA = "{ \"_currentIndex\":PARAM1, \"_backer\":\"PARAM2\"}";
	
}