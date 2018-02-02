package com.migang.admin.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.UserBase;

/**
 * 用户UserDao单元测试
 *
 * @param addTime 2017年9月25日
 * @param author     ChengBo
 */
public class UserBaseDaoTest extends BaseTest {

	@Autowired
	private UserBaseDao userBaseDao;
	
	/**
	 * 测试插入用户基本信息
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertUserBaseInfo(){
		//实例化用户基本信息类
		UserBase userBase = new UserBase();
		
		//设置入库信息
		userBase.setUid(1);
		userBase.setDegree(0);
		userBase.setMarriage(0);
		userBase.setChild(0);
		userBase.setProvince(6);
		userBase.setCity(130);
		userBase.setDistrict(545);
		userBase.setAddress("中华南大街132号 盛景大厦3#6102");
		userBase.setLiveTime(0);
		userBase.setWechat("wx_"+CommonUtils.getRandomString(6));
		userBase.setEmail("test@migang.com");
		userBase.setRealname("王二");
		userBase.setSex(1);
		userBase.setCardNum("360402197104020013");
		userBase.setCardAddress("北京市 朝阳区 畅青园3#2717");
		userBase.setCardNation("汉族");
		userBase.setCardOffice("北京市海淀公安局");
		userBase.setCardExpire("2001.10.1-2020.10.1");
		userBase.setCardPic("{\"zm\":\"/upload/zm.jpg\", \"fm\":\"/upload/fm.jpg\"}");
			
		try{
			//基本信息入库
			Boolean ret = userBaseDao.insertUserBaseInfo(userBase);
			System.out.println("==="+ret+" "+userBase);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
