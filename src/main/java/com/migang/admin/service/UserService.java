package com.migang.admin.service;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.User;
import com.migang.admin.entity.UserBank;
import com.migang.admin.entity.UserBase;
import com.migang.admin.entity.UserJob;
import com.migang.admin.entity.UserLinkMan;
import com.migang.admin.exception.BusinessException;

/**
 * 前端用户Service接口
 *
 * @param addTime 2017年9月5日
 * @param author     ChengBo
 */
public interface UserService {

	/**
	 * 根据用户uid获取用户信息
	 * 
	 * @param uid
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	 Map<String, Object> getUserByUid(long uid) throws BusinessException;
	
	/**
	 * 根据用户名手机号用户信息
	 * 
	 * @param mobile
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	 User getUserByUserMobile(String mobile) throws BusinessException;
	 
	 /**
	  * 新增用户数据
	  * 
	  * @param user
	  * @return
	  * 
	  * @param addTime 2017年9月5日
	  * @param author     ChengBo
	  */
	 int insertUserInfo(User user);
	 
	 /**
	  * 用户登陆操作
	  * 
	  * @param mobile
	  * @param password
	  * @return
	  * 
	  * @param addTime 2017年9月5日
	  * @param author     ChengBo
	 * @throws Exception 
	  */
	 long doUserLogin(String mobile, String password) throws BusinessException;
	 
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
	 long doUserLoginByCaptcha(String mobile, String captcha) throws BusinessException;
	 
	 /**
	  * 用户退出操作
	  * 
	  * @param username
	  * @param loginUniqueKey
	  * 
	  * @param addTime 2017年9月5日
	  * @param author     ChengBo
	  */
	 void userLogout(String loginUniqueKey);
	 
	 /**
	  * 通过唯一标示获取用户缓存数据
	  * 
	  * @param loginUniqueKey
	  * @return
	  * 
	  * @param addTime 2017年9月5日
	  * @param author     ChengBo
	  */
	 User getUserInfoByUniqueKey(String loginUniqueKey);
	 
	 /**
	  * 新用户注册
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
	 long userReg(String mobile, String password, String captcha, String ip) throws BusinessException;
	 
	 /**
	  * 设置用户缓存
	  * 
	  * @param uid
	  * @param cacheKey
	  * @param token
	  * 
	  * @param addTime 2017年9月20日
	  * @param author     ChengBo
	  */
	 void setUserSession(long uid, String cacheKey, String token) throws BusinessException;
	 
	 /**
	  * 检查用户的Token 并更新有效时间
	  * 
	  * @param cacheKey
	  * @param token
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年9月21日
	  * @param author     ChengBo
	  */
	 long checkUserToken(String cacheKey, String token) throws BusinessException;
	 
	 /**
	  * 处理用户退出操作
	  * 
	  * @param uid
	  * @return
	  * 
	  * @param addTime 2017年9月22日
	  * @param author     ChengBo
	  */
	 Boolean doUserLogout(long uid) throws BusinessException;
	 
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
	 Boolean insertUserBaseInfo(Map<String, Object> map) throws BusinessException;
	 
	 /**
	  * 用户职业信息入库
	  * 
	  * @param map
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年9月25日
	  * @param author     ChengBo
	  */
	 Boolean insertUserJobInfo(Map<String, Object> map) throws BusinessException;

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
	 Boolean insertUserLinkManInfo(Map<String, Object> map) throws BusinessException;

	 /**
	  * 更新用户 手机认证信息
	  * 
	  * @param uid
	  * @return
	  * 
	  * @param addTime 2017年9月26日
	  * @param author     ChengBo
	  */
	 Boolean updateUserMobileAuth(long uid) throws BusinessException;
	 
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
	 Boolean authAddUserBank(Map<String, String> bankMap) throws BusinessException;
	 
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
	 UserBank getUserBankByUid(long uid) throws BusinessException;
	 
	 /**
	  * 按条件查询用户列表
	  * 
	  * @param userMap
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年10月10日
	  * @param author     ChengBo
	  */
	 List<User> getUserList(Map<String, String> userMap) throws BusinessException;
	 
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
	 int countUserNum(Map<String, String> userMap) throws BusinessException;
	 
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
	 Map<Long, User> getUserDataByUids(List<Long> uidList) throws BusinessException;
	 
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
	 Map<Long, UserBase> getUserBaseDataByUids(List<Long> uidList) throws BusinessException;

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
	 Boolean setUserNewPwdByCaptcha(String mobile, String captcha, String pwd) throws BusinessException;
	 
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
	 User getUserInfoByUid(int uid) throws BusinessException;
	 
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
	 UserBase getUserBaseInfoByUid(int uid) throws BusinessException;
	 
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
	 UserBank getUserBankInfoByUid(int uid) throws BusinessException;
	 
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
	 UserJob getUserJobInfoByUid(int uid) throws BusinessException;
	 
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
	 List<UserLinkMan> getUserLinkManInfoByUid(int uid) throws BusinessException;
	 
	 /**
	  * 
	  * 
	  * @param uidList
	  * @return
	  * @throws BusinessException
	  * 
	  * @param addTime 2017年11月8日
	  * @param author     ChengBo
	  */
	 Map<Long, UserBank> getUserBankByUids(List<Long> uidList) throws BusinessException;
}
