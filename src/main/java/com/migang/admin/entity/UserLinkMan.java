package com.migang.admin.entity;

/**
 * 用户联系人信息实体类
 *
 *@param addTime 2017-09-26
 *@param author    ChengBo
 */
public class UserLinkMan {

	//设置自增id
	private int linkManId;
	
	//设置用户uid
	private long uid;
	
	//设置联系人姓名
	private String name;
	
	//设置联系人电话
	private String phone;
	
	//设置联系人类型
	private int relation;
	
	//设置创建时间
	private int ctime;
	
	//设置备注信息
	private String remark;

	public int getLinkManId() {
		return linkManId;
	}

	public void setLinkManId(int linkManId) {
		this.linkManId = linkManId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
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
		return "UserLinkMan: "+linkManId+" "+uid+" "+name+" "+phone;
	}
	
}
