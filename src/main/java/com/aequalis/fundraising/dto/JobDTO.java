/**
 * 
 */
package com.aequalis.fundraising.dto;

/**
 * @author anand
 *
 */
public class JobDTO {
	private String referenceNumber;
	private String title;
	private String description;
	private String creator;
	private String vendor;
	private String targetDate;
	private String appliedDate;
	private int fundRequired;
	private int achievedAmount;
	private int status;
	private int myToken;
	
	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}
	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	/**
	 * @return the appliedDate
	 */
	public String getAppliedDate() {
		return appliedDate;
	}
	/**
	 * @param appliedDate the appliedDate to set
	 */
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}
	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	/**
	 * @return the targetDate
	 */
	public String getTargetDate() {
		return targetDate;
	}
	/**
	 * @param targetDate the targetDate to set
	 */
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	/**
	 * @return the fundRequired
	 */
	public int getFundRequired() {
		return fundRequired;
	}
	/**
	 * @param fundRequired the fundRequired to set
	 */
	public void setFundRequired(int fundRequired) {
		this.fundRequired = fundRequired;
	}
	/**
	 * @return the achievedAmount
	 */
	public int getAchievedAmount() {
		return achievedAmount;
	}
	/**
	 * @param achievedAmount the achievedAmount to set
	 */
	public void setAchievedAmount(int achievedAmount) {
		this.achievedAmount = achievedAmount;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the myToken
	 */
	public int getMyToken() {
		return myToken;
	}
	/**
	 * @param myToken the myToken to set
	 */
	public void setMyToken(int myToken) {
		this.myToken = myToken;
	}
}
