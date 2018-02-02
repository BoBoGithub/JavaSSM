package com.migang.admin.dao;

import java.util.List;

import com.migang.admin.entity.AdminMenu;

/**
 * 后台菜单Dao接口
 *
 *＠param addTime 2017-10-17
 *@param author   ChengBo
 */
public interface AdminMenuDao {
	
	//新增菜单
	int addAdminMenu(AdminMenu adminMenu);
	
	//获取菜单列表
	List<AdminMenu> getMenuList();
	
	//更新菜单数据
	Boolean editAdminMenu(AdminMenu adminMenu);
}
