package com.migang.admin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.entity.AdminRole;

/**
 * 测试－后台用户角色Dao
 *
 *@param addTime 2017-10-15
 *@param author    ChengBo
 */
public class AdminRoleDaoTest extends BaseTest {
	
	@Autowired
	private AdminRoleDao adminRoleDao;
	
	/**
	 * 测试－新增用户角色
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 */
	@Test
	public void testAddAdminRole(){
		//实例化入库Bean
		AdminRole adminRole = new AdminRole();
		
		//设置入库条件
		adminRole.setRoleName("超级管理员－10");
		adminRole.setRoleDesc("测试描述－10");
		adminRole.setStatus(0);
		
		//参数入库
		int roleId = adminRoleDao.addAdminUserRole(adminRole);
		logger.debug("=== {}, {}", roleId, adminRole.getRoleId());
	}
	
	/**
	 * 测试　查询角色列表数据
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetRoleList(){
		//实例化查询Map
		Map<String, Object> roleMap = new HashMap<String, Object>();
		
		@SuppressWarnings("serial")
		List<Integer> statusList = new ArrayList<Integer>(){{add(1);add(0);}};
		
		//设置查询条件
		roleMap.put("offset", 0);
		roleMap.put("pageSize", 10);
		roleMap.put("status", statusList);
		
		//查询数据
		List<AdminRole> roleListData = adminRoleDao.getRoleList(roleMap);
		if(roleListData.size() > 0){
			for(int i = 0; i < roleListData.size() ; i++){
				logger.debug("==== roleData: {}", roleListData.get(i));
			}
		}else{
			logger.debug("=== no role data");
		}
	}
	
	/**
	 * 测试　汇总用户角色总条数
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetCountRoleNum(){
		//实例化查询Map
		Map<String, Object> roleMap = new HashMap<String, Object>();
		
		@SuppressWarnings("serial")
		List<Integer> statusList = new ArrayList<Integer>(){{add(1);add(0);}};
		
		//设置查询条件
		roleMap.put("status", statusList);
		
		//查询数据
		int totalNum = adminRoleDao.getCountRoleNum(roleMap);
		logger.debug("==== {}", totalNum);
	}
	
	/**
	 * 测试　根据id查询角色数据
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetRoleById(){
		AdminRole roleData = adminRoleDao.getRoleById(12);
		logger.debug("== {}", roleData);
	}
	
	/**
	 * 编辑角色数据
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 */
	@Test
	public void testEditRoleById(){
		//实例化操作类
		AdminRole adminRole = new AdminRole();
		
		//设置更新参数
		adminRole.setRoleId(12);
//		adminRole.setRoleName("测试角色012-=-");
//		adminRole.setRoleDesc("测试角色描述012-=-");
//		adminRole.setStatus(1);
		
		Boolean ret = adminRoleDao.editRoleById(adminRole);
		logger.debug("=== {}", ret);
	}
}
