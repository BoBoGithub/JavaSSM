package com.migang.admin.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.UserLinkMan;

/**
 * 用户联系人信息Dao测试
 *
 *@param addTime 2017-09-26
 *@param author    ChengBo
 */
public class UserLinkManDaoTest extends BaseTest {

	@Autowired
	private UserLinkManDao userLinkManDao;
	
	/**
	 * 测试新增用户联系人信息
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserLinkManInfo(){
		//实例化联系人Bean
		UserLinkMan userLinkMan = new UserLinkMan();
		
		//设置联系人信息
		userLinkMan.setUid(1);
		userLinkMan.setName("王二1");
		userLinkMan.setPhone("15613187762");
		userLinkMan.setRelation(1);
		userLinkMan.setCtime(CommonUtils.getCurrTimeStamp());
		userLinkMan.setRemark("备注1");
		
		//数据入库
		Boolean lastInsertId = userLinkManDao.insertUserLinkManInfo(userLinkMan);
		System.out.println("=== "+lastInsertId+" "+ userLinkMan.getLinkManId());
	}
}
