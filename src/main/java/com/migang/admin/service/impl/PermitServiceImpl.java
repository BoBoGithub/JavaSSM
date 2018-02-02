package com.migang.admin.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.cache.MemCacheManager;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.dao.AdminMenuDao;
import com.migang.admin.dao.AdminRoleDao;
import com.migang.admin.dao.AdminRolePrivDao;
import com.migang.admin.entity.AdminMenu;
import com.migang.admin.entity.AdminRole;
import com.migang.admin.entity.AdminRolePriv;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.PermitService;

/**
 * 后台权限处理类
 *
 *＠param addTime 2017-10-15
 *@param author  ChengBo
 */
@Service
public class PermitServiceImpl implements PermitService {
	
	//设置日志操作
	private static final Logger logger = LoggerFactory.getLogger(PermitServiceImpl.class);

	@Autowired
	private AdminRoleDao adminRoleDao;
	
	@Autowired
	private AdminMenuDao adminMenuDao;
	
	@Autowired
	private AdminRolePrivDao rolePrivDao;
	
	@Autowired
	private MemCacheManager cacheService;
	
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
	@Override
	public int addAdminUserRole(Map<String, String> roleMap) throws BusinessException {
		//检查角色入库参数
		AdminRole adminRole = checkAddAdminUserRole(roleMap);
		
		try{
			//新增角色数据入库
			adminRoleDao.addAdminUserRole(adminRole);
			
			return adminRole.getRoleId();
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl addAdminUserRole Exception: "+ ex.getMessage());
			
			//抛出插入失败异常
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_INSERT_FAILURE, "用户角色提交失败");
		}
	}

	/**
	 * 检查用户角色入库参数
	 * 
	 * @param roleMap
	 * @return
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private AdminRole checkAddAdminUserRole(Map<String, String> roleMap) throws BusinessException{
		//检查参数
		if(roleMap == null){
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_PARAM_ERROR, "新增用户角色参数错误");
		}
		
		//实例化用户角色实体Bean
		AdminRole adminRole = new AdminRole();
		
		//检查用户角色名称
		if(roleMap.containsKey("roleName") && !roleMap.get("roleName").isEmpty()){
			adminRole.setRoleName(roleMap.get("roleName"));
		}else{
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_NAME_ERROR, "用户角色名称参数错误");
		}
		
		//检查用户角色描述
		if(roleMap.containsKey("roleDesc") && !roleMap.get("roleDesc").isEmpty()){
			adminRole.setRoleDesc(roleMap.get("roleDesc"));
		}else{
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_DESC_ERROR, "用户角色描述参数错误");
		}
		
		//检查角色状态
		if(roleMap.containsKey("roleStatus") && Arrays.asList(new String[]{"0","1"}).contains(roleMap.get("roleStatus"))){
			adminRole.setStatus(Integer.parseInt(roleMap.get("roleStatus")));
		}else{
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_STATUS_ERROR, "用户角色状态参数错误");
		}
		
		return adminRole;
	}

	/**
	 * 查询用户角色列表数据
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public List<AdminRole> getRoleList(int page, int pageSize) throws BusinessException{
		//设置查询Map
		Map<String, Object> roleMap = new HashMap<String, Object>();
		
		//设置查询状态 -1已删除 0:启用 1:禁止
		@SuppressWarnings("serial")
		List<Integer> statusList = new ArrayList<Integer>(){{add(0); add(1);}};

		//设置分页
		pageSize = (pageSize <= 0 || pageSize > 100 ? 10 : pageSize);
		int offset = (page <= 0 ||page -1 <=0 ? 0 : (page -1)*pageSize);
		
		//设置查询参数
		roleMap.put("offset", offset);
		roleMap.put("pageSize", pageSize);
		roleMap.put("status", statusList);
		
		try{
			//查询数据
			List<AdminRole> adminRoleData = adminRoleDao.getRoleList(roleMap);
			
			return adminRoleData;
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl getRoleList Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_SELECT_FAILURE, "用户角色查询失败");
		}
	}
	
	/**
	 * 汇总用户角色总条数
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public int getCountRoleNum() throws BusinessException{
		//设置查询Map
		Map<String, Object> roleMap = new HashMap<String, Object>();
		
		//设置查询状态 -1已删除 0:启用 1:禁止
		@SuppressWarnings("serial")
		List<Integer> statusList = new ArrayList<Integer>(){{add(0); add(1);}};
		
		//设置查询参数
		roleMap.put("status", statusList);
		
		try{
			//查询数据
			return adminRoleDao.getCountRoleNum(roleMap);
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl getCountRoleNum Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_SELECT_FAILURE, "用户角色查询失败");
		}
	}
	
	/**
	 * 根据角色id　获取角色数据
	 * 
	 * @param roleId
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public AdminRole getRoleDataById(int roleId) throws BusinessException{
		//检查参数
		if(!ValidateUtils.checkIsNumber(roleId)){
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_PARAM_ERROR, "用户角色查询参数错误");
		}
		
		try{
			//查询数据
			AdminRole roleData = adminRoleDao.getRoleById(roleId);
			
			return roleData;
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl getRoleDataById Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_SELECT_FAILURE, "用户角色查询失败");
		}
	}
	
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
	public Boolean editRoleById(Map<String, String> roleMap) throws BusinessException{
		//检查用户参数
		AdminRole adminRole = checkEditRoleByIdParam(roleMap);
		
		try{
			//更新数据
			Boolean ret = adminRoleDao.editRoleById(adminRole);
			
			return ret;
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl editRoleById Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_EDIT_FAILURE, "用户角色编辑失败");
		}
	}
	
	/**
	 * 检查　更新角色参数
	 * 
	 * @param roleMap
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private AdminRole checkEditRoleByIdParam(Map<String, String> roleMap) throws BusinessException{
		//检查参数是否为空
		if(roleMap == null){
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_PARAM_ERROR, "用户角色编辑参数错误");
		}
		
		//设置查询Bean
		AdminRole adminRole = new AdminRole();
		
		//检查角色id
		if(roleMap.containsKey("roleId") && ValidateUtils.checkIsNumber(roleMap.get("roleId"))){
			adminRole.setRoleId(Integer.parseInt(roleMap.get("roleId")));
		}else{
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_PARAM_ERROR, "用户角色编辑参数ID错误");
		}
		
		//检查角色名称
		if(roleMap.containsKey("roleName") && !roleMap.get("roleName").isEmpty()){
			adminRole.setRoleName(roleMap.get("roleName"));
		}
		
		//检查角色描述
		if(roleMap.containsKey("roleDesc") && !roleMap.get("roleDesc").isEmpty()){
			adminRole.setRoleDesc(roleMap.get("roleDesc"));
		}
		
		//检查角色状态
		if(roleMap.containsKey("roleStatus") && Arrays.asList(new String[]{"-1", "0","1"}).contains(roleMap.get("roleStatus"))){
			adminRole.setStatus(Integer.parseInt(roleMap.get("roleStatus")));
		}
		
		return adminRole;
	}

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
	public int addAdminMenu(Map<String, String> menuMap) throws BusinessException{
		//检查管理菜单参数
		AdminMenu adminMenu = checkAdminMenuParam(menuMap);
		
		try{
			//菜单入库
			adminMenuDao.addAdminMenu(adminMenu);
			
			//删除菜单缓存
			cacheService.delete(CommonConstant.ADMIN_MENU_CACHE_KEY);
			
			return adminMenu.getId();
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl addAdminMenu Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_MENU_INSERT_FAILURE, "管理菜单入库失败");
		}
	}
	
	/**
	 * 修改管理菜单
	 * 
	 * @param menuMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	public Boolean editAdminMenu(Map<String, String> menuMap) throws BusinessException{
		//检查管理菜单参数
		AdminMenu adminMenu = checkAdminMenuParam(menuMap);
		
		try{
			//菜单入库
			adminMenuDao.editAdminMenu(adminMenu);
			
			//删除菜单缓存
			cacheService.delete(CommonConstant.ADMIN_MENU_CACHE_KEY);
			
			return true;
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl editAdminMenu Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_MENU_INSERT_FAILURE, "管理菜单编辑失败");
		}
	}
	
	/**
	 * 检查入库菜单参数
	 * 
	 * @param menuMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	private AdminMenu checkAdminMenuParam(Map<String, String> menuMap) throws BusinessException{
		//检查参数
		if(menuMap == null){
			throw new BusinessException(CommonConstant.ADMIN_MENU_PARAM_ERROR, "新增菜单参数错误");
		}
		
		//实例化菜单实体
		AdminMenu adminMenu = new AdminMenu();
		
		//检查菜单id 适用于更新参数检查
		if(menuMap.containsKey("menuId") && ValidateUtils.checkIsNumber(menuMap.get("menuId"))){
			//检查是否时更新到自己的子菜单
			if(menuMap.get("menuId").equals(menuMap.get("parentId"))){
				throw new BusinessException(CommonConstant.ADMIN_MENU_PARAM_ERROR, "菜单父级选择错误");
			}
			
			//设置更新的菜单id
			adminMenu.setId(Integer.parseInt(menuMap.get("menuId")));
		}
		
		//检查是否时删除操作
		if(menuMap.containsKey("menuStatus") && menuMap.get("menuStatus").equals("-1")){
			adminMenu.setStatus(Integer.parseInt(menuMap.get("menuStatus")));
			return adminMenu;
		}
		
		//检查父级菜单
		if(!menuMap.containsKey("parentId") || !ValidateUtils.checkIsNumber(menuMap.get("parentId"))){
			throw new BusinessException(CommonConstant.ADMIN_MENU_PARENTID_ERROR, "菜单父级参数错误");
		}
		
		//检查菜单名称
		if(!menuMap.containsKey("menuName") || menuMap.get("menuName").isEmpty()){
			throw new BusinessException(CommonConstant.ADMIN_MENU_NAME_ERROR, "菜单名称参数错误");
		}
		
		//检查菜单请求地址
		if(!menuMap.containsKey("requestUrl") || menuMap.get("requestUrl").isEmpty()){
			throw new BusinessException(CommonConstant.ADMIN_MENU_URL_ERROR, "菜单请求地址参数错误");
		}
		
		//检查菜单状态
		if(!menuMap.containsKey("menuStatus") || !Arrays.asList(new String[]{"-1","0","1"}).contains(menuMap.get("menuStatus"))){
			throw new BusinessException(CommonConstant.ADMIN_MENU_STATUS_ERROR, "菜单状态参数错误");
		}
		
		//设置入库参数
		adminMenu.setParentId(Integer.parseInt(menuMap.get("parentId")));
		adminMenu.setName(menuMap.get("menuName"));
		adminMenu.setUrl(menuMap.get("requestUrl"));
		adminMenu.setStatus(Integer.parseInt(menuMap.get("menuStatus")));
		
		return adminMenu;
	}
	
	/**
	 * 获取树形结构的菜单列表数据
	 * 
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	public List<Map<String, String>> getMenuTree(){
		//实例化返回数据
		List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();
		
		//后去菜单整理后的数据
		List<Map<String, String>> dataList = getChildMenuList(0, menuList, "");
		if(dataList.size() <= 0){
			return menuList;
		}
		
		//返回处理结果
		return menuList;
	}
	
	/**
	 * 调取菜单的子菜单数据
	 * 
	 * @param pid
	 * @return
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	public List<Map<String, String>> getChildMenuList(int pid, List<Map<String, String>> menuList, String spacer){
		//调取菜单数据
		List<AdminMenu> menuListData = getAdminMenuData();
		if(menuListData.size() <= 0){
			return menuList;
		}

		//设置层级前缀
		spacer = (pid == 0 ? "" : spacer+"　│");
		
		//设施菜单个数
		int total = menuList.size();
		
		//提取子菜带数据
		for(int i = 0; i < menuListData.size(); i++){
			if(pid == menuListData.get(i).getParentId()){	
				//实例化菜单Map
				Map<String, String> menuMap = new HashMap<String, String>();

				//设置菜单值
				menuMap.put("id", menuListData.get(i).getId()+"");
				menuMap.put("name", spacer+"　├─"+menuListData.get(i).getName());
				menuMap.put("parentid", menuListData.get(i).getParentId()+"");
				menuMap.put("url", menuListData.get(i).getUrl()+"");
				
				//设置权限时页面上使用
				menuMap.put("pnode", (menuListData.get(i).getParentId() == 0 ? "" : "class='child-of-node-"+menuListData.get(i).getParentId()+"'"));
				menuMap.put("level", getMenuLevel(menuListData.get(i).getId(), 0)+"");
				
				//追加子菜单
				menuList.add(menuMap);
				
				//处理子菜单
				getChildMenuList(menuListData.get(i).getId(), menuList, spacer);
			}
		}
		
		//替换最后一个结尾前置标签 
		if(total < menuList.size()){
			menuList.get(menuList.size()-1).put("name", menuList.get(menuList.size()-1).get("name").replace("├─", "└─"));
		}
		
		return menuList;
	}
	
	/**
	 * 获取指定菜单的层级数
	 * 
	 * @param menuId
	 * @param level
	 * @return
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	private int getMenuLevel(int menuId, int level){
		//调取菜单数据
		List<AdminMenu> menuListData = getAdminMenuData();
		if(menuListData.size() <= 0){
			return level;
		}
		
		//循环菜单查找
		for(int i=0; i<menuListData.size();i++){
			if(menuId == menuListData.get(i).getId()){
				if(menuListData.get(i).getParentId() == 0){
					return level;
				}
				return getMenuLevel(menuListData.get(i).getParentId(), ++level);
			}
		}
		
		return level;
	}
	
	/**
	 * 调取菜单数据
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	public List<AdminMenu> getAdminMenuData(){
		//从缓存中调取数据
		@SuppressWarnings("unchecked")
		List<AdminMenu> menuListCacheData = (List<AdminMenu>) cacheService.get(CommonConstant.ADMIN_MENU_CACHE_KEY);
		
		//检查是否存在缓存
		if(menuListCacheData != null && menuListCacheData.size() > 0){
			return menuListCacheData;
		}
		
		//调取菜单数据列表
		List<AdminMenu> menuListData = adminMenuDao.getMenuList();
		
		//设置缓存
		cacheService.set(CommonConstant.ADMIN_MENU_CACHE_KEY, menuListData, 86400*30);
		
		//返回菜单数据
		return menuListData;
	}
	
	/**
	 * 获取指定菜单id的数据
	 * 
	 * @param menuId
	 * @return
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	public AdminMenu getAdminMenuById(int menuId){
		//设置返回值
		AdminMenu adminMenu = new AdminMenu();
		
		//设置是否时顶级菜单
		if(menuId == 0){
			adminMenu.setId(0);
			adminMenu.setName("作为一级菜单");
			adminMenu.setParentId(0);
			return adminMenu;
		}
		
		//调取菜单数据列表
		List<AdminMenu> menuListData = getAdminMenuData();
		if(menuListData.size()>0){
			//提取菜单数据
			for(int i = 0; i<menuListData.size();i++){
				if(menuListData.get(i).getId() == menuId){
					return menuListData.get(i);
				}
			}
		}

		return adminMenu;
	}

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
	public List<String> getAdminRolePrivByRoleId(int roleId) throws BusinessException{
		//检查角色id参数
		if(!ValidateUtils.checkIsNumber(roleId)){
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_ID_PARAM, "查询权限角色ID参数错误");
		}
		
		//从缓存中调取数据
		@SuppressWarnings("unchecked")
		List<String> privList = (List<String>) cacheService.get(CommonConstant.ADMIN_ROLE_PRIV_CACHE_KEY +roleId);
		if(privList != null && privList.size() > 0){
			return privList;
		}
		
		//设置返回变量Map
		List<String> privLists = new ArrayList<String>();			
		
		//查询角色数据
		List<AdminRolePriv> rolePrivList = rolePrivDao.getAdminRolePrivByRoleId(roleId);
		if(rolePrivList.size() <= 0){
			return privLists;
		}
			
		//提取权限url
		for(int i=0; i<rolePrivList.size();i++){
			privLists.add(rolePrivList.get(i).getUrl());
		}
		
		//设置角色的权限缓存
		cacheService.set(CommonConstant.ADMIN_ROLE_PRIV_CACHE_KEY+roleId, privLists, 86400*20);
		
		return privLists;
	}

	/**
	 * 设置角色权限
	 * 
	 * @param privMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	public Boolean setAdminRolePriv(int roleId, List<Integer> menuIds) throws BusinessException{
		//检查角色ID
		if(!ValidateUtils.checkIsNumber(roleId)){
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_ID_PARAM, "角色参数错误");
		}
		
		//检查菜单ids
		if(menuIds.size()>0){
			for(int i=0;i<menuIds.size();i++){
				if(!ValidateUtils.checkIsNumber(menuIds.get(i))){
					throw new BusinessException(CommonConstant.ADMIN_MENU_ID_PARAM_ERROR, "菜单参数错误");
				}
			}
		}
	
		//查找指定菜单id的菜单数据
		List<AdminRolePriv> menuList = getAddAdminRoPrivByMenuIds(roleId, menuIds);
		
		try{
			//删除当前角色现有的权限
			rolePrivDao.delPrivByRoleId(roleId);
			if(menuIds.size() <= 0){
				return true;
			}
			
			//菜单权限批量入库新菜单
			rolePrivDao.batchInsertRolePriv(menuList);
			
			//删除角色权限缓存数据
			cacheService.delete(CommonConstant.ADMIN_ROLE_PRIV_CACHE_KEY+roleId);
			
			return true;
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("PermitServiceImpl setAdminRolePriv Exception: "+ ex.getMessage());
			
			//抛出失败异常
			throw new BusinessException(CommonConstant.ADMIN_ROLE_PRIV_INSERT_FAILURE, "更新角色权限失败");
		}
	}
	
	/**
	 * 获取指定菜单id的菜单数据
	 * 
	 * @param menuIds
	 * @return
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	public List<AdminRolePriv> getAddAdminRoPrivByMenuIds(int roleId, List<Integer> menuIds){
		//实例化返回结果
		List<AdminRolePriv> privList = new ArrayList<AdminRolePriv>();
		if(menuIds.size() <= 0){
			return privList;
		}
		
		//获取菜单数据
		List<AdminMenu> menuListData = getAdminMenuData();

		//提取菜单数据
		for(AdminMenu menu : menuListData){
			if(menuIds.contains(menu.getId()) || menuIds.contains(menu.getId()+"")){
				//实例化权限操作Bean
				AdminRolePriv tmpPriv = new AdminRolePriv();
				
				//设置入库参数
				tmpPriv.setRoleId(roleId);
				tmpPriv.setUrl(menu.getUrl());
				
				//追加到返回结果中
				privList.add(tmpPriv);
			}
		}
		
		return privList;
	}
	
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
	public Boolean checkVistPermit(int roleId, String url) throws BusinessException{		
		//检查参数
		if(!ValidateUtils.checkIsNumber(roleId) || url.isEmpty()){
			throw new BusinessException(CommonConstant.COMMON_PERMISSION_PARAM_ERROR, "请求验证权限参数错误");
		}
		
		//由于本地开发调试 地址后缀 去掉
		url = url.replaceFirst("/admin", "");
		
		//记录查询参数
		logger.debug("PermitService checkVistPermit roleId: {}, url: {}", roleId, url);
		
		//检查是否时超级管理员
		if(roleId==1){
			return true;
		}
		
		//调取角色权限数据
		List<String> rolePrivList = getAdminRolePrivByRoleId(roleId);
		
		//检查当前请求是否再权限List中
		if(!rolePrivList.contains(url)){
			throw new BusinessException(CommonConstant.COMMON_NO_PERMISSION, "无权限访问");
		}
		
		return true;
	}

	/**
	 * 查询指定pid的菜单数据
	 * 
	 * @param pid
	 * @return
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 */
	public List<AdminMenu> getAdminMenuByPid(int pid){
		//设置返回结果
		List<AdminMenu> menuList = new ArrayList<AdminMenu>();
		
		//调取菜单数据
		List<AdminMenu> menuListData = getAdminMenuData();
		if(menuListData.size() <= 0){
			return menuList;
		}
		
		//便利菜单提取菜单数据
		for(AdminMenu adminMenu : menuListData){
			if(adminMenu.getParentId() == pid){
				menuList.add(adminMenu);
			}
		}
		
		return menuList;
	}
}
