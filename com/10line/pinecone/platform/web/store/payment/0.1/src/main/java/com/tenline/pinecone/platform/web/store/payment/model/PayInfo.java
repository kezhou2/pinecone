/**
 * 
 */
package com.tenline.pinecone.platform.web.store.payment.model;

/**
 * pay info class
 * @author WongYQ
 *
 */
public class PayInfo {
	/**
	 * userID
	 */
	private String userID;
	/**
	 * userName
	 */
	private String userName;
	/**
	 * userAccountID
	 */
	private String userAccountID;
	/**
	 * userAccountID
	 */
	private String userAccountBankName;
	/**
	 * userPhone
	 */
	private String userPhone;
	/**
	 * payNumber
	 */
	private int payNumber;
	/**
	 * payInfo
	 */
	private String payInfo;
	/**
	 */
	public PayInfo() {
		
	}
	/**
	 * @param userAccountID
	 * @param payNumber
	 */
	public PayInfo(String userAccountID, int payNumber) {
		this("", "", userAccountID, "",  "",  payNumber,"");
	}
	/*
	/**
	 * @param userName
	 * @param userAccountID
	 * @param userAccountBankName
	 * @param payNumber
	 */
	public PayInfo(String userName, String userAccountID,
			String userAccountBankName, int payNumber) {
		this("", userName, userAccountID, userAccountBankName,  "",  payNumber,"");
	}
	/**
	 * @param userID
	 * @param userName
	 * @param userAccountID
	 * @param userAccountBankName
	 * @param userPhone
	 * @param payNumber
	 * @param payInfo
	 */
	public PayInfo(String userID, String userName, String userAccountID,
			String userAccountBankName, String userPhone, int payNumber,
			String payInfo) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userAccountID = userAccountID;
		this.userAccountBankName = userAccountBankName;
		this.userPhone = userPhone;
		this.payNumber = payNumber;
		this.payInfo = payInfo;
	}
	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userAccountID
	 */
	public String getUserAccountID() {
		return userAccountID;
	}
	/**
	 * @param userAccountID the userAccountID to set
	 */
	public void setUserAccountID(String userAccountID) {
		this.userAccountID = userAccountID;
	}
	/**
	 * @return the userAccountBankName
	 */
	public String getUserAccountBankName() {
		return userAccountBankName;
	}
	/**
	 * @param userAccountBankName the userAccountBankName to set
	 */
	public void setUserAccountBankName(String userAccountBankName) {
		this.userAccountBankName = userAccountBankName;
	}
	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}
	/**
	 * @param userPhone the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * @return the payNumber
	 */
	public int getPayNumber() {
		return payNumber;
	}
	/**
	 * @param payNumber the payNumber to set
	 */
	public void setPayNumber(int payNumber) {
		this.payNumber = payNumber;
	}
	/**
	 * @return the payInfo
	 */
	public String getPayInfo() {
		return payInfo;
	}
	/**
	 * @param payInfo the payInfo to set
	 */
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	
}
