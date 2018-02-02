package com.migang.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.CryptoUtils;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.dao.AdminUserDao;
import com.migang.admin.entity.AdminUser;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.AdminUserService;

/**
 * 管理用户的业务处理类
 *
 *＠param addTime 2017-08-21
 *@param author  ChengBo
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

	//引入操作用户Dao
	@Autowired
	private AdminUserDao adminUserDao;
	
	public AdminUser getAdminUserInfoByUid(long uid) throws BusinessException {
		//检查用户参数
		if(!ValidateUtils.checkIsNumber(uid)){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "管理用户不存在");
		}
		
		//调取管理用户数据
		AdminUser adminUserInfo = adminUserDao.getAdminUserByUid(uid);
		
		return adminUserInfo;
	}

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param String username
	 * 
	 */
	public AdminUser getAdminUserInfoByUserName(String username) {
		//检查用户名参数
		if(StringUtils.isEmpty(username)){
			//TODO 稍后把全局异常处理加进来　这里直接跑异常{错误码+信息}
			return null;
		}
		
		//获取用户数据
		AdminUser userInfo = adminUserDao.getAdminUserByUserName(username);
		
		//返回结果
		return userInfo;
	}

	/**
	 * 获取用户列表数据
	 * 
	 * @param String username
	 * @param int page
	 * @param int pageSize
	 * 
	 * @param addTime 2017-08-23
	 * @param author    ChengBo
	 */
	public List<AdminUser> getAdminUserList(String username, Integer page, Integer pageSize){
		//设置参数
		username = ValidateUtils.checkUserName(username) ? username : "";
		
		//设置页数偏移
		page			= ValidateUtils.checkIsNumber(page) ? page : 1;
		pageSize	= ValidateUtils.checkIsNumber(pageSize) ? pageSize : 10;
		
		//调取用户列表
		List<AdminUser> userList = adminUserDao.getAdminUserList(username, (page-1)*pageSize, pageSize);
		
		return userList;
	}

	/**
	 * 获取用户的总条数
	 * 
	 * @param String username
	 * 
	 * @param addTime 2017-08-23
	 * @param author    ChengBo
	 */
	public int getTotalUserNum(String username) {
		//设置参数
		username = ValidateUtils.checkUserName(username) ? username : "";
		
		//查询用户总条数
		int totalNum = adminUserDao.getTotalUserNum(username);
		
		return totalNum;
	}
	
	/**
	 * 新增用户
	 * 
	 * @param username
	 * @param password
	 * @param realname
	 * @param roleId
	 * @return
	 */
	public int addAdminUser(String username, String password, String realname, Integer roleId){
		//TODO 检查参数
		
		//生成加盐串
		String saltStr = CommonUtils.getRandomString(6);
		
		//这是密码
		password = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5(password) + saltStr);
		
		//实例化用户实体
		AdminUser userInfo = new AdminUser();
		
		//设置用户数据
		userInfo.setUsername(username);
		userInfo.setPassword(password);
		userInfo.setRealname(realname);
		userInfo.setEmail(username+"@migang.com");
		userInfo.setEncrypt(saltStr);
		userInfo.setRoleId(roleId);
		
		//实例化时间类
		Date ctime = new Date();
		
		//格式化时间
		SimpleDateFormat dateStyle = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//设置入库时间
		userInfo.setCtime(dateStyle.format(ctime));

		//新增用户入库
		int ret = adminUserDao.insertAdminUserInfo(userInfo);
		
		return ret;
	}
	
	/**
	 * 调取指定角色的用户列表
	 * 
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @return
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public List<AdminUser> getAdminUserListByRoleId(Integer roleId, Integer page, Integer pageSize) throws BusinessException{
		//检查角色id
		if(!ValidateUtils.checkIsNumber(roleId)){
			throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_ID_PARAM, "用户角色参数错误");
		}
		
		//调取用户列表
		List<AdminUser> userList = adminUserDao.getAdminUserListByRoleId(roleId, (page-1)*pageSize, pageSize);
		
		return userList;
	}
	
	/**
	 * 获取用户的总条数
	 * 
	 * @param String username
	 * 
	 * @param addTime 2017-08-23
	 * @param author    ChengBo
	 */
	public int getTotalUserNumByRoleId(Integer roleId) {
		//TODO 检查参数
		
		//查询用户总条数
		int totalNum = adminUserDao.getTotalUserNumByRoleId(roleId);
		
		return totalNum;
	}
	
	/**
	 * 更新管理用户的状态
	 * 
	 * @param adminUid
	 * @param status
	 * @return
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public boolean updAdminUserByUid(Map<String, String> adminUserMap) throws BusinessException{
		//设置用户实体Bean
		AdminUser adminUser = checkUpdAdminUserByUid(adminUserMap);
		
		//更新用户数据
		return adminUserDao.updAdminUserByUid(adminUser);
	}
	
	//检查管理用户更新参数
	private AdminUser checkUpdAdminUserByUid(Map<String, String> adminUserMap) throws BusinessException{
		//检查更新参数
		if(adminUserMap == null){
			throw new BusinessException(CommonConstant.ADMIN_USER_PARAM_ERROR, "更新参数错误");
		}
		
		//设置更新用户Bean
		AdminUser adminUser = new AdminUser();
		
		//检查用户UID
		if(!adminUserMap.containsKey("uid") || !ValidateUtils.checkIsNumber(adminUserMap.get("uid"))){
			throw new BusinessException(CommonConstant.ADMIN_USER_UID_PARAM_ERROR, "用户UID参数错误");
		}else{
			adminUser.setUid(Integer.parseInt(adminUserMap.get("uid")));
		}
		
		//检查更新状态
		if(adminUserMap.containsKey("status")){
			if(!ValidateUtils.checkIsNumber(adminUserMap.get("status"))){
				throw new BusinessException(CommonConstant.ADMIN_USER_STATUS_PARAM_ERROR, "用户状态参数错误");
			}else{
				adminUser.setStatus(Integer.parseInt(adminUserMap.get("status")));
			}
		}
		
		//检查用户角色
		if(adminUserMap.containsKey("roleId")){
			if(!ValidateUtils.checkIsNumber(adminUserMap.get("roleId"))){
				throw new BusinessException(CommonConstant.ADMIN_USER_ROLE_ID_PARAM, "用户角色参数错误");
			}else{
				adminUser.setRoleId(Integer.parseInt(adminUserMap.get("roleId")));
			}
		}
		
		//检查用户名
		if(adminUserMap.containsKey("userName")){
			if(!ValidateUtils.checkUserName(adminUserMap.get("userName"))){
				throw new BusinessException(CommonConstant.ADMIN_USER_NAME_PARAM_ERROR, "用户名参数错误");
			}else{
				adminUser.setUsername(adminUserMap.get("userName"));
			}
		}
		
		//检查真实姓名
		if(adminUserMap.containsKey("realName")){
			if(adminUserMap.get("realName").isEmpty()){
				throw new BusinessException(CommonConstant.ADMIN_REAL_NAME_PARAM_ERROR, "用户真实姓名参数错误");
			}else{
				adminUser.setRealname(adminUserMap.get("realName"));
			}
		}
		
		//检查用户名密码
		if(adminUserMap.containsKey("password")){
			if(adminUserMap.get("password").isEmpty()){
				throw new BusinessException(CommonConstant.ADMIN_REAL_NAME_PARAM_ERROR, "用户密码参数错误");
			}else{
				//提取设置密码加密数据
				Map<String, String> pwdMap = createAdminUserEditPwd(adminUserMap.get("password"));

				//设置更新密码参数
				adminUser.setPassword(pwdMap.get("pwd"));
				adminUser.setEncrypt(pwdMap.get("encrypt"));
			}
		}
		
		return adminUser;
	}
	
	/**
	 * 生成编辑用户的密码
	 * 
	 * @param pwd
	 * @return
	 * 
	 * @param addTime 2017年10月24日
	 * @param author     ChengBo
	 */
	private Map<String, String> createAdminUserEditPwd(String pwd){
		//实例化返回结果
		Map<String, String>	pwdMap = new HashMap<String, String>();

		//生成加盐串
		String saltStr = CommonUtils.getRandomString(6);
		
		//设置返回参数
		pwdMap.put("pwd", CryptoUtils.encodeMD5(CryptoUtils.encodeMD5(pwd) + saltStr));
		pwdMap.put("encrypt", saltStr);
		
		return pwdMap;
	}
	
	/**
	 * 更新管理用户信息
	 * 
	 * @param updMap
	 * @return
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public Boolean updAdminInfo(Map<String, String> updMap) throws BusinessException{
		//检查用户参数
		if(!updMap.containsKey("adminUid") || !ValidateUtils.checkIsNumber(updMap.get("adminUid"))){
			throw new BusinessException(CommonConstant.USER_UID_ERROR, "用户已退出登陆");
		}
		
		//检查用户真实姓名
		if(!updMap.containsKey("realName") || updMap.get("realName").isEmpty()){
			throw new BusinessException(CommonConstant.USER_REAL_NAME_ERROR, "用户真实姓名错误");
		}
		
		//检查用户邮箱
		if(!updMap.containsKey("email") || !ValidateUtils.checkEmail(updMap.get("email"))){
			throw new BusinessException(CommonConstant.USER_EMAIL_ERROR, "用户邮箱地址错误");
		}
	
		//实例化管理用户Bean
		AdminUser adminUser = new AdminUser();
		
		//设置更新参数
		adminUser.setUid(Integer.parseInt(updMap.get("adminUid")));
		adminUser.setRealname(updMap.get("realName"));
		adminUser.setEmail(updMap.get("email"));
		
		//更新管理用户信息
		Boolean updRet = adminUserDao.updAdminUserByUid(adminUser);
		if(updRet){
			//更新缓存
			
		}
		
		return updRet;
	}
}
