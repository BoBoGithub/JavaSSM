package com.migang.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.migang.admin.entity.AdminRolePriv;

/**
 * 后台角色权限Dao接口
 *
 *＠param addTime 2017-10-19
 *@param author   ChengBo
 */
public interface AdminRolePrivDao {

	//新增角色权限
	int addAdminRolePriv(AdminRolePriv rolePriv);
	
	//查询指定角色的权限数据
	List<AdminRolePriv> getAdminRolePrivByRoleId(int roleId);
	
	//删除指定角色的权限数据
	Boolean delPrivByRoleId(int roleId);
	
	//批量插入角色权限数据
	Boolean batchInsertRolePriv(List<AdminRolePriv> lists);
	
	//根据角色ID和url查询权限数据
	AdminRolePriv getRolePrivByRoleIdUrl(@Param("roleId") int roleId, @Param("url") String url);
}
