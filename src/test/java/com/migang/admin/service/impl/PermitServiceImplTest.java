package com.migang.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.entity.AdminMenu;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.PermitService;

/**
 * 测试　权限相关的操作
 *
 *@param addTime 2017-10-18
 *@param author    ChengBo
 */
public class PermitServiceImplTest extends BaseTest {

	@Autowired
	private PermitService permitService;
	
	@Autowired
	private PermitServiceImpl permitServiceImpl;
	
	/**
	 * 测试　查询菜单列表数据
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetMenuList() throws BusinessException{
		//调取数据
		List<Map<String, String>> menuListData = permitService.getMenuTree();
		logger.debug("=== {}", menuListData);
	}
	
	/**
	 * 测试　获取菜单数据
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetAdminMenuData(){
		//后去菜单数据
		List<AdminMenu> menuData = permitServiceImpl.getAdminMenuData();
		logger.debug("=== {}, {}",menuData.size(), menuData);
	}
	
	/**
	 * 测试　调取子菜单列表
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetChildMenuList(){
		//调取子菜单数据
//		List<Map<String, String>> listData = permitServiceImpl.getChildMenuList(1);
//		logger.debug("=== {}, {}",listData.size(),  listData);
	}
	
	/**
	 * 测试　获取菜单数据
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetMenuTree(){
		//获取数据
		List<Map<String, String>> menuList = permitServiceImpl.getMenuTree();
		if(menuList.size() >= 0){
			for(int i = 0; i<menuList.size();i++){
				logger.debug("==== {}, {}", i, menuList.get(i));
			}
		}else{
			logger.debug("=== no menu data");
		}
	}
	
	/**
	 * 测试　查询指定角色id的权限数据
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetAdminRolePrivByRoleId() throws BusinessException{
		//查询数据
		List<String> listData = permitServiceImpl.getAdminRolePrivByRoleId(1);
		if(listData.size()>= 0){
			for(int i=0;i<listData.size();i++){
				logger.debug("==== {}", listData.get(i));
			}
		}else{
			logger.debug("=== no data");
		}
	}
}
