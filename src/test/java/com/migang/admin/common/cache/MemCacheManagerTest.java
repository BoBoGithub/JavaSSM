package com.migang.admin.common.cache;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.entity.AdminUser;
import com.migang.admin.service.AdminUserService;

/**
 * 测试MemCache缓存
 *
 * @param addTime 2017-09-13
 * @param author     ChengBo
 */
public class MemCacheManagerTest extends BaseTest {

	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private MemCacheManager memCache;
	
	/**
	 * 设置缓存 对象数据
	 * 
	 * @param addTime 2017年9月13日
	 * @param author     ChengBo
	 */
	@Test
	public void testSet(){
		//设置查询用户
		String username = "admin002";
		
		//查询用户
		AdminUser userInfo = adminUserService.getAdminUserInfoByUserName(username);
		logger.debug("++++++++RedisCacheManagerTest  testGetAdminUserByUserName userInfo: "+userInfo);
		
		String key = "testMemCacheObjectKey";

		//设置缓存
//		memCache.set(key, userInfo, 30);

		//读取缓存
		AdminUser ret = (AdminUser)memCache.get(key);
		if(ret != null){
			logger.info("========== RedisCacheManagerTest Obj:  ============= "+ ret.getUsername());
		}else{
			logger.debug("== no cache data");
		}
	}	
	
	/**
	 * 测试　获取活跃的keys
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testGetActiveKeys() throws UnsupportedEncodingException{
		ArrayList<String> list = memCache.getActiveKeys("app_user_login_token");
		for(int i = 0; i < list.size();i++){
			String key = URLDecoder.decode(list.get(i), "utf-8");
			logger.debug("======="+key);
			Object obj = memCache.get("c7366aa8e74faac489e8156178e370b2");
			logger.debug("===="+obj);
		}
	}
	
	@Test
	public void testFlushDb(){
//		memCache.flushDb();
	}
}
