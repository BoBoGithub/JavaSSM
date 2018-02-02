package com.migang.admin.entity;

import java.io.Serializable;

/**
 *  前端用户表实体类 mg_db_admin.mg_user
 *  
 *  ＠param addTime 2017-09-05
 *  @param author ChengBo
 */
public class User implements Serializable{
	//序列化时使用
	private static final long serialVersionUID = 1L;
	
	//用户id
	private long uid;

	//用户手机号
	private String mobile;
	
	//用户密码
	private String password;
	
	//加盐串
	private String encrypt;
	
	//创建时间
	private long ctime;
	
	//用户状态
	private int status;
	
	//注册ip
	private String ip;
	
	//身份认证状态
	private long shenfenAuth;
	
	//活体认证状态
	private long huotiAuth;
	
	//个人信息认证
	private long personAuth;
	
	//职业信息认证
	private long jobAuth;
	
	//联系人信息认证
	private long contactsAuth;
	
	//手机运营商认证
	private long mobileAuth;
	
	//银行卡认证
	private long bandcardAuth;
	
	//所在省份
	private int province;
	
	//所在城市
	private int city;
	
	//所在区县
	private int district;

	//注册起始时间 [查询使用]
	private long regStartTime;
	
	//注册截止时间 [查询使用]
	private long regEndTime;
	
	//设置偏移数
	private int offset;
	
	//设置每页条数
	private int pageSize;
	
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

	public long getRegStartTime() {
		return regStartTime;
	}

	public void setRegStartTime(long regStartTime) {
		this.regStartTime = regStartTime;
	}

	public long getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(long regEndTime) {
		this.regEndTime = regEndTime;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getShenfenAuth() {
		return shenfenAuth;
	}

	public void setShenfenAuth(long shenfenAuth) {
		this.shenfenAuth = shenfenAuth;
	}

	public long getHuotiAuth() {
		return huotiAuth;
	}

	public void setHuotiAuth(long huotiAuth) {
		this.huotiAuth = huotiAuth;
	}

	public long getPersonAuth() {
		return personAuth;
	}

	public void setPersonAuth(long personAuth) {
		this.personAuth = personAuth;
	}

	public long getJobAuth() {
		return jobAuth;
	}

	public void setJobAuth(long jobAuth) {
		this.jobAuth = jobAuth;
	}

	public long getContactsAuth() {
		return contactsAuth;
	}

	public void setContactsAuth(long contactsAuth) {
		this.contactsAuth = contactsAuth;
	}

	public long getMobileAuth() {
		return mobileAuth;
	}

	public void setMobileAuth(long mobileAuth) {
		this.mobileAuth = mobileAuth;
	}

	public long getBandcardAuth() {
		return bandcardAuth;
	}

	public void setBandcardAuth(long bandcardAuth) {
		this.bandcardAuth = bandcardAuth;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "uid:"+this.getUid()+" mobile:"+this.getMobile();
	}	
}