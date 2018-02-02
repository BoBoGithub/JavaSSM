package com.migang.admin.entity;

import java.io.Serializable;

public class AdminMenu implements Serializable{

	//序列化时使用
	private static final long serialVersionUID = 1L;
	
	//菜单自增ID
	private int id;
	
	//菜单名称
	private String name;
	
	//菜单父类id
	private int parentId;
	
	//菜单访问地址
	private String url;
	
	//菜单状态
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AdminMenu: "+ id+ " "+name+" "+url+" "+parentId+" "+status;
	}
}
