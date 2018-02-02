package com.migang.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.migang.admin.entity.AdminUser;

/**
 * 管理员用户Dao接口
 *
 *＠param addTime 2017-08-22
 *@param author   ChengBo
 */
public interface AdminUserDao {

	//根据用户uid获取用户信息
	AdminUser getAdminUserByUid(long uid);
	
	//新增用户数据
	int insertAdminUserInfo(AdminUser userInfo);
	
	//给用户分配角色
	int updAdminUserRoleByUid(@Param("uid") long uid, @Param("roleId") long roleId);
	
	//根据用户名查询用户信息
	AdminUser getAdminUserByUserName(String username);
	
	//获取用户列表数据
	List<AdminUser> getAdminUserList(@Param("username") String username, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

	//查询用户总条数
	int getTotalUserNum(@Param("username") String username);

	//查询指定角色的用户数据
	List<AdminUser> getAdminUserListByRoleId(@Param("roleId") Integer roleId, @Param("page") Integer page, @Param("pageSize") Integer pageSize);
	
	//查询用户总条数
	int getTotalUserNumByRoleId(@Param("roleId") Integer roleId);
	
	//更新用户数据
	boolean updAdminUserByUid(AdminUser adminUser	);
}
