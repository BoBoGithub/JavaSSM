package com.migang.admin.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.UserJob;

/**
 * 用户UserJobDao单元测试
 *
 * @param addTime 2017年9月25日
 * @param author     ChengBo
 */
public class UserJobDaoTest extends BaseTest {

	@Autowired
	private UserJobDao userJobDao;
	
	/**
	 * 测试用户职业信息入库
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserJobInfo(){
		//实例化实体Bean
		UserJob userJob = new UserJob();
		
		//设置入库参数
		userJob.setUid(1);
		userJob.setJobName("Java开发工程师");
		userJob.setIncome(20000);
		userJob.setCompany("中国人民银行");
		userJob.setProvince(2);
		userJob.setCity(545);
		userJob.setDistrict(120);
		userJob.setAddress("畅青园小区8#10#1102");
		userJob.setPhone("010-89878767");
		userJob.setCtime(CommonUtils.getCurrTimeStamp());
		
		//职业信息入库
		try{
			Boolean ret = userJobDao.insertUserJobInfo(userJob);
			System.out.println("=== "+ ret);
		}catch(Exception e){
			System.out.println("=== test insert failue");
		}
	}
}
