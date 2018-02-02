package com.migang.admin.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.CryptoUtils;
import com.migang.admin.entity.User;


/**
 * 用户UserDao单元测试
 *
 * @param addTime 2017年9月5日
 * @param author     ChengBo
 */
public class UserDaoTest extends BaseTest {

	//引入用户操作注解
	@Autowired
	private UserDao userDao;
	
	/**
	 * 测试根据uid获取用户信息
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetUserByUid(){
		long uid = 1;
		User user = userDao.getUserByUid(uid);
		logger.debug("==== testGetUserByUid ==== "+ user);
	}
	
	/**
	 * 测试 根据用户名查询用户信息
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetUserByUserName(){
		String userName = "15613187762";
		User user = userDao.getUserByUserMobile(userName);
		logger.debug("==== getGetUserByUserName ===== "+user);
	}
	
	/**
	 * 测试　新增用户接口
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserInfo(){
		//设置混淆字符串
		String saltStr = CommonUtils.getRandomString(6);
		
		//设置密码
		String password = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5("123456")+saltStr);
		
		//生成随机数
		int randNum = (int) (Math.random()*100);
		
		//设置用户数据
		User user = new User();
		user.setPassword(password);
		user.setEncrypt(saltStr);
		user.setMobile("15613187"+randNum);
		user.setCtime(CommonUtils.getCurrTimeStamp());
		user.setIp("127.0.0.1");

		//测试入库
		long uid = 0;//userDao.insertUserInfo(user);
		logger.debug("===== insertStatus: {} , uid: {} ======", uid, user.getUid());
	}
	
	/**
	 * 测试　查询借款用户列表
	 * 
	 * @param addTime 2017年10月9日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetUserList(){
		//实例化用户实体
		User userInfo = new User();
		
		//设置查询条件
//		userInfo.setMobile("15613187762");
//		userInfo.setShenfenAuth(1);
//		userInfo.setRegStartTime(100);
//		userInfo.setRegEndTime(1507543542);
		userInfo.setOffset(2);
		userInfo.setPageSize(3);
		
		logger.debug("===== "+ userInfo);
		
		//查询数据
		List<User> userList = userDao.getUserList(userInfo);
		logger.debug("===== {}", userList);
	}
}
