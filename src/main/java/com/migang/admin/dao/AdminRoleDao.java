package com.migang.admin.dao;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.AdminRole;

/**
 * 后台角色Dao接口
 *
 *＠param addTime 2017-10-15
 *@param author   ChengBo
 */
public interface AdminRoleDao {

	//新增角色入库
	int addAdminUserRole(AdminRole adminRole);
	
	//查询角色列表数据
	List<AdminRole> getRoleList(Map<String, Object> roleMap);
	
	//查询角色总条数
	int getCountRoleNum(Map<String, Object> roleMap);
	
	//查询角色数据
	AdminRole getRoleById(int roleId);
	
	//编辑角色数据
	Boolean editRoleById(AdminRole adminRole);
}