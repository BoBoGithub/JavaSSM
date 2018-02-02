package com.migang.admin.entity;

import java.math.BigDecimal;

/**
 * 产品实体类
 *
 *@param addTime 2017-11-09
 *@param author     ChengBo
 */
public class Product {

	//自增id
	private int productId;
	
	//产品名称
	private String productName;
	
	//借款金额
	private BigDecimal borrowMoney;
	
	//借款期限
	private int borrowLimit;
	
	//日利率
	private BigDecimal dayRate;
	
	//服务费
	private BigDecimal serviceCharge;
	
	//状态
	private int status;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getBorrowMoney() {
		return borrowMoney;
	}

	public void setBorrowMoney(BigDecimal borrowMoney) {
		this.borrowMoney = borrowMoney;
	}

	public int getBorrowLimit() {
		return borrowLimit;
	}

	public void setBorrowLimit(int borrowLimit) {
		this.borrowLimit = borrowLimit;
	}

	public BigDecimal getDayRate() {
		return dayRate;
	}

	public void setDayRate(BigDecimal dayRate) {
		this.dayRate = dayRate;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Produce: "+productId+" "+productName+" "+borrowMoney;
	}
}
