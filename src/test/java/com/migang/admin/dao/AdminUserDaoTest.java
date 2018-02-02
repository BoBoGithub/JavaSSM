package com.migang.admin.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.CryptoUtils;
import com.migang.admin.entity.AdminUser;

/**
 * 测试AdminUserDao
 *
 *＠param addTime 2017-08-22
 *@param author   ChengBo
 */
public class AdminUserDaoTest extends BaseTest {

	//实例化Dao注解
	@Autowired
	private AdminUserDao adminUserDao;
	
	/**
	 * 测试　根据用户uid获取用户信息接口
	 * 
	 *＠param addTime 2017-08-22
	 *@param author   ChengBo
	 */
	@Test
	public void getAdminUserInfoByUid() throws Exception {
		long uid = Long.parseLong("2");
		AdminUser userInfo = adminUserDao.getAdminUserByUid(uid);
		System.out.println(userInfo);
	}
	
	/**
	 * 测试　新增用户接口
	 * 
	 *＠param addTime 2017-08-22
	 *@param author   ChengBo
	 */
	@Test
	public void insertAdminUserInfo() throws Exception{
		//设置用户名
		String username = "mg_"+CommonUtils.getRandomString(5);
		
		//生成加盐串
		String saltStr = CommonUtils.getRandomString(6);
		
		//这是密码
		String password = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5("123456") + saltStr);
		
		//设置入库信息
		AdminUser userInfo = new AdminUser();
		userInfo.setUsername(username);
		userInfo.setPassword(password);
		userInfo.setEncrypt(saltStr);
		userInfo.setEmail(username+"@migang.com");
		userInfo.setRealname("管理员: "+username);
		
		//实例化时间类
		Date ctime = new Date();
		
		//格式化时间
		SimpleDateFormat dateStyle = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//设置入库时间
		userInfo.setCtime(dateStyle.format(ctime));
		
		//测试入库
		int uid = adminUserDao.insertAdminUserInfo(userInfo);
		System.out.println(uid);
	}
	
	/**
	 * 测试　修改用户角色接口
	 * 
	 *＠param addTime 2017-08-22
	 *@param author   ChengBo
	 */
	@Test
	public void updAdminUserRoleByUid() throws Exception{
		//设置更新参数
		long uid 			= Long.parseLong("2");
		long roleId 	= Long.parseLong("3");
		
		//调用更新接口
		int updRet = adminUserDao.updAdminUserRoleByUid(uid, roleId);
		System.out.println("Test updAdminUserRoleByUid Ret: "+ updRet);
	}
	
	/**
	 * 更具用户名查询数据
	 * 
	 *＠param addTime 2017-08-22
	 *@param author   ChengBo
	 */
	@Test
	public void getAdminUserByUserName() throws Exception{
		//设置查询用户名
		String username = "admin002";
		
		//查询用户数据
		AdminUser	userInfo = adminUserDao.getAdminUserByUserName(username);
		System.out.println("getAdminUserByUserName ret: "+userInfo);
	}
	
	/**
	 * 获取用户数据列表
	 * 
	 *＠param addTime 2017-08-2３
	 *@param author   ChengBo
	 */
	@Test
	public void getUserInfoList() throws Exception{
		//设置查询参数
		String username = "";
		Integer page = 0;
		Integer pageSize = 2;
		
		//查询数据
		List<AdminUser> userList = adminUserDao.getAdminUserList(username, page, pageSize);
		for(AdminUser userInfo : userList){
			System.out.println("-++++++- "+ userInfo);
		}
	}
	
	/**
	 * 测试　获取用户总条数
	 * 
	 *＠param addTime 2017-08-23
	 *@param author   ChengBo
	 */
	@Test
	public void getTotalUserNum() throws Exception{
		//设置查询用户
		String username = "";
		
		//查询数据
		int totalNum = adminUserDao.getTotalUserNum(username);
		System.out.println("===========+"+totalNum);
	}
	
	/**
	 * 测试　更新管理用户
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 */
	@Test
	public void testUpdAdminUserByUid(){
		//实例化更新Bean
		AdminUser adminUser = new AdminUser();
		
		//设置更新参数
		adminUser.setUid(116);
		adminUser.setUsername("adminTest003001");
		adminUser.setRealname("测试003-----");
		adminUser.setPassword("9a67d672314dce6024d39b8026cfa6aa");
		adminUser.setStatus(-1);
		
		boolean ret = adminUserDao.updAdminUserByUid(adminUser);
		logger.debug("===== {}", ret);
	}
}
