package com.migang.admin.entity;

/**
 *  用户角色实体类 mg_db_admin.mg_admin_role
 *  
 *  ＠param addTime 2017-08-22
 *  @param author ChengBo
 */
public class AdminRole {

	//角色自增id
	private int roleId;
	
	//角色名称
	private String roleName;
	
	//角色描述
	private String roleDesc;
	
	//角色状态
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public String toString() {
		return "AdminRoleData: "+roleId+" "+roleName+" "+ roleDesc+" "+status;
	}
}
