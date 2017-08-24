/**
 * 
 */
package com.aequalis.fundraising.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aequalis.fundraising.blockchainapi.WebAPICall;
import com.aequalis.fundraising.dto.JobDTO;
import com.aequalis.fundraising.dto.MilestoneDTO;
import com.aequalis.fundraising.model.InvestmentLog;
import com.aequalis.fundraising.model.Type;
import com.aequalis.fundraising.model.User;
import com.aequalis.fundraising.service.InvestmentLogService;
import com.aequalis.fundraising.service.TypeService;
import com.aequalis.fundraising.service.UserService;

/**
 * @author anand
 *
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	InvestmentLogService investmentLogService;
	
	@Autowired(required = true)
	public HttpServletRequest request;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	public HttpSession getSession() {
		if (request != null) {
			return request.getSession();
		}
		return null;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String loginScreen(Model model) {
	    return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerScreen(Model model) {
	    return "register";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutScreen(Model model) {
		HttpSession session = getSession();
		session.removeAttribute("loginUser");
	    return "index";
	}
	
	private BigDecimal getMyBalance(String myAddress) {
		
		BigDecimal wei = new BigDecimal("1000000000000000000");
		BigDecimal myBlanace = new BigDecimal("0");
		String currentBalance = WebAPICall.getBalance(myAddress);
		
		if (currentBalance != null) {
			myBlanace = new BigDecimal(new BigInteger(currentBalance.substring(2, currentBalance.length()), 16));
			myBlanace = myBlanace.divide(wei);
		}
		
		return myBlanace;
	}
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView registerUser(Model model, HttpServletRequest httpServletRequest) {
		
		String userType = httpServletRequest.getParameter("type");
		String fullname = httpServletRequest.getParameter("fullname");
		String username = httpServletRequest.getParameter("username");
		String password = httpServletRequest.getParameter("password");
		String confirmPassword = httpServletRequest.getParameter("confirmPassword");
		String contactNumber = httpServletRequest.getParameter("contactNumber");
		String email = httpServletRequest.getParameter("email");
		String address = httpServletRequest.getParameter("address");
		String initialBalance = httpServletRequest.getParameter("initialBalance");
		if (initialBalance == null || initialBalance.trim().length() == 0) {
			initialBalance = "0";
		}
		Type type = typeService.findByName(userType);
		if (password.equals(confirmPassword)) {
			User availableUser = userService.findByUsername(username);
			if (availableUser == null) {
				Properties properties = new Properties();
				String coinbaseAddress = "";
				String passcode = "";
				try {
					properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("fundraising.properties"));
					coinbaseAddress = properties.getProperty("coinbaseAddress");
					passcode = properties.getProperty("passcode");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (type.getName().equalsIgnoreCase("investor")) {
					BigDecimal coinbaseBalance = getMyBalance(coinbaseAddress);
					if (coinbaseBalance.intValue() < Integer.parseInt(initialBalance)) {
						model.addAttribute("errormsg", "Insufficient balance in the coinbase, Please contact system Admin!");
						return new ModelAndView("redirect:/register");
					}
				}
				
				String bcAddress = WebAPICall.registerNewUser(password);
				if (bcAddress != null) {
					WebAPICall.unlockUser(bcAddress, password);
					
					if (type.getName().equalsIgnoreCase("investor")) {
						
						BigDecimal wei = new BigDecimal("1000000000000000000");
						BigDecimal myValue = new BigDecimal(initialBalance);
						BigDecimal etherValue = myValue.multiply(wei);
						String value = "0x" + etherValue.toBigInteger().toString(16);
						
						WebAPICall.unlockUser(coinbaseAddress, passcode);
						
						String result = WebAPICall.sendTransaction(coinbaseAddress, bcAddress, value);
						System.out.println("Initial Amount sent transaction ref: " + result);
					}
					
					User user = new User();
					user.setUsername(username);
					user.setFullname(fullname);
					user.setPassword(password);
					user.setContactnumber(contactNumber);
					user.setEmail(email);
					user.setAddress(address);
					user.setBcaddress(bcAddress);
					user.setType(type);
					userService.addUser(user);
					
					return new ModelAndView("redirect:/index");
				} else {
					model.addAttribute("errormsg", "Failed to create user account in blockchain, Please try again!");
					return new ModelAndView("redirect:/register");
				}
				
			} else {
				model.addAttribute("errormsg", "Username is not available, Please try again!");
				return new ModelAndView("redirect:/register");
			}
			
		} else {
			model.addAttribute("errormsg", "Password does not match, Please try again!");
			return new ModelAndView("redirect:/register");
		}
			
	}
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public ModelAndView loginUser(Model model, HttpServletRequest httpServletRequest) {
		Date loginTime = new Date();
		String userName = httpServletRequest.getParameter("userName");
		String password = httpServletRequest.getParameter("password");
		String userType = httpServletRequest.getParameter("type");
		
		Type type = typeService.findByName(userType);
		
		User user = userService.loginUser(userName, password);
		if (user != null) {
			if (type != null && user.getType().getTypeid() == type.getTypeid()) {
				HttpSession session = getSession();
				session.setAttribute("loginUser", user.getUserid());
				user.setLastLogin(user.getCurrentLogin());
				user.setCurrentLogin(loginTime);
				userService.addUser(user);
				
				if (type.getName().equalsIgnoreCase("owner")) {
					return new ModelAndView("redirect:/ownerhome");
				} else if (type.getName().equalsIgnoreCase("investor")) {
					return new ModelAndView("redirect:/investorhome");
				} else if (type.getName().equalsIgnoreCase("vendor")) {
					return new ModelAndView("redirect:/vendorhome");
				}
			} else {
				model.addAttribute("errormsg", "Invalid user group. Please try again.");
				return new ModelAndView("redirect:/");	
			}
		}
		
		model.addAttribute("errormsg", "Invalid user name or password. Please try again.");
		return new ModelAndView("redirect:/");	
		
	}
	
	
	@RequestMapping(value = "/vendorhome", method = RequestMethod.GET)
	public String vendorHomeScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			List<JobDTO> jobs = WebAPICall.myJobs(user.getBcaddress());
			model.addAttribute("jobs", jobs);
			
			return "vendorhome";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/investorhome", method = RequestMethod.GET)
	public String investorHomeScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			List<JobDTO> jobs = WebAPICall.listPublishedJobs(user.getBcaddress());
			model.addAttribute("jobs", jobs);
			
			return "investorhome";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/ownerhome", method = RequestMethod.GET)
	public String ownerHomeScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			List<JobDTO> jobs = WebAPICall.listJobsByCreator(user.getBcaddress());
			model.addAttribute("jobs", jobs);
			
			Type type = typeService.findByName("Vendor");
			
			List<User> vendors = userService.findByType(type);
			
			model.addAttribute("vendors", vendors);
			
			return "ownerhome";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/ownerprofile", method = RequestMethod.GET)
	public String ownerProfileScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			return "ownerprofile";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/vendorprofile", method = RequestMethod.GET)
	public String vendorProfileScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			return "vendorprofile";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/investorprofile", method = RequestMethod.GET)
	public String investorProfileScreen(Model model) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			return "investorprofile";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/viewproject", method = RequestMethod.GET)
	public String viewProject(Model model, HttpServletRequest request) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		String referenceNumber = request.getParameter("referenceNumber");
		
		if(userId != null && referenceNumber != null) {
			
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			JobDTO job = WebAPICall.getJobDetailByJobTitle(referenceNumber, user.getBcaddress());
			model.addAttribute("job", job);
			
			List<InvestmentLog> investmentLogs = investmentLogService.findByReferencenumberAndCreator(referenceNumber, user);
			
			model.addAttribute("investments", investmentLogs);
			
			return "viewproject";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/manageproject", method = RequestMethod.GET)
	public String manageProject(Model model, HttpServletRequest request) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		String referenceNumber = request.getParameter("referenceNumber");
		
		String vendor = request.getParameter("vendor");
		
		if(userId != null && referenceNumber != null && vendor != null) {
			
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			JobDTO job = WebAPICall.getJobDetailByJobTitle(referenceNumber, user.getBcaddress());
			model.addAttribute("job", job);
			
			model.addAttribute("referenceNumber", referenceNumber);
			
			model.addAttribute("vendor", vendor);
			
			List<MilestoneDTO> milestones = WebAPICall.listMilestone(referenceNumber);
			
			model.addAttribute("milestones", milestones);
			
			int percentage = WebAPICall.getCurrentPercentage(referenceNumber, vendor);
			
			String milestoneAction = "";
			
			if (percentage < 100) {
				milestoneAction = "<a href=\"newmilestone?referenceNumber="+referenceNumber+"&vendor="+vendor+"\" class=\"buttontype\" style=\"float:right;;margin-top: -5px\"> + New Milestone</a> ";
			}
			model.addAttribute("newMilestoneAction", milestoneAction);
			
			return "manageproject";
		}
		
		return "index";
	}
	
	
	@RequestMapping(value = "/managemyproject", method = RequestMethod.GET)
	public String manageMyProject(Model model, HttpServletRequest request) {
	    
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		String referenceNumber = request.getParameter("referenceNumber");
		
		if(userId != null && referenceNumber != null) {
			
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			model.addAttribute("referenceNumber", referenceNumber);
			
			List<MilestoneDTO> milestones = WebAPICall.listMilestone(referenceNumber);
			
			model.addAttribute("milestones", milestones);
			
			return "managemyproject";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/newmilestone", method = RequestMethod.GET)
	public String newMilestone(Model model, HttpServletRequest request) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			
			String referenceNumber = request.getParameter("referenceNumber");
			
			String vendor = request.getParameter("vendor");
			
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			model.addAttribute("referenceNumber", referenceNumber);
			
			model.addAttribute("vendor", vendor);
			
			return "newmilestone";
		} else {
			return "index";
		}
	}
	
	
	@RequestMapping(value = "/newproject", method = RequestMethod.GET)
	public String newProject(Model model, HttpServletRequest request) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			return "newproject";
		} else {
			return "index";
		}
	}
	
	@RequestMapping(value = "/investJob", method = RequestMethod.GET)
	public String investJob(Model model, HttpServletRequest request) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			String referenceNumber = request.getParameter("referenceNumber");
			String creator = request.getParameter("creator");
			
			JobDTO job = WebAPICall.getJobDetailByJobTitle(referenceNumber, creator);
			
			model.addAttribute("currentUser", user);
			
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			model.addAttribute("myBalance", myBlanace.setScale(2, BigDecimal.ROUND_UP));
			
			model.addAttribute("job", job);
			
			return "investjob";
			
		} else {
			return "index";
		}
	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	@RequestMapping(value = "/forceRefundInvestments", method = RequestMethod.GET)
	public ModelAndView forceRefundInvestments(Model model, HttpServletRequest httpServletRequest) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			String id = httpServletRequest.getParameter("id");
			String referenceNumber = httpServletRequest.getParameter("referenceNumber");
			if (id != null && referenceNumber != null) {
				Properties properties = new Properties();
				int refundPercentage = 0;
				int etherToTokenRate = 1;
				try {
					properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("fundraising.properties"));
					refundPercentage = Integer.parseInt(properties.getProperty("refundPercentage"));
					etherToTokenRate = Integer.parseInt(properties.getProperty("etherToTokenRate"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				BigDecimal wei = new BigDecimal("1000000000000000000");
				InvestmentLog investment = investmentLogService.findByInvestmentid(Long.parseLong(id));
				double refundAmount = Integer.parseInt(investment.getAmount()) / etherToTokenRate * refundPercentage / 100;
				BigDecimal myValue = new BigDecimal(refundAmount);
				BigDecimal etherValue = myValue.multiply(wei);
				String value = "0x" + etherValue.toBigInteger().toString(16);
					
				WebAPICall.unlockUser(investment.getCreator().getBcaddress(), investment.getCreator().getPassword());
					
				String result = WebAPICall.sendTransaction(investment.getCreator().getBcaddress(), investment.getBacker().getBcaddress(), value);
				if (result != null) {
					WebAPICall.forceRefund(referenceNumber, investment.getCreator().getBcaddress(), investment.getBacker().getBcaddress(), investment.getAmount());
					investment.setRefundstatus(true);
					investment.setRefundamount("" + round(refundAmount,2));
					investmentLogService.addInvestmentLog(investment);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return new ModelAndView("redirect:/viewproject?referenceNumber=" + referenceNumber);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	
	@RequestMapping(value = "/refundInvestments", method = RequestMethod.GET)
	public ModelAndView refundInvestments(Model model, HttpServletRequest httpServletRequest) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			String referenceNumber = httpServletRequest.getParameter("referenceNumber");
			if (referenceNumber != null) {
				BigDecimal wei = new BigDecimal("1000000000000000000");
				List<InvestmentLog> investments = investmentLogService.findByReferencenumberAndRefundstatus(referenceNumber, false);
				Boolean refundStatus = true;
				String creator = null;
				for (InvestmentLog investment : investments) {
					Properties properties = new Properties();
					int refundPercentage = 0;
					int etherToTokenRate = 1;
					try {
						properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("fundraising.properties"));
						refundPercentage = Integer.parseInt(properties.getProperty("refundPercentage"));
						etherToTokenRate = Integer.parseInt(properties.getProperty("etherToTokenRate"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					double refundAmount = Integer.parseInt(investment.getAmount()) / etherToTokenRate * refundPercentage / 100;
					BigDecimal myValue = new BigDecimal(refundAmount);
					BigDecimal etherValue = myValue.multiply(wei);
					String value = "0x" + etherValue.toBigInteger().toString(16);
					creator = investment.getCreator().getBcaddress();
					
					WebAPICall.unlockUser(investment.getCreator().getBcaddress(), investment.getCreator().getPassword());
					
					String result = WebAPICall.sendTransaction(investment.getCreator().getBcaddress(), investment.getBacker().getBcaddress(), value);
					if (result == null) {
						refundStatus = false;
					} else {
						investment.setRefundstatus(true);
						investment.setRefundamount("" + round(refundAmount,2));
						investmentLogService.addInvestmentLog(investment);
					}
				}
				if (refundStatus && creator != null) {
					WebAPICall.updateJobStatusByJobReferenceNumber(referenceNumber, creator, 4);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return new ModelAndView("redirect:/viewproject?referenceNumber=" + referenceNumber);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	
	@RequestMapping(value = "/assignVendor", method = RequestMethod.POST)
	public ModelAndView assignVendor(Model model, HttpServletRequest httpServletRequest) {
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		String vendorid = httpServletRequest.getParameter("vendor");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			
			User vendorUser = userService.findByUserid(Long.parseLong(vendorid));
			
			String address = WebAPICall.assignVendor(referenceNumber, user.getBcaddress(), vendorUser.getBcaddress());
			
			if (address != null) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new ModelAndView("redirect:/ownerhome");
			} else {
				model.addAttribute("errormsg", "Failed to assign vendor. Please try again.");
				return new ModelAndView("redirect:/ownerhome");
			}
			
		} else {
			return new ModelAndView("redirect:/");
		}
		
	}
	
	@RequestMapping(value = "/myFundInvestment", method = RequestMethod.POST)
	public ModelAndView myFundInvestment(Model model, HttpServletRequest httpServletRequest) {
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		String myfund = httpServletRequest.getParameter("myfund");
		String creatorAddress = httpServletRequest.getParameter("creatorAddress");
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			BigDecimal wei = new BigDecimal("1000000000000000000");
			BigDecimal myBlanace = getMyBalance(user.getBcaddress());
			int etherToTokenRate = 1;
			Properties properties = new Properties();
			try {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("fundraising.properties"));
				etherToTokenRate = Integer.parseInt(properties.getProperty("etherToTokenRate"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			BigDecimal myValue = new BigDecimal(Double.parseDouble(myfund) / etherToTokenRate);
			
			if(myBlanace.compareTo(myValue) > 0) {
				
				int availableToken = WebAPICall.getMyTokenBalanceByReferenceNumber(referenceNumber, creatorAddress, creatorAddress);
				
				if (availableToken >= Integer.parseInt(myfund)) {
					WebAPICall.unlockUser(user.getBcaddress(), user.getPassword());
					
					User creator = userService.findByBcaddress(creatorAddress);
					
					BigDecimal etherValue = myValue.multiply(wei);
					String value = "0x" + etherValue.toBigInteger().toString(16);
					
					String result = WebAPICall.sendTransaction(user.getBcaddress(), creatorAddress, value);
					if (result != null) {
						WebAPICall.buyProjectToken(referenceNumber, creatorAddress, user.getBcaddress(), myfund);
						//WebAPICall.sendFund(referenceNumber, creatorAddress, user.getBcaddress(), Integer.parseInt(myfund));
						InvestmentLog investmentLog = new InvestmentLog();
						investmentLog.setReferencenumber(referenceNumber);
						investmentLog.setCreator(creator);
						investmentLog.setBacker(user);
						investmentLog.setAmount(myfund);
						investmentLog.setTransactiondate(new Date());
						investmentLog.setRefundstatus(false);
						investmentLogService.addInvestmentLog(investmentLog);
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						return new ModelAndView("redirect:/investorhome");
					} else {
						model.addAttribute("errormsg", "Failed to send fund. Please try again.");
						return new ModelAndView("redirect:/investorhome");
					}
				} else {
					model.addAttribute("errormsg", "There is no sufficient tokens to send fund. Please try again.");
					return new ModelAndView("redirect:/investorhome");
					//return new ModelAndView("redirect:/investorhome?referenceNumber="+referenceNumber+"&creator="+creatorAddress, "model", model);
				}
			} else {
				model.addAttribute("errormsg", "You don't have sufficient fund to transfer. Please try again.");
				return new ModelAndView("redirect:/investorhome");
				//return new ModelAndView("redirect:/investorhome?referenceNumber="+referenceNumber+"&creator="+creatorAddress);
			}
			
		} else {
			return new ModelAndView("redirect:/");
		}
		
	}
	
	
	@RequestMapping(value = "/publishJob", method = RequestMethod.GET)
	public ModelAndView publishJob(Model model, HttpServletRequest httpServletRequest) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			String referenceNumber = httpServletRequest.getParameter("referenceNumber");
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			if (referenceNumber != null) {
				WebAPICall.publishJob(referenceNumber, user.getBcaddress());
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return new ModelAndView("redirect:/ownerhome");
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	
	@RequestMapping(value = "/completeMilestone", method = RequestMethod.GET)
	public ModelAndView completeMilestone(Model model, HttpServletRequest httpServletRequest) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		String indexId = httpServletRequest.getParameter("indexId");
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		
		if(userId != null && indexId != null && referenceNumber != null) {
			
			if (indexId != null) {
				String result = WebAPICall.updateMilestoneStatus(indexId, 1);
				if (result != null) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return new ModelAndView("redirect:/managemyproject?referenceNumber="+referenceNumber);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	
	
	@RequestMapping(value = "/payoutMilestone", method = RequestMethod.GET)
	public ModelAndView payoutMilestone(Model model, HttpServletRequest httpServletRequest) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		String indexId = httpServletRequest.getParameter("indexId");
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		String vendor = httpServletRequest.getParameter("vendor");
		
		if(userId != null && indexId != null && referenceNumber != null && vendor != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			if (indexId != null) {
//				WebAPICall.unlockUser(user.getBcaddress(), user.getPassword());
//					
//				JobDTO job = WebAPICall.getJobDetailByJobTitle(referenceNumber, user.getBcaddress());
//				BigDecimal wei = new BigDecimal("1000000000000000000");
//				int amount = job.getFundRequired();
//				
//				MilestoneDTO milestone = WebAPICall.getMilestoneDetail(Integer.parseInt(indexId));
//				
//				BigDecimal myValue = new BigDecimal(amount * milestone.getPaymentPercentage() / 100);
//				
//				BigDecimal etherValue = myValue.multiply(wei);
//				String value = "0x" + etherValue.toBigInteger().toString(16);
//				
//				String result = WebAPICall.sendTransaction(user.getBcaddress(), vendor, value);
//				
//				if (result != null) {
//					result = WebAPICall.updateMilestoneStatus(indexId, 3);
//					if (result != null) {
//
//						WebAPICall.updateJobStatusByJobReferenceNumber(referenceNumber, user.getBcaddress(), 6);
//						try {
//							Thread.sleep(3000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				} else {
//					model.addAttribute("errormsg", "Failed to send payout, Please try again!");
//					return new ModelAndView("redirect:/manageproject?referenceNumber="+referenceNumber + "&vendor=" + vendor);
//				}
				String result = WebAPICall.updateMilestoneStatus(indexId, 3);
				if (result != null) {

					WebAPICall.updateJobStatusByJobReferenceNumber(referenceNumber, user.getBcaddress(), 6);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return new ModelAndView("redirect:/manageproject?referenceNumber="+referenceNumber + "&vendor=" + vendor);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	@RequestMapping(value = "/reviewMilestone", method = RequestMethod.GET)
	public ModelAndView reviewAndPayMilestone(Model model, HttpServletRequest httpServletRequest) {
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		String indexId = httpServletRequest.getParameter("indexId");
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		String vendor = httpServletRequest.getParameter("vendor");
		
		if(userId != null && indexId != null && referenceNumber != null && vendor != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			if (indexId != null) {
				String result = WebAPICall.updateMilestoneStatus(indexId, 2);
				if (result != null) {
					Properties properties = new Properties();
					int vendorPercentage = 0;
					int etherToTokenRate = 1;
					try {
						properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("fundraising.properties"));
						vendorPercentage = Integer.parseInt(properties.getProperty("vendorPercentage"));
						etherToTokenRate = Integer.parseInt(properties.getProperty("etherToTokenRate"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					WebAPICall.unlockUser(user.getBcaddress(), user.getPassword());
					
					JobDTO job = WebAPICall.getJobDetailByJobTitle(referenceNumber, user.getBcaddress());
					BigDecimal wei = new BigDecimal("1000000000000000000");
					double amount = job.getFundRequired() / etherToTokenRate * vendorPercentage / 100;
					
					MilestoneDTO milestone = WebAPICall.getMilestoneDetail(Integer.parseInt(indexId));
					
					BigDecimal myValue = new BigDecimal(amount * milestone.getPaymentPercentage() / 100);
					
					BigDecimal etherValue = myValue.multiply(wei);
					String value = "0x" + etherValue.toBigInteger().toString(16);
					
					result = WebAPICall.sendTransaction(user.getBcaddress(), vendor, value);
					
					if (result != null) {
						result = WebAPICall.updateMilestoneStatus(indexId, 3);
						if (result != null) {
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(WebAPICall.isAllMilestoneCompleted(referenceNumber, vendor)) {
								WebAPICall.updateJobStatusByJobReferenceNumber(referenceNumber, user.getBcaddress(), 6);
							}
						}
						
					} else {
						model.addAttribute("errormsg", "Failed to send payout, Please try again!");
						return new ModelAndView("redirect:/manageproject?referenceNumber="+referenceNumber + "&vendor=" + vendor);
					}
				}
			}
			return new ModelAndView("redirect:/manageproject?referenceNumber="+referenceNumber + "&vendor=" + vendor);
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	@RequestMapping(value = "/addNewProject", method = RequestMethod.POST)
	public ModelAndView addNewProject(Model model, HttpServletRequest httpServletRequest) {
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		String jobTitle = httpServletRequest.getParameter("jobTitle");
		String fundRequired = httpServletRequest.getParameter("fundRequired");
		String description = httpServletRequest.getParameter("description");
		String expiryDate = httpServletRequest.getParameter("expiryDate");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			User user = userService.findByUserid(Long.parseLong(userId.toString()));
			if (!WebAPICall.checkAvailableJob(referenceNumber, user.getBcaddress())) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				 
				try {
					Date date = sdf.parse(expiryDate);
					expiryDate = Long.toString(date.getTime());
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				WebAPICall.createNewJob(referenceNumber, jobTitle, user.getBcaddress(), description, fundRequired, expiryDate);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new ModelAndView("redirect:/ownerhome");
				
			} else {
				model.addAttribute("errormsg", "Project with reference is already added, Please try again!");
				return new ModelAndView("redirect:/newproject");
			}
		} else {
			return new ModelAndView("redirect:/");
		}
	}
	
	@RequestMapping(value = "/addNewMilestone", method = RequestMethod.POST)
	public ModelAndView addNewMilestone(Model model, HttpServletRequest httpServletRequest) {
		String referenceNumber = httpServletRequest.getParameter("referenceNumber");
		String vendor = httpServletRequest.getParameter("vendor");
		String description = httpServletRequest.getParameter("description");
		String paymentPercentage = httpServletRequest.getParameter("paymentPercentage");
		String completionDate = httpServletRequest.getParameter("completionDate");
		
		HttpSession session = getSession();
		Object userId = session.getAttribute("loginUser");
		
		if(userId != null) {
			if (!WebAPICall.checkAvailableMilestone(referenceNumber, vendor, description)) {
				
				int percentage = WebAPICall.getCurrentPercentage(referenceNumber, vendor);
				
				percentage += Integer.parseInt(paymentPercentage);
				if (percentage <= 100) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					 
					try {
						Date date = sdf.parse(completionDate);
						completionDate = Long.toString(date.getTime());
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					
					String result = WebAPICall.createNewMilestone(referenceNumber, vendor, description, completionDate, paymentPercentage);
					if (result != null) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return new ModelAndView("redirect:/manageproject?referenceNumber=" + referenceNumber + "&vendor=" + vendor);
					} else {
						model.addAttribute("errormsg", "Failed to create new milestone, Please try again!");
						return new ModelAndView("redirect:/newmilestone?referenceNumber=" + referenceNumber + "&vendor=" + vendor);
					}
				} else {
					model.addAttribute("errormsg", "Payment percentages goes more than 100 for this project, Please try again!");
					return new ModelAndView("redirect:/newmilestone?referenceNumber=" + referenceNumber + "&vendor=" + vendor);
				}
			} else {
				model.addAttribute("errormsg", "Milestone with description is already added, Please try again!");
				return new ModelAndView("redirect:/newmilestone?referenceNumber=" + referenceNumber + "&vendor=" + vendor);
			}
		} else {
			return new ModelAndView("redirect:/");
		}
	}
}
