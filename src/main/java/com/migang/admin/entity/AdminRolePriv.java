package com.migang.admin.entity;

/**
 * 角色权限实体类
 * 
 *@param addTime 2017-10-19
 *@param author    ChengBo
 */
public class AdminRolePriv {

	//设置角色id
	private int roleId;
	
	//设置请求地址
	private String url;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "AdminRolePriv: "+roleId+" "+url;
	}
}
