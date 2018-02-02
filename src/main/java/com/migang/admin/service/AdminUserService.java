package com.migang.admin.service;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.AdminUser;
import com.migang.admin.exception.BusinessException;

/**
 * 管理用户操作Service接口
 *
 *＠param addTime 2017-08-22
 *@param author   ChengBo
 */
public interface AdminUserService {

	/**
	 * 更具用户uid查询用户信息
	 * 
	 * ＠param long uid　用户uid
	 * @throws BusinessException 
	 */
	AdminUser getAdminUserInfoByUid(long uid) throws BusinessException;
	
	/**
	 * 更具用户名查询用户信息
	 * 
	 * ＠param String username 用户名
	 */
	AdminUser getAdminUserInfoByUserName(String username);
	
	/**
	 * 获取用户列表数据
	 * 
	 * @param String username
	 * @param int page
	 * @param int pageSize
	 */
	List<AdminUser> getAdminUserList(String username, Integer page, Integer pageSize);
	
	/**
	 * 获取用户的总条数
	 * 
	 * @param String username
	 */
	int getTotalUserNum(String username);
	
	/**
	 * 新增用户
	 * 
	 * @param username
	 * @param password
	 * @param realname
	 * @param roleId
	 * @return
	 */
	int addAdminUser(String username, String password, String realname, Integer roleId);
	
	/**
	 * 调取指定角色的用户列表
	 * 
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @return
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	List<AdminUser> getAdminUserListByRoleId(Integer roleId, Integer page, Integer pageSize) throws BusinessException;
	
	/**
	 * 获取指定角色用户的总条数
	 * 
	 * @param roleId
	 * @return
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	int getTotalUserNumByRoleId(Integer roleId);
	
	/**
	 * 更新管理用户的状态
	 * 
	 * @param adminUserMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 */
	boolean updAdminUserByUid(Map<String, String> adminUserMap) throws BusinessException;
	
	/**
	 * 更新管理用户信息
	 * 
	 * @param updMap
	 * @return
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	Boolean updAdminInfo(Map<String, String> updMap) throws BusinessException;
}
