package com.migang.admin.entity;

/**
 * 用户职业信息实体类
 *
 *@param addTime 2017-09-25
 *@param author    ChengBo
 */
public class UserJob {

	//自增id
	private int jobId;
	
	//用户uid
	private long uid;
	
	//职位名称
	private String jobName;
	
	//月收入
	private int income;
	
	//公司名称
	private String company;
	
	//所在省份
	private int province;
	
	//所在城市
	private int city;
	
	//所在区县
	private int district;
	
	//具体住址
	private String address;
	
	//单位电话
	private String phone;
	
	//创建时间
	private long ctime;

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getDistrict() {
		return district;
	}

	public void setDistrict(int district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	@Override
	public String toString() {
		return "UserJob: "+uid+" "+jobName+" "+income+" "+company;
	}
	
}
