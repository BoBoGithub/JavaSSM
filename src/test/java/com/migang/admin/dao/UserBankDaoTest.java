package com.migang.admin.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.UserBank;

/**
 * 用户UserBankDao单元测试
 *
 * @param addTime 2017年9月27日
 * @param author     ChengBo
 */
public class UserBankDaoTest extends BaseTest{

	@Autowired
	private UserBankDao userBankDao;
	
	/**
	 * 测试 用户银行卡信息入库
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserBankInfo(){
		//实例化用户银行卡Bean
		UserBank userBank = new UserBank();
		
		//设置入库参数
		userBank.setUid(1);
		userBank.setBankName("中国农业银行");
		userBank.setBankNum("6228489887898789878");
		userBank.setMobile("15613187762");
		userBank.setBankProvince(2);
		userBank.setBankCity(3312);
		userBank.setCardType(1);
		userBank.setCtime(CommonUtils.getCurrTimeStamp());

		//数据入库
		Boolean ret = userBankDao.insertUserBankInfo(userBank);
		System.out.println("=== "+ret+" "+userBank.getBankid());
	}
	
	/**
	 * 测试后去用户银行卡信息
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetUserBankByUid(){
		//设置用户uid
		long uid = 1;
		
		//获取用户信息
		UserBank userBank = userBankDao.getUserBankByUid(uid);
		System.out.println("=== "+ userBank);
	}
}
