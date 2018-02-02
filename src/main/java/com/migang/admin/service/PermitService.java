package com.migang.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.migang.admin.entity.AdminMenu;
import com.migang.admin.entity.AdminRole;
import com.migang.admin.exception.BusinessException;

/**
 * 后台用户权限相关Service
 * 
 *@param addTime	2017-10-15
 *@param authro  		ChengBo
 */
@Service
public interface PermitService {

	/**
	 * 新增用户角色
	 * 
	 * @param roleMap
	 * @return
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	int addAdminUserRole(Map<String, String> roleMap) throws BusinessException;
	
	/**
	 * 查询用户角色列表数据
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	List<AdminRole> getRoleList(int page, int pageSize) throws BusinessException;
	
	/**
	 * 汇总用户角色总条数
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	int getCountRoleNum() throws BusinessException;
	
	/**
	 * 根据角色id　获取角色数据
	 * 
	 * @param roleId
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	AdminRole getRoleDataById(int roleId) throws BusinessException;
	
	/**
	 * 编辑用户角色
	 * 
	 * @param roleMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	Boolean editRoleById(Map<String, String> roleMap) throws BusinessException;
	
	/**
	 * 新增管理菜单
	 * 
	 * @param menuMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	int addAdminMenu(Map<String, String> menuMap) throws BusinessException;
	
	/**
	 * 获取树形结构的菜单列表数据
	 * 
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	List<Map<String, String>> getMenuTree() throws BusinessException;
	
	/**
	 * 获取指定菜单id的数据
	 * 
	 * @param menuId
	 * @return
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	AdminMenu getAdminMenuById(int menuId) throws BusinessException;
	
	
	/**
	 * 编辑管理菜单
	 * 
	 * @param roleMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	Boolean editAdminMenu(Map<String, String> menuMap) throws BusinessException;
	
	/**
	 * 根据角色id查询权限数据
	 * 
	 * @param roleId
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	List<String> getAdminRolePrivByRoleId(int roleId) throws BusinessException;
	
	/**
	 * 设置角色权限
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	Boolean setAdminRolePriv(int roleId, List<Integer> menuIds) throws BusinessException;

	/**
	 * 检查当前角色是否有权限访问
	 * 
	 * @param roleId
	 * @param url
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月20日
	 * @param author     ChengBo
	 */
	Boolean checkVistPermit(int roleId, String url) throws BusinessException;
	
	/**
	 * 查询指定pid的菜单数据
	 * 
	 * @param pid
	 * @return
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 */
	List<AdminMenu> getAdminMenuByPid(int pid);
}
