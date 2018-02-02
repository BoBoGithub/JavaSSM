package com.migang.admin.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.entity.AdminMenu;

/**
 * 测试－管理菜单操作
 */
public class AdminMenuDaoTest extends BaseTest {

	@Autowired
	private AdminMenuDao adminMenuDao;
	
	/**
	 * 测试　新增菜单操作
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	@Test
	public void testAddAdminMenu(){
		//实例化菜单实体
		AdminMenu adminMenu = new AdminMenu();
		
		//设置入库参数
		adminMenu.setParentId(5);
		adminMenu.setName("财务管理－子菜单02－子子菜单002");
		adminMenu.setUrl("/admin/finacail/02/002");
		adminMenu.setStatus(0);
		
		//数据入库
		int ret = adminMenuDao.addAdminMenu(adminMenu);
		logger.debug("=== {}, {}", ret, adminMenu.getId());
	}
	
	/**
	 * 测试　查询菜单列表数据
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetMenuList(){
		List<AdminMenu> menuList = adminMenuDao.getMenuList();
		if(menuList.size() > 0){
			for(int i = 0; i < menuList.size(); i++){
				logger.debug("=== {}", menuList.get(i));
			}
		}else{
			logger.debug("=== no date");
		}
	}
}
