package com.migang.admin.entity;

import java.math.BigDecimal;

/**
 * 还款记录表
 *
 *@param addTime 2017-11-03
 *@param author     ChengBo
 */
public class RepayRecord {
	
	//设置自增id
	private int repayId;
	
	//设置借款记录id
	private int brecordId;
	
	//设置用户uid
	private int uid;
	
	//设置还款金额
	private BigDecimal repayMoney;
	
	//设置还款类型
	private int repayType;
	
	//设置还款时间
	private int ctime;
	
	//还款备注
	private String remark;

	public int getRepayId() {
		return repayId;
	}

	public void setRepayId(int repayId) {
		this.repayId = repayId;
	}

	public int getBrecordId() {
		return brecordId;
	}

	public void setBrecordId(int brecordId) {
		this.brecordId = brecordId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public BigDecimal getRepayMoney() {
		return repayMoney;
	}

	public void setRepayMoney(BigDecimal repayMoney) {
		this.repayMoney = repayMoney;
	}

	public int getRepayType() {
		return repayType;
	}

	public void setRepayType(int repayType) {
		this.repayType = repayType;
	}

	public int getCtime() {
		return ctime;
	}

	public void setCtime(int ctime) {
		this.ctime = ctime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "RepayRecord: "+ brecordId+" "+uid+" "+repayMoney+" "+ctime;
	}
}
