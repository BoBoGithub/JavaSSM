package com.migang.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.User;
import com.migang.admin.entity.UserBank;
import com.migang.admin.entity.UserBase;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.UserService;

/**
 * 测试用户Service类
 *
 *@param addTime 2017-09-25
 *@param author    ChengBo
 */
public class UserServiceImpTest extends BaseTest {

	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
//	private UserServiceImpl userServiceImpl;
	
	/**
	 * 测试新增用户基本信息
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserBaseInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		//设置入库参数
		map.put("uid", 3);
		map.put("degree", null);
		map.put("marriage", 1);
		map.put("child", 2);
		map.put("province", "北京");
		map.put("city", "北京市");
		map.put("district", "朝阳区");
		map.put("address", "畅青园");
		map.put("liveTime", 3);
		map.put("wechat", "wx_"+CommonUtils.getRandomString(6));
		map.put("realname", "王二");
		map.put("cardnum", "360402197104020013");
		map.put("cardpic", "{\"zm\":\"/upload/zm.jpg\", \"fm\":\"/upload/fm.jpg\"}");

		//数据入库
		try {
			Boolean ret = userService.insertUserBaseInfo(map);
			logger.debug("=== {}", ret);
		} catch (BusinessException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试新增用户职业信息
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserJobInfo(){
		//实例化职业信息Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//设置用户职业信息
		map.put("uid", 1);
		map.put("jobName", "数据分析师");
		map.put("company", "中国人民银行");
		map.put("income", 1);
		map.put("province", "北京");
		map.put("city", "北京市");
		map.put("district", "朝阳区");
		map.put("address", "畅青园");
		map.put("phone", "010-898767876");
		
		//职业信息入库
		try{
			Boolean ret = userService.insertUserJobInfo(map);
			System.out.println("==="+ ret);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试新增用户联系人信息
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * 
	 * @throws BusinessException 
	 */
	@Test
	public void testInsertLinkManInfo() throws BusinessException{
		//实例化操作联系人Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//设置参数
		map.put("uid", 1);
		map.put("name", "王二2");
		map.put("phone", "010-89878987");
		map.put("relation", 0);
		map.put("ctime", CommonUtils.getCurrTimeStamp());
		map.put("remark", "备注２");
		
		//数据入库
		Boolean ret = userService.insertUserLinkManInfo(map);
		System.out.println("=== "+ret);
	}

	/**
	 * 测试更新用户联系人信息
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testUpdateUserContactsAuth() throws BusinessException{
		//更新数据
//		Boolean ret = userServiceImpl.updateUserContactsAuth(1);
//		System.out.println("=== "+ret);
	}
	
	/**
	 * 测试 用户银行卡绑定
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * 
	 * @throws BusinessException 
	 */
	@Test
	public void testAddUserBank() throws BusinessException{
		//设置绑定数据
		Map<String, String> bankMap = new HashMap<String, String>();
		
		//设置入库参数
		bankMap.put("uid", "1");
		bankMap.put("bankNum", "6229489878787878787");
		bankMap.put("mobile", "15613187762");
		bankMap.put("bankName", "中国农业银行");
		bankMap.put("bankProvince", "139");
		bankMap.put("bankCity", "545");
		
		//绑定入库
		Boolean ret = userService.authAddUserBank(bankMap);
		System.out.println("=== "+ ret);
	}
	
	/**
	 * 测试　获取用户银行卡信息 
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetUserBankByUid() throws BusinessException{
		//设置查询用户uid
		long uid = 1;
		
		//查询数据
		UserBank userBank = userService.getUserBankByUid(uid);
		System.out.println("=== "+ userBank);
	}
	
	/**
	 * 测试　查询借款用户列表数据
	 * 
	 * @param addTime 2017年10月10日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetUserList(){
		//实例化查询map
		Map<String, String> selectMap = new HashMap<String, String>();
		
		//设置查询参数
//		selectMap.put("uid", "1");
//		selectMap.put("mobile", "15613187762");
//		selectMap.put("shenfenAuth", "1");
//		selectMap.put("huotiAuth", "1");
	
		try{
			//查询数据
			List<User> userList = userService.getUserList(selectMap);
			for(User user : userList){
				logger.debug("=== " + user);
			}
		}catch(Exception ex){
			logger.debug("=== "+ ex.getMessage());
		}
	}
	
	/**
	 * 测试 通过uids查询用户信息
	 * 
	 * @param addTime 2017年10月12日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetUserByUids() throws BusinessException{
		//设置查询UserDto 
		List<Long> uidList = new ArrayList<Long>();
		
		//设置查询条件
		uidList.add((long) 1);
		uidList.add((long) 2);
		uidList.add((long) 26);
		
		//查询数据
		Map<Long, User> userMap = userService.getUserDataByUids(uidList);
		logger.debug("=== {}", userMap);
	}
	
	/**
	 * 测试 通过uids查询用户基本信息
	 * 
	 * @param addTime 2017年10月12日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetUserBaseByUids() throws BusinessException{
		//设置查询UserDto 
		List<Long> uidList = new ArrayList<Long>();
		
		//设置查询条件
		uidList.add((long) 1);
		uidList.add((long) 2);
		uidList.add((long) 26);
		
		//查询数据
		Map<Long, UserBase> userMap = userService.getUserBaseDataByUids(uidList);
		logger.debug("=== {}", userMap);
	}
}