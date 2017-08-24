/**
 * 
 */
package com.aequalis.fundraising.blockchainapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;

import com.aequalis.fundraising.dto.JobDTO;
import com.aequalis.fundraising.dto.MilestoneDTO;

/**
 * @author anand
 *
 */
public class WebAPICall {
	
	public static String BLOCKCHAIN_BASE = "";
	public static String BLOCKCHAIN_URL = "";
	public static int etherToTokenRate = 0;
	public static int vendorPercentage = 0;
	
	public static void setProperties() {
		if (BLOCKCHAIN_BASE.equals("")) {
			Properties properties = new Properties();
			try {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("fundraising.properties"));
				BLOCKCHAIN_BASE = properties.getProperty("BLOCKCHAIN_BASE");
				BLOCKCHAIN_URL = properties.getProperty("BLOCKCHAIN_URL");
				etherToTokenRate = Integer.parseInt(properties.getProperty("etherToTokenRate"));
				vendorPercentage = Integer.parseInt(properties.getProperty("vendorPercentage"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String registerNewUser(String passcode) {
		String bcAddress = null;
		try {
			URL url = new URL(BLOCKCHAIN_URL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.REGISTER_DATA.replace("PARAM1", passcode);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("result"))
					bcAddress = json.getString("result");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bcAddress;
	}
	
	public static Boolean unlockUser(String bcAddress, String passcode) {
		Boolean result = false;
		try {
			URL url = new URL(BLOCKCHAIN_URL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.UNLOCK_DATA.replace("PARAM1", bcAddress).replace("PARAM2", passcode);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("result"))
					result = json.getBoolean("result");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String sendTransaction(String fromAddres, String toAddress, String amount) {
		String result = null;
		try {
			URL url = new URL(BLOCKCHAIN_URL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.SEND_TRANSACTION.replace("PARAM1", fromAddres).replace("PARAM2", toAddress).replace("PARAM3", amount);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("result"))
					result = json.getString("result");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getBalance(String myAddress) {
		String result = null;
		try {
			URL url = new URL(BLOCKCHAIN_URL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GET_BALANCE.replace("PARAM1", myAddress);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("result"))
					result = json.getString("result");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int getJobCount() {
		int result = 0;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETJOBCOUNT);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("GET");
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("jobCount"))
					result = json.getInt("jobCount");
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int getMilestoneCount() {
		int result = 0;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETMILESTONECOUNT);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("GET");
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("milestoneCount"))
					result = json.getInt("milestoneCount");
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int getJobCountByCreator(String creator) {
		int result = 0;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETJOBCOUNTBYCREATOR);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GETJOBCOUNTBYCREATOR_DATA.replace("PARAM1", creator);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("jobCount"))
					result = json.getInt("jobCount");
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String createNewJob(String referenceNumber, String jobTitle, String creator, String description, String fundRequired, String targetDate) {
		String transactionAddress = "0x12345";
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.CREATENEWJOB);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.CREATENEWJOB_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", jobTitle).replace("PARAM3", creator).replace("PARAM4", description).replace("PARAM5", fundRequired).replace("PARAM6", targetDate);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String createNewMilestone(String referenceNumber, String vendor, String description, String completionDate, String paymentPercentage) {
		String transactionAddress = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.CREATENEWMILESTONE);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.CREATENEWMILESTONE_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", vendor).replace("PARAM3", description).replace("PARAM4", completionDate).replace("PARAM5", paymentPercentage);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static MilestoneDTO getMilestoneDetail(int currentIndex) {
		MilestoneDTO milestone = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETMILESTONEDETAIL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GETJOBDETAIL_DATA.replace("PARAM1", "" + currentIndex);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("ReferenceNumber")) {
					milestone = new MilestoneDTO();
					milestone.setReferenceNumber(json.getString("ReferenceNumber"));
					milestone.setVendor(json.getString("Vendor"));
					milestone.setDescription(json.getString("Description"));
					milestone.setCompletionDate(json.getString("CompletionDate"));
					milestone.setCompletedDate(json.getString("CompletedDate"));
					milestone.setPaymentPercentage(Integer.parseInt(json.getString("PaymentPercentage")));
					milestone.setStatus(Integer.parseInt(json.getString("Status")));
					milestone.setCurrentIndex(currentIndex);
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return milestone;
	}
	
	
	
	public static String getJobVendor(int currentIndex) {
		String vendor = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETJOBVENDOR);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GETJOBDETAIL_DATA.replace("PARAM1", "" + currentIndex);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("Vendor")) {
					vendor = json.getString("Vendor");
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vendor;
	}
	
	public static JobDTO getJobDetail(int currentIndex) {
		JobDTO job = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETJOBDETAIL);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GETJOBDETAIL_DATA.replace("PARAM1", "" + currentIndex);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("JobInfo")) {
					job = new JobDTO();
					String jobInfo = json.getString("JobInfo");
					String[] arrInfo = jobInfo.split(":::");
					job.setReferenceNumber(arrInfo[0]);
					job.setTitle(arrInfo[1]);
					job.setDescription(arrInfo[2]);
					job.setCreator(json.getString("Creator"));
					job.setTargetDate(json.getString("TargetDate"));
					job.setAppliedDate(json.getString("AppliedDate"));
					job.setFundRequired(Integer.parseInt(json.getString("FundRequired")));
					job.setAchievedAmount(Integer.parseInt(json.getString("AchievedAmount")));
					job.setStatus(Integer.parseInt(json.getString("Status")));
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return job;
	}
	
	public static JobDTO getJobDetailByJobTitle(String referenceNumber, String creator) {
		JobDTO job = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETJOBDETAILBYJOBTITLE);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.PUBLISHJOB_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("Title")) {
					job = new JobDTO();
					job.setReferenceNumber(referenceNumber);
					job.setTitle(json.getString("Title"));
					job.setDescription(json.getString("Description"));
					job.setCreator(creator);
					job.setTargetDate(json.getString("TargetDate"));
					job.setAppliedDate(json.getString("AppliedDate"));
					job.setFundRequired(Integer.parseInt(json.getString("FundRequired")));
					job.setAchievedAmount(Integer.parseInt(json.getString("AchievedAmount")));
					job.setStatus(Integer.parseInt(json.getString("Status")));
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return job;
	}
	
	public static int getMyTokenBalanceByReferenceNumber(String referenceNumber, String creator, String backer) {
		int balance = 0;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETMYTOKENBALANCEBYREFERENCENUMBER);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GETMYTOKENBALANCEBYREFERENCENUMBER_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator).replace("PARAM3", backer);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("MyTokenBalance")) {
					balance = json.getInt("MyTokenBalance");
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return balance;
	}
	
	public static int getMyTokenBalance(int currentIndex, String backer) {
		int balance = 0;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.GETMYTOKENBALANCE);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.GETMYTOKENBALANCE_DATA.replace("PARAM1", "" + currentIndex).replace("PARAM2", backer);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("MyTokenBalance")) {
					balance = json.getInt("MyTokenBalance");
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return balance;
	}
	
	public static String publishJob(String referenceNumber, String creator) {
		String transactionAddress = "0x12345";
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.PUBLISHJOB);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.PUBLISHJOB_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String assignVendor(String referenceNumber, String creator, String vendor) {
		String transactionAddress = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.ASSIGNVENDOR);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.ASSIGNVENDOR_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator).replace("PARAM3", vendor);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static boolean checkAvailableJob(String referenceNumber, String creator) {
		boolean result = false;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.CHECKAVAILABLEJOB);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.PUBLISHJOB_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				JSONObject json = new JSONObject(sb.toString());
				if(json.has("isAvailable"))
					result = json.getBoolean("isAvailable");
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String sendFund(String referenceNumber, String creator, String backer, int amount) {
		String transactionAddress = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.SENDFUND);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.SENDFUND_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator).replace("PARAM3", backer).replace("PARAM4", "" + amount);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String forceRefund(String referenceNumber, String creator, String backer, String amount) {
		String transactionAddress = "0x12345";
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.FORCEREFUNDFUND);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.SENDFUND_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator).replace("PARAM3", backer).replace("PARAM4", amount);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String buyProjectToken(String referenceNumber, String creator, String backer, String amount) {
		String transactionAddress = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.BUYPROJECTTOKEN);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.SENDFUND_DATA.replace("PARAM1", referenceNumber).replace("PARAM2", creator).replace("PARAM3", backer).replace("PARAM4", amount);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String updateJobStatus(int currentIndex, int statusIndex) {
		String transactionAddress = "0x12345";
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.UPDATEJOBSTATUS);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.UPDATEJOBSTATUS_DATA.replace("PARAM1", "" + currentIndex).replace("PARAM2", "" + statusIndex);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String updateJobStatusByJobReferenceNumber(String referenceNumber, String creator, int statusIndex) {
		String transactionAddress = "0x12345";
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.UPDATEJOBSTATUSBYJOBREFERENCENUMBER);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.UPDATEJOBSTATUSBYJOBREFERENCENUMBER_DATE.replace("PARAM1", referenceNumber).replace("PARAM2", creator).replace("PARAM3", "" + statusIndex);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String updateMilestoneStatus(String currentIndex, int statusIndex) {
		String transactionAddress = null;
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.UPDATEMILESTONESTATUS);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type","application/json");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			String data = WEBAPI.UPDATEJOBSTATUS_DATA.replace("PARAM1", currentIndex).replace("PARAM2", "" + statusIndex);
			out.write(data);
			out.close();
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	public static String triggerStatus() {
		String transactionAddress = "0x12345";
		try {
			URL url = new URL(BLOCKCHAIN_BASE + WEBAPI.TRIGGERSTATUS);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
		  
			BufferedReader br;
			if (200 <= httpCon.getResponseCode() && httpCon.getResponseCode() <= 299) {
				br = new BufferedReader(new InputStreamReader((httpCon.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				
				transactionAddress = sb.toString();
			} else {
				System.out.println(httpCon.getResponseCode() + " " + httpCon.getResponseMessage());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return transactionAddress;
	}
	
	
	public static List<JobDTO> listJobs() {
		List<JobDTO> jobs = new ArrayList<JobDTO>();
		JobDTO job;
		
		int totalJobs = getJobCount();
		
		for (int index = 0; index < totalJobs; index++) {
			job = getJobDetail(index);
			job.setVendor(getJobVendor(index));
			jobs.add(job);
		}
		
		return jobs;
	}
	
	public static List<MilestoneDTO> listMilestone(String referenceNumber) {
		List<MilestoneDTO> milestones = new ArrayList<MilestoneDTO>();
		MilestoneDTO milestone;
		
		int totalMilestones = getMilestoneCount();
		
		for (int index = 0; index < totalMilestones; index++) {
			milestone = getMilestoneDetail(index);
			if (milestone.getReferenceNumber().equalsIgnoreCase(referenceNumber))
				milestones.add(milestone);
		}
		
		return milestones;
	}
	
	public static boolean checkAvailableMilestone(String referenceNumber, String vendor, String description) {
		MilestoneDTO milestone;
		boolean result = false;
		int totalMilestones = getMilestoneCount();
		
		for (int index = 0; index < totalMilestones; index++) {
			milestone = getMilestoneDetail(index);
			if (milestone.getReferenceNumber().equalsIgnoreCase(referenceNumber) && milestone.getVendor().equals(vendor) && milestone.getDescription().equalsIgnoreCase(description))
				result = true;
		}
		
		return result;
	}
	
	public static int getCurrentPercentage(String referenceNumber, String vendor) {
		int currentPercentage = 0;
		int totalMilestones = getMilestoneCount();
		MilestoneDTO milestone;
		for (int index = 0; index < totalMilestones; index++) {
			milestone = getMilestoneDetail(index);
			if (milestone.getReferenceNumber().equalsIgnoreCase(referenceNumber) && milestone.getVendor().equals(vendor))
				currentPercentage += milestone.getPaymentPercentage();
		}
		
		return currentPercentage;
	}
	
	public static boolean isAllMilestoneCompleted(String referenceNumber, String vendor) {
		boolean result = true;
		
		int totalMilestones = getMilestoneCount();
		MilestoneDTO milestone;
		for (int index = 0; index < totalMilestones; index++) {
			milestone = getMilestoneDetail(index);
			if (milestone.getReferenceNumber().equalsIgnoreCase(referenceNumber) && milestone.getVendor().equals(vendor) && milestone.getStatus() != 3){
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	public static List<JobDTO> listJobsByCreator(String creator) {
		List<JobDTO> jobs = new ArrayList<JobDTO>();
		JobDTO job;
		
		int totalJobs = getJobCount();
		
		for (int index = 0; index < totalJobs; index++) {
			job = getJobDetail(index);
			if (job.getCreator().equalsIgnoreCase(creator)) {
				job.setVendor(getJobVendor(index));
				job.setMyToken(getMyTokenBalance(index, creator));
				jobs.add(job);
			}
				
		}
		
		return jobs;
	}
	
	public static List<JobDTO> listPublishedJobs(String backer) {
		List<JobDTO> jobs = new ArrayList<JobDTO>();
		JobDTO job;
		
		int totalJobs = getJobCount();
		
		for (int index = 0; index < totalJobs; index++) {
			job = getJobDetail(index);
			job.setMyToken(getMyTokenBalance(index, backer));
			if ((job.getStatus() == 1 || job.getMyToken() > 0) && !job.getCreator().equalsIgnoreCase(backer)) {
				job.setVendor(getJobVendor(index));
				jobs.add(job);
			}
		}
		
		return jobs;
	}
	
	public static List<JobDTO> myJobs(String vendor) {
		List<JobDTO> jobs = new ArrayList<JobDTO>();
		JobDTO job;
		
		int totalJobs = getJobCount();
		
		for (int index = 0; index < totalJobs; index++) {
			job = getJobDetail(index);
			job.setVendor(getJobVendor(index));
			if ((job.getStatus() == 5 || job.getStatus() == 6) && job.getVendor().equalsIgnoreCase(vendor)) {
				job.setMyToken(getMyTokenBalance(index, vendor));
				job.setFundRequired((int) (job.getFundRequired() / etherToTokenRate * vendorPercentage / 100)); 
				jobs.add(job);
			}
		}
		
		return jobs;
	}
	
}
