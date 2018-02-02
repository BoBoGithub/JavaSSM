package com.migang.admin.entity;

/**
 * 用户银行卡信息实体类
 *
 *@param addTime 2017-09-26
 *@param author    ChengBo
 */
public class UserBank {

	//设置自增id
	private int bankid;
	
	//用户uid
	private long uid;
	
	//银行卡号
	private String bankNum;
	
	//绑卡预留手机号
	private String mobile;
	
	//银行名称
	private String bankName;
	
	//开户行省份
	private int bankProvince;
	
	//开户行城市
	private int bankCity;
	
	//银行卡类型
	private int cardType;
	
	//银行卡状态
	private int status;
	
	//添加时间
	private int ctime;

	public int getBankid() {
		return bankid;
	}

	public void setBankid(int bankid) {
		this.bankid = bankid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(int bankProvince) {
		this.bankProvince = bankProvince;
	}

	public int getBankCity() {
		return bankCity;
	}

	public void setBankCity(int bankCity) {
		this.bankCity = bankCity;
	}

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCtime() {
		return ctime;
	}

	public void setCtime(int ctime) {
		this.ctime = ctime;
	}

	@Override
	public String toString() {
		return "UserBank: "+uid+" "+bankName+" "+bankNum+" "+mobile;
	}
	
}
