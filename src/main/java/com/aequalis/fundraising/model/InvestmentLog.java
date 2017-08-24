/**
 * 
 */
package com.aequalis.fundraising.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author anand
 *
 */
@Entity
@Table(name="investments")
public class InvestmentLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long investmentid;

	@Column(name = "referencenumber")
	private String referencenumber;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creatorid")
	private User creator;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backerid")
	private User backer;
	
	@Column(name = "amount")
	private String amount;
	
	@Column(name="transactiondate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactiondate;
	
	@Column(name = "refundstatus")
	private Boolean refundstatus;
	
	@Column(name = "refundamount")
	private String refundamount;

	/**
	 * @return the investmentid
	 */
	public Long getInvestmentid() {
		return investmentid;
	}

	/**
	 * @param investmentid the investmentid to set
	 */
	public void setInvestmentid(Long investmentid) {
		this.investmentid = investmentid;
	}

	/**
	 * @return the referencenumber
	 */
	public String getReferencenumber() {
		return referencenumber;
	}

	/**
	 * @param referencenumber the referencenumber to set
	 */
	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the backer
	 */
	public User getBacker() {
		return backer;
	}

	/**
	 * @param backer the backer to set
	 */
	public void setBacker(User backer) {
		this.backer = backer;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactiondate
	 */
	public Date getTransactiondate() {
		return transactiondate;
	}

	/**
	 * @param transactiondate the transactiondate to set
	 */
	public void setTransactiondate(Date transactiondate) {
		this.transactiondate = transactiondate;
	}
	
	/**
	 * @return the refundstatus
	 */
	public Boolean getRefundstatus() {
		return refundstatus;
	}

	/**
	 * @param refundstatus the refundstatus to set
	 */
	public void setRefundstatus(Boolean refundstatus) {
		this.refundstatus = refundstatus;
	}

	/**
	 * @return the refundamount
	 */
	public String getRefundamount() {
		return refundamount;
	}

	/**
	 * @param refundamount the refundamount to set
	 */
	public void setRefundamount(String refundamount) {
		this.refundamount = refundamount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvestmentLog [investmentid=" + investmentid + ", referencenumber=" + referencenumber + ", creator="
				+ creator + ", backer=" + backer + ", amount=" + amount + ", transactiondate=" + transactiondate
				+ ", refundstatus=" + refundstatus + ", refundamount=" + refundamount + "]";
	}

}
