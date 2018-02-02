package com.migang.admin.entity;

import java.math.BigDecimal;

/**
 * 借款记录信息实体类
 *
 *@param addTime 2017-09-27
 *@param author    ChengBo
 */
public class BorrowRecord {

	//设置自增id
	private int brecordId;
	
	//设置用户uid
	private long uid;
	
	//设置借款金额
	private BigDecimal borrowMoney;
	
	//设置借款期限
	private int borrowLimit;
	
	//设置借款利息
	private BigDecimal borrowInterest;

	//设置服务费
	private BigDecimal serviceCharge;
	
	//设置逾期费
	private BigDecimal overdueCharge;
	
	//设置申请时间
	private int applyTime;
	
	//设置审核时间
	private int checkTime;
	
	//设置放款时间
	private int loanTime;
	
	//设置还款时间
	private int repayTime;
	
	//实际还款时间
	private int realRepayTime;
	
	//设置借款状态
	private int status;
	
	//设置审核类型
	private int checkType;
	
	//设置操作人uid
	private long operater;
	
	//设置风控分数
	private double score;
	
	//设置备注信息 多条用!@##@!隔开
	private String remark;
	
	//设置借款状态多个查询
	private String whereStatus;
	
	//设置开始申请时间
	private int applyStartTime;
	
	//设置结束申请时间
	private int applyEndTime;

	//设置放款开始时间
	private int loanStartTime;
	
	//设置放款结束时间
	private int loanEndTime;
	
	//设置风控起始分数
	private int startScore;
	
	//设置风控结束分数
	private int endScore;
	
	//设置数据偏移条数
	private int offset;
	
	//设置页面条数
	private int pageSize;
	
	public String getWhereStatus() {
		return whereStatus;
	}

	public void setWhereStatus(String whereStatus) {
		this.whereStatus = whereStatus;
	}

	public int getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(int applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public int getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(int applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public int getLoanStartTime() {
		return loanStartTime;
	}

	public void setLoanStartTime(int loanStartTime) {
		this.loanStartTime = loanStartTime;
	}

	public int getLoanEndTime() {
		return loanEndTime;
	}

	public void setLoanEndTime(int loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public int getStartScore() {
		return startScore;
	}

	public void setStartScore(int startScore) {
		this.startScore = startScore;
	}

	public int getEndScore() {
		return endScore;
	}

	public void setEndScore(int endScore) {
		this.endScore = endScore;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBrecordId() {
		return brecordId;
	}

	public void setBrecordId(int brecordId) {
		this.brecordId = brecordId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
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

	public BigDecimal getBorrowInterest() {
		return borrowInterest;
	}

	public void setBorrowInterest(BigDecimal borrowInterest) {
		this.borrowInterest = borrowInterest;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public BigDecimal getOverdueCharge() {
		return overdueCharge;
	}

	public void setOverdueCharge(BigDecimal overdueCharge) {
		this.overdueCharge = overdueCharge;
	}

	public int getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(int applyTime) {
		this.applyTime = applyTime;
	}

	public int getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(int checkTime) {
		this.checkTime = checkTime;
	}

	public int getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(int loanTime) {
		this.loanTime = loanTime;
	}

	public int getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(int repayTime) {
		this.repayTime = repayTime;
	}
	
	public int getRealRepayTime() {
		return realRepayTime;
	}

	public void setRealRepayTime(int realRepayTime) {
		this.realRepayTime = realRepayTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCheckType() {
		return checkType;
	}

	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}

	public long getOperater() {
		return operater;
	}

	public void setOperater(long operater) {
		this.operater = operater;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BorrowRecord: "+uid+" "+borrowMoney+" "+borrowLimit;
	}
	
}
