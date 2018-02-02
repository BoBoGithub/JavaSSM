package com.migang.admin.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.entity.AdminRolePriv;

/**
 * 角色权限Dao 测试
 */
public class AdminRolePrivDaoTest extends BaseTest {

	@Autowired
	private AdminRolePrivDao rolePrivDao;
	
	/**
	 * 测试　新增角色权限数据
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	@Test
	public void testAddAdminRolePriv(){
		//实例化角色权限Bean
		AdminRolePriv rolePriv = new AdminRolePriv();
	
		//设置入库参数
		rolePriv.setRoleId(10);
		rolePriv.setUrl("/admin/operate");
		
		//输入入库
		try{
			int ret = rolePrivDao.addAdminRolePriv(rolePriv);
			logger.debug("==== {}", ret);
		}catch(Exception ex){
			logger.debug("=== {}", ex.getMessage());
		}
	}
	
	/**
	 * 测试　获取指定角色的权限数据
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetAdminRolePrivByRoleId(){
		//查询角色权限数据
		List<AdminRolePriv> rolePrivList = rolePrivDao.getAdminRolePrivByRoleId(1);
		if(rolePrivList.size() > 0){
			for(int i=0;i<rolePrivList.size();i++){
				logger.debug("=== {}", rolePrivList.get(i));
			}
		}else{
			logger.debug("=== no data");
		}
	}
	
	/**
	 * 测试　删除角色权限
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	@Test
	public void testDeletePrivByRoleId(){
		//删除指定角色的权限
		Boolean ret = rolePrivDao.delPrivByRoleId(1);
		logger.debug("=== {}", ret);
	}
	
	/**
	 * 测试　查询指定　角色＋URL　的权限数据
	 * 
	 * @param addTime 2017年10月20日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetRolePrivByRoleIdUrl(){
		//设置查询参数
		int roleId = 22;
		String url = "/admin/check/info/01/002";

		//查询数据
		AdminRolePriv rolePirvData = rolePrivDao.getRolePrivByRoleIdUrl(roleId, url);
		logger.debug("======= {}", rolePirvData);
	}
}