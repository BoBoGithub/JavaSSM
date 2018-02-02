package com.migang.admin.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.cache.MemCacheManager;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.CryptoUtils;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.dao.UserBankDao;
import com.migang.admin.dao.UserBaseDao;
import com.migang.admin.dao.UserDao;
import com.migang.admin.dao.UserJobDao;
import com.migang.admin.dao.UserLinkManDao;
import com.migang.admin.entity.User;
import com.migang.admin.entity.UserBank;
import com.migang.admin.entity.UserBase;
import com.migang.admin.entity.UserJob;
import com.migang.admin.entity.UserLinkMan;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.AuthService;
import com.migang.admin.service.MobileMessageService;
import com.migang.admin.service.UserService;

/**
 * 前端用户操作Service类
 *
 * @param addTime 2017年9月5日
 * @param author     ChengBo
 */
@Service
public class UserServiceImpl implements UserService {

	//设置日志操作
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	//设置用户缓存前缀
	public static final String USER_INFO_CACHE_PREFIX = "front_user_login_info_cache:";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserBaseDao userBaseDao;
	
	@Autowired
	private UserJobDao userJobDao;
	
	@Autowired
	private UserLinkManDao userLinkManDao;
	
	@Autowired
	private UserBankDao userBankDao;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private MemCacheManager memCache;
	
	@Autowired
	private MobileMessageService mobileMsgService;
	
	/**
	 * 通过用户uid获取用户数据
	 * 
	 * @param uid
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 * 
	 * @throws BusinessException 
	 */
	@Override
	public Map<String, Object> getUserByUid(long uid) throws BusinessException {
		//检查参数
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户相关信息错误");
		}

		//设置返回结果Map
		Map<String, Object> userMap = new HashMap<String, Object>();
		
		//调取用户信息
		User userInfo = userDao.getUserByUid(uid);
		
		//设置返回结果
		userMap.put("uid", uid);
		userMap.put("mobile", userInfo.getMobile());
		userMap.put("realName", "测试数据");
		
		return userMap;
	}

	@Override
	public User getUserByUserMobile(String mobile) throws BusinessException{
		//检查用户用户名
		if(!ValidateUtils.checkMobile(mobile)){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "手机号格式错误");
		}
		
		//调取用户数据
		User user = userDao.getUserByUserMobile(mobile);
		
		return user;
	}

	@Override
	public int insertUserInfo(User user) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 用户登陆相关操作
	 * 
	 * @param mobile
	 * @param password
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 * @throws Exception 
	 */
	public long doUserLogin(String mobile, String password) throws BusinessException{
		//检查用户用户名
		if(!ValidateUtils.checkMobile(mobile) || !ValidateUtils.checkPassword(password)){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或密码格式错误");
		}
		
		//检查登陆次数　TODO
		
		//调取用户数据
		User user = getUserByUserMobile(mobile);
		if(user == null){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "手机号 或 密码错误");
		}
		
		//加密用户密码
		String encodePwd = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5(password)+user.getEncrypt());

		//检查用户密码是否正确
		if(!user.getPassword().equals(encodePwd)){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "手机号或密码错误");
		}

		//设置登陆缓存Key
		String userUniqueKey = getUserUniqueKey(user.getUid());
		
		//设置用户登陆缓存
		memCache.set(userUniqueKey, user, CommonConstant.APP_USER_SESSION_EXPIRE_TIME);
		
		return user.getUid();
	}

	 /**
	  * 用户退出操作
	  * 
	  * @param username
	  * @param loginUniqueKey
	  * 
	  * @param addTime 2017年9月5日
	  * @param author     ChengBo
	  */
	public void userLogout(String loginUniqueKey){
		//设置登陆缓存Key
		String loginCacheKey = USER_INFO_CACHE_PREFIX + loginUniqueKey;
		
		//清楚用户缓存信息
		memCache.delete(loginCacheKey);	
	}
	
	 /**
	  * 通过唯一标示获取用户缓存数据
	  * 
	  * @param loginUniqueKey
	  * 
	  * @param addTime 2017年9月5日
	  * @param author     ChengBo
	  */
	public User getUserInfoByUniqueKey(String loginUniqueKey){
		//检查参数
		if(loginUniqueKey == null || loginUniqueKey.equals("")){
			return null;
		}
		
		//设置登陆缓存Key
		String loginCacheKey = USER_INFO_CACHE_PREFIX + loginUniqueKey;
		
		//获取缓存数据
		return  (User) memCache.get(loginCacheKey);
	}

	/**
	 * 用户注册操作
	 * 
	 * @param mobile
	 * @param password
	 * @param captcha
	 * @param ip
	 * 
	 * @return Integer
	 * 
	 * @param addTime 2017年9月19日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public long userReg(String mobile, String password, String captcha, String ip) throws BusinessException{
		//检查参数
		if(!ValidateUtils.checkMobile(mobile) || !ValidateUtils.checkPassword(password)){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或密码格式错误");
		}
		
		//验证短信验证码
		if(!ValidateUtils.checkCaptcha(captcha) || !mobileMsgService.checkCaptchaCode(mobile, CommonConstant.MOBILE_MSG_TYPE_REG, captcha)){
			throw new BusinessException(CommonConstant.MOBILE_CAPTCHA_ERROR, "短信验证码错误");
		}
		
		//设置注册IP
		String regIp = ValidateUtils.checkIp(ip) ? ip : "127.0.0.1";
		
		//检查是否已注册
		User userInfo = userDao.getUserByUserMobile(mobile);
		if(userInfo != null){
			throw new BusinessException(CommonConstant.USER_HAS_EXISTS, "该用户已注册");
		}
		
		//设置混淆字符串
		String saltStr = CommonUtils.getRandomString(6);
		
		//设置密码
		String md5Pwd = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5(password)+saltStr);

		//设置注册参数
		User user = new User();
		user.setMobile(mobile);
		user.setPassword(md5Pwd);
		user.setEncrypt(saltStr);
		user.setCtime(CommonUtils.getCurrTimeStamp());
		user.setIp(regIp);
		
		try{
			//注册用户数据
			userDao.insertUserInfo(user);
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl userReg exception: " + ex.getMessage());
			
			throw new BusinessException(CommonConstant.USER_REG_FAILURE, "用户注册失败");
		}
		
		//返回新增的用户uid
		return user.getUid();
	}

	/**
	 * 设置用户缓存
	 * 
	 * @param uid
	 * @param cacheKey
	 * @param token
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public void setUserSession(long uid, String cacheKey, String token) throws BusinessException {
		//检查参数
		if(uid == 0 || cacheKey.isEmpty() || token.isEmpty()){
			throw new BusinessException(CommonConstant.USER_SET_CACHE_PARAM_ERROR, "用户相关信息错误");
		}
		
		//设置当前时间戳
		long timeStamp = CommonUtils.getCurrTimeStamp();
		
		//设置用户信息Map
		Map<String, Object> map = new HashMap<String ,Object>();
		
		//设置用户信息
		map.put("token",	token);
		map.put("cacheKey", cacheKey);
		map.put("activeTime", timeStamp);
		
		//设置用户Token和用户对应关系缓存
		memCache.set(cacheKey, uid, CommonConstant.APP_USER_DATA_CACHE_EXPIRE_TIME);
		
		//设置用户Token和活跃时间
		memCache.set(CommonConstant.APP_USER_lOGIN_TOKNE_CACHE_PREFIX+uid, map, CommonConstant.APP_USER_DATA_CACHE_EXPIRE_TIME);
	}
	
	/**
	 * 从用户登陆标示中提取uid
	 * 
	 * @param cacheKey
	 * @return
	 * 
	 * @param addTime 2017年9月22日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public long getUidFromLoginCache(String cacheKey) throws BusinessException{
		//检查参数
		if(cacheKey.isEmpty()){
			throw new BusinessException(CommonConstant.USER_SET_CACHE_PARAM_ERROR, "用户相关信息错误");
		}
		
		//获取用户uid数据
		Object uidObj = memCache.get(cacheKey);
		if(uidObj == null){
			throw new BusinessException(CommonConstant.USER_NOT_LOGIN, "用户未登录");
		}
		
		//提取用户uid
		return Long.parseLong(uidObj.toString());
	}
	
	 /**
	  * 检查用户的Token 并更新有效时间
	  * 
	  * @param cacheKey
	  * @param token
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年9月21日
	  * @param author     ChengBo
	 * @return 
	  */
	public long checkUserToken(String cacheKey, String token) throws BusinessException{
		//检查参数
		if(cacheKey.isEmpty() || token.isEmpty()){
			throw new BusinessException(CommonConstant.USER_SET_CACHE_PARAM_ERROR, "用户相关信息错误");
		}
		
		//提取用户uid
		long uid = getUidFromLoginCache(cacheKey);
		if(uid <= 0){
			throw new BusinessException(CommonConstant.USER_NOT_LOGIN, "用户未登录");
		}
		
		//获取Token缓存数据
		@SuppressWarnings("unchecked")
		Map<String, Object> userLoginTokenMap = (Map<String, Object>) memCache.get(CommonConstant.APP_USER_lOGIN_TOKNE_CACHE_PREFIX+uid);
		
		//检查token是否匹配
		if(userLoginTokenMap == null || !userLoginTokenMap.containsKey("token") || !token.equals(userLoginTokenMap.get("token"))){
			throw new BusinessException(CommonConstant.COMMON_TOKEN_VALIDATE_ERROR, "Token验证错误");
		}
		
		//设置当前时间戳
		long timeStamp = CommonUtils.getCurrTimeStamp();
		
		//获取用户上次活跃时间戳
		long lastActiveTimeStamp = Long.valueOf(userLoginTokenMap.get("activeTime").toString());

		//检查用户登陆状态过期时间
		if(timeStamp - lastActiveTimeStamp > CommonConstant.APP_USER_SESSION_EXPIRE_TIME){
			throw new BusinessException(CommonConstant.USER_NOT_LOGIN, "用户未登录");
		}
		
		//更新缓存会话活跃时间
		userLoginTokenMap.put("activeTime", timeStamp);
		
		//设置缓存
		memCache.set(CommonConstant.APP_USER_lOGIN_TOKNE_CACHE_PREFIX+uid, userLoginTokenMap, CommonConstant.APP_USER_DATA_CACHE_EXPIRE_TIME);
		
		//返回当前用户的uid
		return uid;
	}
	
	 /**
	  * 处理用户退出操作
	  * 
	  * @param uid
	  * @return
	  * 
	  * @param addTime 2017年9月22日
	  * @param author     ChengBo
	 * @throws BusinessException 
	  */
	public Boolean doUserLogout(long uid) throws BusinessException{
		//检查用户uid
		if(uid <= 0){
			throw new BusinessException(CommonConstant.USER_SET_CACHE_PARAM_ERROR, "用户相关信息错误");
		}
				
		//获取用户登陆缓存
		@SuppressWarnings("unchecked")
		Map<String, Object> userLoginTokenCache = (Map<String, Object>) memCache.get(CommonConstant.APP_USER_lOGIN_TOKNE_CACHE_PREFIX+uid);
		
		//设置用户登陆缓存key
		String userLoginCackeKey = (String) userLoginTokenCache.get("cacheKey");
		
		//清楚用户登陆缓存
		return memCache.delete(userLoginCackeKey);
	}
	
	/**
	 * 新增用户基本信息数据
	 * 
	 * @param map
	 * @return
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public Boolean insertUserBaseInfo(Map<String, Object> map) throws BusinessException{
		//检查提取入库参数
		UserBase userBase = checkInsertUserBaseInfo(map);

		try{
			//基本信息入库
			Boolean ret = userBaseDao.insertUserBaseInfo(userBase);
			
			//处理后续更新
			if(ret){
				//设置更新参数
				User userInfo = new User();
				userInfo.setUid(userBase.getUid());
				userInfo.setProvince(userBase.getProvince());
				userInfo.setCity(userBase.getCity());
				userInfo.setDistrict(userBase.getDistrict());
				userInfo.setPersonAuth(CommonUtils.getCurrTimeStamp());
				
				//更新用户信息认证数据＋所在地数据
				updateUserBaseInfo(userInfo);
			}
			
			return ret;
		}catch(Exception ex){
			//记录失败日志
			logger.error("UserService insertUserBaseInfo exception: "+ ex.getMessage());
			
			//抛出插入失败异常
			throw new BusinessException(CommonConstant.USER_BASE_INFO_INSERT_FAILURE, "用户基本信息提交失败");
		}
	}
	
	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 * 
	 * @param addTime 2017年10月31日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private Boolean updateUserBaseInfo(User userInfo) throws BusinessException{
		//检查参数
		if(userInfo == null){
			throw new BusinessException(CommonConstant.USER_BASE_INFO_PARAM_ERROR, "用户个人信息参数错误");
		}
		
		//更新数据
		return userDao.updateUserInfoByUid(userInfo);
	}
	
	/**
	 * 检查用户基本信息入库参数
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	private UserBase checkInsertUserBaseInfo(Map<String, Object> map) throws BusinessException{
		//检查参数
		if(map == null){
			throw new BusinessException(CommonConstant.USER_BASE_INFO_PARAM_ERROR, "用户个人信息参数错误");
		}

		//实例化用户基本信息类
		UserBase userBase = new UserBase();

		//检查用户
		if(map.containsKey("uid") && Long.parseLong(map.get("uid").toString()) > 0){
			//设置用户uid
			userBase.setUid(Long.parseLong(map.get("uid").toString()));
		}else{
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}

		//检查学历
		if(map.containsKey("degree") && map.get("degree") != null){
			if(Arrays.asList(new String[]{"0","1", "2", "3", "4"}).contains(map.get("degree").toString())){
				//设置学历
				userBase.setDegree(Integer.parseInt(map.get("degree").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_DEGREE_ERROR, "学历填写错误");
			}
		}
		
		//检查婚姻
		if(map.containsKey("marriage") && map.get("marriage") != null){
			if(Arrays.asList(new String[]{"0","1"}).contains(map.get("marriage").toString())){
				//设置婚姻
				userBase.setMarriage(Integer.parseInt(map.get("marriage").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_MARRIAGE_ERROR, "婚姻填写错误");
			}
		}
		
		//检查子女个数
		if(map.containsKey("child") && map.get("child") != null){
			if(ValidateUtils.checkIsNumber(map.get("child"))){
				//设置子女个数
				userBase.setChild(Integer.parseInt(map.get("child").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_CHILD_ERRO, "子女个数填写错误");
			}
		}
		
		//检查居住省份
		if(map.containsKey("province") && map.get("province") != null){
			if(ValidateUtils.checkIsNumber(map.get("province"))){
				//设置居住省份
				userBase.setProvince(Integer.parseInt(map.get("province").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_PROVINCE_ERROR, "所在省份填写错误");
			}
		}
		
		//检查居住城市
		if(map.containsKey("city") && map.get("city") != null){
			if(ValidateUtils.checkIsNumber(map.get("city"))){
				//设置居住城市
				userBase.setCity(Integer.parseInt(map.get("city").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_CITY_ERROR, "所在城市填写错误");
			}
		}
		
		//检查居住区县
		if(map.containsKey("district") && map.get("district") != null){
			if(ValidateUtils.checkIsNumber(map.get("district"))){
				//设置居住区县
				userBase.setDistrict(Integer.parseInt(map.get("district").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_DISTRICT_ERROR, "所在区县填写错误");
			}
		}
		
		//检查居住地址
		if(map.containsKey("address") && map.get("address") != null){
			if(!map.get("address").toString().isEmpty()){
				//设置地址
				userBase.setAddress(map.get("address").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_ADDRESS_ERROR, "所在地址填写错误");
			}
		}
		
		//检查居住时长
		if(map.containsKey("liveTime") && map.get("liveTime") != null){
			if(Arrays.asList(new String[]{"0","1","2","3"}).contains(map.get("liveTime").toString())){
				//设置居住时长
				userBase.setLiveTime(Integer.parseInt(map.get("liveTime").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_ADDRESS_ERROR, "居住时长填写错误");
			}
		}
		
		//检查微信号
		if(map.containsKey("wechat") && map.get("wechat") != null){
			if(!map.get("wechat").toString().isEmpty()){
				//设置地址
				userBase.setWechat(map.get("wechat").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_WECHAT_ERROR, "微信号填写错误");
			}
		}
		
		//设置其它字段为空
		userBase.setEmail("---");
		userBase.setRealname("---");
		userBase.setSex(0);
		userBase.setCardNum("---");
		userBase.setCardAddress("---");
		userBase.setCardNation("---");
		userBase.setCardOffice("---");
		userBase.setCardExpire("---");
		userBase.setCardPic("---");
		
		return userBase;
	}
	
	/**
	 * 用户职业信息入库
	 * 
	 * @param map
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	public Boolean insertUserJobInfo(Map<String, Object> map) throws BusinessException{
		//检查入库参数
		UserJob userJob = checkInsertUserJobInfo(map);
		
		try{
			//数据入库
			Boolean ret = userJobDao.insertUserJobInfo(userJob);
			
			//更新用户职业认证时间
			if(ret){
				updateUserJobAuth(Long.parseLong(map.get("uid").toString()));
			}
			
			return ret;
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("UserServiceImpl insertUserJobInfo Exception: "+ ex.getMessage());
			
			//抛出插入失败异常
			throw new BusinessException(CommonConstant.USER_JOB_INFO_INSERT_FAILURE, "用户职业信息提交失败");
		}
	}
	
	/**
	 * 检查用户职业信息
	 * 
	 * @param map
	 * @return
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private UserJob checkInsertUserJobInfo(Map<String, Object> map) throws BusinessException{
		//检查参数
		if(map == null){
			throw new BusinessException(CommonConstant.USER_JOB_INFO_PARAM_ERROR, "用户职业信息参数错误");
		}
		
		//实例化用户职业信息类
		UserJob userJob = new UserJob();

		//检查用户
		if(map.containsKey("uid") && Long.parseLong(map.get("uid").toString()) > 0){
			//设置用户uid
			userJob.setUid(Long.parseLong(map.get("uid").toString()));
		}else{
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}

		//检查职业名称
		if(map.containsKey("jobName") && map.get("jobName") != null){
			if(!map.get("jobName").toString().isEmpty()){
				//设置职业名称
				userJob.setJobName(map.get("jobName").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_JOB_INFO_JOBNAME_ERROR, "职业名称填写错误");
			}
		}
		
		//检查收入
		if(map.containsKey("income") && map.get("income") != null){
			if(Arrays.asList(new String[]{"0","1", "2", "3", "4", "5"}).contains(map.get("income").toString())){
				//设置收入
				userJob.setIncome(Integer.parseInt(map.get("income").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_DEGREE_ERROR, "学历填写错误");
			}
		}
		
		//检查公司名称
		if(map.containsKey("company") && map.get("company") != null){
			if(!map.get("company").toString().isEmpty()){
				//设置职业名称
				userJob.setCompany(map.get("company").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_JOB_INFO_COMPANY_NAME_ERROR, "公司名称填写错误");
			}
		}
		
		//检查居住省份
		if(map.containsKey("province") && map.get("province") != null){
			if(!ValidateUtils.checkIsNumber(map.get("province"))){
				//设置居住省份
				userJob.setProvince(Integer.parseInt(map.get("province").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_PROVINCE_ERROR, "所在省份填写错误");
			}
		}
		
		//检查居住城市
		if(map.containsKey("city") && map.get("city") != null){
			if(!ValidateUtils.checkIsNumber(map.get("city"))){
				//设置居住城市
				userJob.setCity(Integer.parseInt(map.get("city").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_CITY_ERROR, "所在城市填写错误");
			}
		}
		
		//检查居住区县
		if(map.containsKey("district") && map.get("district") != null){
			if(!ValidateUtils.checkIsNumber(map.get("district"))){
				//设置居住区县
				userJob.setDistrict(Integer.parseInt(map.get("district").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_DISTRICT_ERROR, "所在区县填写错误");
			}
		}
		
		//检查居住地址
		if(map.containsKey("address") && map.get("address") != null){
			if(!map.get("address").toString().isEmpty()){
				//设置地址
				userJob.setAddress(map.get("address").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_ADDRESS_ERROR, "所在地址填写错误");
			}
		}
		
		//检查单位电话
		if(map.containsKey("phone") && map.get("phone") != null){
			if(!map.get("phone").toString().isEmpty()){
				//设置单位电话
				userJob.setPhone(map.get("phone").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_JOB_INFO_PHONE_ERROR, "单位电话填写错误");
			}
		}
		
		//设置入库时间
		userJob.setCtime(CommonUtils.getCurrTimeStamp());
		
		//返回用户职业信息Bean
		return userJob;
	}

	 /**
	  * 新增用户联系人信息
	  * 
	  * @param map
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年9月26日
	  * @param author     ChengBo
	  */
	public Boolean insertUserLinkManInfo(Map<String, Object> map) throws BusinessException {
		//检查联系人参数
		UserLinkMan userLinkMan = checkUserLinkManInfo(map);
		
		try{
			//联系人信息入库
			Boolean ret = userLinkManDao.insertUserLinkManInfo(userLinkMan);
			
			//更新用户联系人认证时间
			if(ret){
				updateUserContactsAuth(Long.parseLong(map.get("uid").toString()));
			}
			
			return ret;
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl insertUserLinkManInfo Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_LINK_MAN_INFO_INSERT_FAILURE, "用户联系人信息提交失败");
		}
	}
	
	/**
	 * 检查用户联系人信息
	 * 
	 * @param map
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private UserLinkMan checkUserLinkManInfo(Map<String, Object> map) throws BusinessException{
		//检查参数
		if(map == null){
			throw new BusinessException(CommonConstant.USER_LINK_MAN_INFO_PARAM_ERROR, "用户联系人信息参数错误");
		}
		
		//实例化用户职业信息类
		UserLinkMan userLinkMan = new UserLinkMan();

		//检查用户
		if(map.containsKey("uid") && Long.parseLong(map.get("uid").toString()) > 0){
			//设置用户uid
			userLinkMan.setUid(Long.parseLong(map.get("uid").toString()));
		}else{
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}

		//检查联系人姓名
		if(map.containsKey("name") && map.get("name") != null){
			if(!map.get("name").toString().isEmpty()){
				//设置联系人姓名
				userLinkMan.setName(map.get("name").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_LINK_MAN_INFO_NAME_ERROR, "联系人名称填写错误");
			}
		}

		//检查联系人电话
		if(map.containsKey("phone") && map.get("phone") != null){
			if(!map.get("phone").toString().isEmpty()){
				//设置联系人电话
				userLinkMan.setPhone(map.get("phone").toString());
			}else{
				throw new BusinessException(CommonConstant.USER_LINK_MAN_INFO_PHONE_ERROR, "联系人电话填写错误");
			}
		}
		
		//检查联系人类型
		if(map.containsKey("relation") && map.get("relation") != null){
			if(Arrays.asList(new String[]{"0","1","2","3","４"}).contains(map.get("relation").toString())){
				//设置居住时长
				userLinkMan.setRelation(Integer.parseInt(map.get("relation").toString()));
			}else{
				throw new BusinessException(CommonConstant.USER_LINK_MAN_INFO_RELATION_ERROR, "联系人类型填写错误");
			}
		}
		
		//检查联系人备注
		if(map.containsKey("remark") && map.get("remark") != null){
			if(!map.get("remark").toString().isEmpty()){
				//设置联系人电话
				userLinkMan.setRemark(map.get("remark").toString());
			}
		}
		
		//设置入库时间
		userLinkMan.setCtime(CommonUtils.getCurrTimeStamp());
		
		return userLinkMan;
	}
	
	/**
	 * 更新用户联系人认证时间
	 * 
	 * @param uid
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private Boolean updateUserContactsAuth(long uid) throws BusinessException{
		//检查用户uid
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//获取当前时间戳
		int timeStamp = CommonUtils.getCurrTimeStamp();
		
		try{
			//更新联系人认证时间
			return userDao.updateUserContactsAuth(uid, timeStamp);
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl updateUserContactsAuth uid:"+uid+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_UPDATE_FAILURE, "更新用户联系人信息失败");
		}
	}
	
	/**
	 * 更新用户职业认证时间
	 * 
	 * @param uid
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private Boolean updateUserJobAuth(long uid) throws BusinessException{
		//检查用户uid
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//获取当前时间戳
		int timeStamp = CommonUtils.getCurrTimeStamp();
		
		try{
			//更新职业认证时间
			return userDao.updateUserJobAuth(uid, timeStamp);
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl updateUserJobAuth uid:"+uid+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_UPDATE_FAILURE, "更新用户职业信息失败");
		}
	}
	
	/**
	 * 更新用户个人信息认证时间
	 * 
	 * @param uid
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@SuppressWarnings("unused")
	private Boolean updateUserBaseAuth(long uid) throws BusinessException{
		//检查用户uid
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//获取当前时间戳
		int timeStamp = CommonUtils.getCurrTimeStamp();
		
		try{
			//更新职业认证时间
			return userDao.updateUserPersonAuth(uid, timeStamp);
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl updateUserBaseAuth uid:"+uid+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_UPDATE_FAILURE, "更新用户个人信息失败");
		}
	}
	
	/**
	 * 更新用户手机认证时间
	 * 
	 * @param uid
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public Boolean updateUserMobileAuth(long uid) throws BusinessException{
		//检查用户uid
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//获取当前时间戳
		int timeStamp = CommonUtils.getCurrTimeStamp();
		
		try{
			//更新职业认证时间
			return userDao.updateUserMobileAuth(uid, timeStamp);
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl updateUserMobileAuth uid:"+uid+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_UPDATE_FAILURE, "更新用户个人信息失败");
		}
	}
	
	/**
	 * 更新用户银行卡认证时间
	 * 
	 * @param uid
	 * @return
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private Boolean updateUserBankCardAuth(long uid) throws BusinessException{
		//检查用户uid
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//获取当前时间戳
		int timeStamp = CommonUtils.getCurrTimeStamp();
		
		try{
			//更新银行卡认证时间
			return userDao.updateUserBankCardAuth(uid, timeStamp);
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl updateUserBankCardAuth uid:"+uid+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_UPDATE_FAILURE, "更新用户个人信息失败");
		}
	}
	
	 /**
	  * 新增用户银行卡信息
	  * 
	  * @param bankMap
	  * @return
	  * 
	  * @param addTime 2017年9月27日
	  * @param author     ChengBo
	 * @throws BusinessException 
	  */
	public Boolean authAddUserBank(Map<String, String> map) throws BusinessException{		
		//检查用户的银行卡信息
		UserBank userBank = checkUserBankInfo(map);
		
		//调用三方服务验证
		authService.authUserBank(map);
		
		try{
			//银行卡信息入库
			userBankDao.insertUserBankInfo(userBank);
			
			//更新用户绑卡认证状态
			updateUserBankCardAuth(userBank.getUid());
			
			return true;
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl addUserBank param:"+map+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_BANK_INFO_INSERT_FAILURE, "银行卡信息添加失败");
		}
	}
	
	/**
	 * 检查用户银行卡信息
	 * 
	 * @param map
	 * @return
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private UserBank checkUserBankInfo(Map<String, String> map) throws BusinessException{
		//检查参数
		if(map == null){
			throw new BusinessException(CommonConstant.USER_BANK_INFO_PARAM_ERROR, "用户银行卡信息参数错误");
		}
		
		//实例化用户银行卡信息类
		UserBank userBank = new UserBank();

		//检查用户
		if(map.containsKey("uid") && Long.parseLong(map.get("uid").toString()) > 0){
			//设置用户uid
			userBank.setUid(Long.parseLong(map.get("uid").toString()));
		}else{
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//检查银行卡号
		if(map.containsKey("bankNum") && ValidateUtils.checkIsNumber(map.get("bankNum"))){
			//设置银行卡号
			userBank.setBankNum(map.get("bankNum"));
		}else{
			throw new BusinessException(CommonConstant.USER_BANK_INFO_BANK_NUM_ERROR, "银行卡号错误");
		}
		
		//检查银行预留手机号
		if(map.containsKey("mobile") && ValidateUtils.checkMobile(map.get("mobile"))){
			//设置银行预留手机号
			userBank.setMobile(map.get("mobile"));
		}else{
			throw new BusinessException(CommonConstant.USER_BANK_INFO_MOBILE_ERROR, "银行预留手机号错误");
		}
		
		//检查银行名称
		if(map.containsKey("bankName") && !map.get("bankName").isEmpty()){
			//设置银行名称
			userBank.setBankName(map.get("bankName"));
		}else{
			throw new BusinessException(CommonConstant.USER_BANK_INFO_BANK_NAME_ERROR, "银行名称错误");
		}
		
		//检查省份
		if(map.containsKey("bankProvince") && ValidateUtils.checkIsNumber(map.get("bankProvince"))){
			//设置省份
			userBank.setBankProvince(Integer.parseInt(map.get("bankProvince")));
		}else{
				throw new BusinessException(CommonConstant.USER_BASE_INFO_PROVINCE_ERROR, "开户省份填写错误");
		}
		
		//检查城市
		if(map.containsKey("bankCity") && ValidateUtils.checkIsNumber(map.get("bankCity"))){
			//设置城市
			userBank.setBankCity(Integer.parseInt(map.get("bankCity")));
		}else{
			throw new BusinessException(CommonConstant.USER_BASE_INFO_CITY_ERROR, "所在城市填写错误");
		}
		
		//设置入库时间
		userBank.setCtime(CommonUtils.getCurrTimeStamp());
		
		//设置银行卡类型 0:放款卡, 1:还款卡
		userBank.setCardType(0);
		
		return userBank;
	}
	
	 /**
	  * 获取用户放款银行卡信息
	  * 
	  * @param uid
	  * @return
	  * 
	  * @param addTime 2017年9月27日
	  * @param author     ChengBo
	 * @throws BusinessException 
	  */
	 public UserBank getUserBankByUid(long uid) throws BusinessException{
			//检查用户uid
			if(uid == 0){
				throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
			}
			
			//查询用户银行卡信息
			UserBank userBank = userBankDao.getUserBankByUid(uid);

			//返回数据
			return userBank;
	 }
	 
	 /**
	  * 按条件查询用户列表
	  * 
	  * @param userMap
	  * @return
	  * 
	  * @param addTime 2017年10月9日
	  * @param author     ChengBo
	 * @throws BusinessException 
	  */
	 public List<User> getUserList(Map<String, String> userMap) throws BusinessException{
		 //检查查询参数
		 User userInfo = checkGetUserListParam(userMap);

		try{
			 //查询数据
			 List<User> userList = userDao.getUserList(userInfo);
		
			 return userList;
		}catch(Exception ex){
			//记录日志
			logger.error("UserServiceImpl getUserList param:"+userMap+" Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.USER_BASE_INFO_SELECT_FAILURE, "用户信息查询失败");
		}
	 }
	 
	 /**
	  * 检查查询用户列表参数
	  * 
	  * @param userMap
	  * @return
	  * 
	  * @param addTime 2017年10月10日
	  * @param author     ChengBo
	 * @throws BusinessException 
	  */
	 private User checkGetUserListParam(Map<String, String> userMap) throws BusinessException{		 
		 //实例化返回Bean
		 User userInfo = new User();
		 
		 //设置用户uid
		 if(userMap.containsKey("uid")){
			 if(ValidateUtils.checkIsNumber(userMap.get("uid")) && Long.parseLong(userMap.get("uid")) > 0){
				 userInfo.setUid(Long.parseLong(userMap.get("uid")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_NOT_EXIST, "查询用户不存在");
			 }
		 }
		 
		 //设置手机号
		 if(userMap.containsKey("mobile")){
			 	if(ValidateUtils.checkMobile(userMap.get("mobile"))){
					 userInfo.setMobile(userMap.get("mobile"));
			 	}else{
			 		throw new BusinessException(CommonConstant.USER_MOBILE_ERROR, "查询手机号格式错误");
			 	}
		 }
		 
		 //设置身份认证
		 if(userMap.containsKey("shenfenAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("shenfenAuth").toString())){
				 userInfo.setShenfenAuth(Long.parseLong(userMap.get("shenfenAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_IDENTITY_AUTH_PARAM_ERROR, "查询实名信息格式错误");
			 }
		 }
		 
		 //设置活体认证
		 if(userMap.containsKey("huotiAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("huotiAuth").toString())){
				 userInfo.setHuotiAuth(Long.parseLong(userMap.get("huotiAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_BODY_AUTH_PARAM_ERROR, "查询活体信息格式错误");
			 }
		 }
		 
		 //设置个人信息认证
		 if(userMap.containsKey("persionAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("persionAuth").toString())){
				 userInfo.setPersonAuth(Long.parseLong(userMap.get("persionAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_BASE_INFO_PARAM_ERROR, "查询用户基本信息格式错误");
			 }
		 }
		 
		 //设置职业信息认证
		 if(userMap.containsKey("jobAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("jobAuth").toString())){
				 userInfo.setJobAuth(Long.parseLong(userMap.get("jobAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_JOB_INFO_PARAM_ERROR, "查询用户职业信息格式错误");
			 }
		 }
		 
		 //设置联系人信息认证
		 if(userMap.containsKey("contactsAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("contactsAuth").toString())){
				 userInfo.setContactsAuth(Long.parseLong(userMap.get("contactsAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_LINK_MAN_INFO_PARAM_ERROR, "查询用户联系人信息格式错误");
			 }
		 }
		 
		 //设置手机认证
		 if(userMap.containsKey("mobileAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("mobileAuth").toString())){
				 userInfo.setMobileAuth(Long.parseLong(userMap.get("mobileAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_MOBILE_AUTH_PARAM_ERROR, "查询用户手机号认证信息格式错误");			 
			 }
		 }
		 
		 //设置银行卡认证
		 if(userMap.containsKey("bandcardAuth")){
			 if(Arrays.asList(new String[]{"1","2"}).contains(userMap.get("bandcardAuth").toString())){
				 userInfo.setBandcardAuth(Long.parseLong(userMap.get("bandcardAuth")));
			 }else{
				 throw new BusinessException(CommonConstant.USER_BANK_INFO_PARAM_ERROR, "查询用户银行卡认证信息格式错误");
			 }
		 }
		 
		 //设置省份
		 if(userMap.containsKey("province") && ValidateUtils.checkIsNumber(userMap.get("province"))){
			 userInfo.setProvince(Integer.parseInt(userMap.get("province")));
		 }
		 
		 //设置城市
		 if(userMap.containsKey("city") && ValidateUtils.checkIsNumber(userMap.get("city"))){
			 userInfo.setCity(Integer.parseInt(userMap.get("city")));
		 }
		 
		 //设置区县
		 if(userMap.containsKey("district") && ValidateUtils.checkIsNumber(userMap.get("district"))){
			 userInfo.setDistrict(Integer.parseInt(userMap.get("district")));
		 }
		 
		 //设置注册开始时间
		 if(userMap.containsKey("regStartTime") && ValidateUtils.checkIsNumber(userMap.get("regStartTime"))){
			 userInfo.setRegStartTime(Long.parseLong(userMap.get("regStartTime")));
		 }
		 
		 //设置注册结束时间
		 if(userMap.containsKey("regEndTime") && ValidateUtils.checkIsNumber(userMap.get("regEndTime"))){
			 userInfo.setRegEndTime(Long.parseLong(userMap.get("regEndTime")));
		 }
		 
		 //提取每页条数
		 int pageSize = userMap.containsKey("pageSize") && ValidateUtils.checkIsNumber(Integer.parseInt(userMap.get("pageSize"))) ? Integer.parseInt(userMap.get("pageSize")) : 13;

		 //提取当前页数
		 int page = userMap.containsKey("page") && ValidateUtils.checkIsNumber(Integer.parseInt(userMap.get("page"))) ? Integer.parseInt(userMap.get("page")) : 1;
		 
		 //设置查询偏移数
		 userInfo.setOffset((page-1)*pageSize);
		 
		 //设置每页条数
		 userInfo.setPageSize(pageSize);
		 
		 return userInfo;
	 }
	 
	 /**
	  * 按条件统计用户总条数
	  * 
	  * @param userMap
	  * @return
	  * 
	  * @param addTime 2017年10月10日
	  * @param author     ChengBo
	 * @throws BusinessException 
	  */
	 public int countUserNum(Map<String, String> userMap) throws BusinessException{
		 //检查查询参数
		 User userInfo = checkGetUserListParam(userMap);

		 //查询总条数
		 int countNum = userDao.countUserNum(userInfo);
		 
		return countNum;
	 }
	 
	 /**
	  * 获取制定uids的用户数据
	  * 
	  * @param uidList
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年10月12日
	  * @param author     ChengBo
	  */
	 public Map<Long, User> getUserDataByUids(List<Long> userList) throws BusinessException{
		 //检查参数
		 if(userList.size() == 0){
			 throw new BusinessException(CommonConstant.USER_INFO_UID_PARAM_ERROR, "查询参数不能为空");
		 }
		 
		 //实例化返回Map
		 Map<Long, User> userDataMap = new HashMap<Long, User>();
		 
		 //提取用户表查询条件
		 Map<String, Object> userFilter = new HashMap<String, Object>();
		 userFilter.put("uids", userList);
		 
		 //查询用户数据
		 List<User> userListData = userDao.getUserByUids(userFilter);
		 if(userListData.size() > 0){
			 for(User user : userListData){
				 userDataMap.put(user.getUid(), user);
			 }
		 }
		 
		 return userDataMap;
	 }
	 
	 /**
	  * 获取制定uids的用户基本数据
	  * 
	  * @param uidList
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年10月12日
	  * @param author     ChengBo
	  */
	 public Map<Long, UserBase> getUserBaseDataByUids(List<Long> userList) throws BusinessException{
		 //检查参数
		 if(userList.size() == 0){
			 throw new BusinessException(CommonConstant.USER_INFO_UID_PARAM_ERROR, "查询参数不能为空");
		 }
		 
		 //实例化返回Map
		 Map<Long, UserBase> userBaseDataMap = new HashMap<Long, UserBase>();

		 //设置查询条件
		 Map<String, Object> userBaseFilter = new HashMap<String, Object>();
		 userBaseFilter.put("uids", userList);
		 
		 //查询用户数据
		 List<UserBase> userListData = userBaseDao.getUserBaseByUids(userBaseFilter);
		 if(userListData.size() > 0){
			 for(int i = 0; i < userListData.size(); i++){
				 userBaseDataMap.put(userListData.get(i).getUid(), userListData.get(i));
			 }
		 }
		 
		 return userBaseDataMap; 
	 }
	 
	 /**
	  * 获取制定uids的用户银行信息
	  * 
	  * @param uidList
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月8日
	  * @param author     ChengBo
	  */
	 public Map<Long, UserBank> getUserBankByUids(List<Long> userList) throws BusinessException{
		 //检查参数
		 if(userList.size() == 0){
			 throw new BusinessException(CommonConstant.USER_INFO_UID_PARAM_ERROR, "查询参数不能为空");
		 }
		 
		 //实例化返回Map
		 Map<Long, UserBank> userBankDataMap = new HashMap<Long, UserBank>();

		 //设置查询条件
		 Map<String, Object> userBankFilter = new HashMap<String, Object>();
		 userBankFilter.put("uids", userList);
		 
		 //查询用户数据
		 List<UserBank> userListData = userBankDao.getUserBankByUids(userBankFilter);
		 if(userListData.size() > 0){
			 for(int i = 0; i < userListData.size(); i++){
				 userBankDataMap.put(userListData.get(i).getUid(), userListData.get(i));
			 }
		 }
		 
		 return userBankDataMap; 
	 }
	 
	 /**
	  * 用户使用短信验证码登陆操作
	  * 
	  * @param mobile
	  * @param captcha
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年10月26日
	  * @param author     ChengBo
	  */
	 public long doUserLoginByCaptcha(String mobile, String captcha) throws BusinessException{
			//检查用户用户名
			if(!ValidateUtils.checkMobile(mobile) || !ValidateUtils.checkCaptcha(captcha)){
				throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或验证码格式错误");
			}
			
			//检查短信验证码是否正确
			if(!mobileMsgService.checkCaptchaCode(mobile, CommonConstant.MOBILE_MSG_TYPE_LOGIN, captcha)){
				throw new BusinessException(CommonConstant.MOBILE_CAPTCHA_ERROR, "短信验证码输入错误");
			}
			
			//调取用户数据
			User user = getUserByUserMobile(mobile);
			if(user == null){
				throw new BusinessException(CommonConstant.USER_MOBILE_NOT_EXISTS, "请先注册后，再登录!");
			}

			//设置登陆缓存Key
			String userUniqueKey = getUserUniqueKey(user.getUid());
			
			//设置用户登陆缓存
			memCache.set(userUniqueKey, user, CommonConstant.APP_USER_SESSION_EXPIRE_TIME);
			
			return user.getUid();
	 }
	 
	 /**
	  * 获取用户登陆缓存Key
	  * 
	  * @param uid
	  * @return
	  * 
	  * @param addTime 2017年10月26日
	  * @param author     ChengBo
	  */
	 private String getUserUniqueKey(long uid){
		 return CommonConstant.APP_USER_INFO_CACHE_PREFIX + uid;
	 }
	 
	 /**
	  * 找回密码
	  * 
	  * @param mobile
	  * @param captcha
	  * @param pwd
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年10月26日
	  * @param author     ChengBo
	  */
	 public Boolean setUserNewPwdByCaptcha(String mobile, String captcha, String pwd) throws BusinessException{
		 //检查手机号参数
		if(!ValidateUtils.checkMobile(mobile)){
			throw new BusinessException(CommonConstant.USER_MOBILE_ERROR, "手机号格式错误");
		}
		
		//检查新密码格式
		if(!ValidateUtils.checkPassword(pwd)){
			throw new BusinessException(CommonConstant.USER_PWD_ERROR, "新密码格式错误");
		}
		
		//检查验证码
		if(!ValidateUtils.checkCaptcha(captcha) || !mobileMsgService.checkCaptchaCode(mobile, CommonConstant.MOBILE_MSG_TYPE_FIND_PWD, captcha)){
			throw new BusinessException(CommonConstant.MOBILE_CAPTCHA_ERROR, "短信验证码输入错误");
		}

		//设置混淆字符串
		String saltStr = CommonUtils.getRandomString(6);
		
		//设置密码
		String md5Pwd = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5(pwd)+saltStr);
		
		//设置更新用户Bean
		User userInfo = new User();
		
		//设置更新参数
		userInfo.setMobile(mobile);
		userInfo.setPassword(md5Pwd);
		userInfo.setEncrypt(saltStr);
		
		//更新用户的登陆密码
		return userDao.updateUserPwdByMobile(userInfo);
	 }
	 
	 /**
	  * 通过用户uid获取用户信息
	  * 
	  * @param uid
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月3日
	  * @param author     ChengBo
	  */
	 public User getUserInfoByUid(int uid) throws BusinessException{
		 //检查参数
		 if(!ValidateUtils.checkIsNumber(uid)){
			 throw new BusinessException(CommonConstant.USER_NOT_EXIST, "查询用户不存在");
		 }
		 
		 //调取用户信息
		 User userInfo = userDao.getUserByUid(uid);
		 
		 return userInfo;
	 }
	 
	 /**
	  * 获取用户基本信息
	  * 
	  * @param uid
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月3日
	  * @param author     ChengBo
	  */
	 public UserBase getUserBaseInfoByUid(int uid) throws BusinessException{
		//检查参数
		 if(!ValidateUtils.checkIsNumber(uid)){
			 throw new BusinessException(CommonConstant.USER_NOT_EXIST, "查询用户不存在");
		 }
		 
		 //调取用户信息
		 UserBase userBaseInfo = userBaseDao.getUserBaseByUid(uid);
		 
		 return userBaseInfo;
	 }

	 /**
	  * 获取用户银行卡信息
	  * 
	  * @param uid
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月3日
	  * @param author     ChengBo
	  */
	 public UserBank getUserBankInfoByUid(int uid) throws BusinessException{
			//检查参数
		 if(!ValidateUtils.checkIsNumber(uid)){
			 throw new BusinessException(CommonConstant.USER_NOT_EXIST, "查询用户不存在");
		 }
		 
		 //调取用户信息
		 UserBank userBankInfo = userBankDao.getUserBankByUid(uid);
		 
		 return userBankInfo;
	 }
	 
	 /**
	  * 调取用户的职业信息
	  * 
	  * @param uid
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月7日
	  * @param author     ChengBo
	  */
	 public UserJob getUserJobInfoByUid(int uid) throws BusinessException{
		 //检查参数
		 if(!ValidateUtils.checkIsNumber(uid)){
			 throw new BusinessException(CommonConstant.USER_NOT_EXIST, "查询用户不存在");
		 }
		 
		 //调取用户职业信息
		 UserJob userJobInfo = userJobDao.getUserJobInfoByUid(uid);
		 
		 return userJobInfo;
	 }
	 
	 /**
	  * 查询用户的紧急联系人
	  * 
	  * @param uid
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月7日
	  * @param author     ChengBo
	  */
	 public List<UserLinkMan> getUserLinkManInfoByUid(int uid) throws BusinessException{
		 //检查参数
		 if(!ValidateUtils.checkIsNumber(uid)){
			 throw new BusinessException(CommonConstant.USER_NOT_EXIST, "查询用户不存在");
		 }
		 
		 //调取用户联系人信息
		 List<UserLinkMan> userLinkManData = userLinkManDao.getUserLinkManByUid(uid);
		 
		 return userLinkManData;
	 }
}
