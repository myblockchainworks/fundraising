/**
 * 
 */
package com.aequalis.fundraising.dto;

/**
 * @author anand
 *
 */
public class MilestoneDTO {
	private String referenceNumber;
	private String description;
	private String vendor;
	private String completionDate;
	private String completedDate;
	private int currentIndex;
	private int paymentPercentage;
	private int status;
	
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
	 * @return the completionDate
	 */
	public String getCompletionDate() {
		return completionDate;
	}
	/**
	 * @param completionDate the completionDate to set
	 */
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	/**
	 * @return the completedDate
	 */
	public String getCompletedDate() {
		return completedDate;
	}
	/**
	 * @param completedDate the completedDate to set
	 */
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	/**
	 * @return the paymentPercentage
	 */
	public int getPaymentPercentage() {
		return paymentPercentage;
	}
	/**
	 * @param paymentPercentage the paymentPercentage to set
	 */
	public void setPaymentPercentage(int paymentPercentage) {
		this.paymentPercentage = paymentPercentage;
	}
	/**
	 * @return the currentIndex
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
	/**
	 * @param currentIndex the currentIndex to set
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
}
