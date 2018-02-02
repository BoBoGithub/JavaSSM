package com.migang.admin.entity;

import java.io.Serializable;

/**
 *  管理用用户表实体类 mg_db_admin.mg_admin
 *  
 *  ＠param addTime 2017-08-22
 *  @param author ChengBo
 */
public class AdminUser implements Serializable{
	//序列化时使用
	private static final long serialVersionUID = 1L;
	
	//用户id
	private long uid;
	
	//用户名
	private String username;
	
	//用户密码
	private String password;
	
	//角色id
	private int roleId;
	
	//加盐串
	private String encrypt;
	
	//用户邮箱
	private String email;
	
	//真是姓名
	private String realname;
	
	//创建时间
	private String ctime;
	
	//用户状态
	private int status;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "uid:"+this.getUid()+" username:"+this.getUsername()+" realname:"+this.getRealname();
	}
	
}
