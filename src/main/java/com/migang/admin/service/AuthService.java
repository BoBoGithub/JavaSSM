package com.migang.admin.service;

import java.util.Map;

import com.migang.admin.exception.BusinessException;

/**
 * 第三方那个服务认证接口
 *
 *＠param addTime 2017-09-26
 *@param author   ChengBo
 */
public interface AuthService {

	/**
	 * 验证 手机服务密码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * 
	 * @throws BusinessException 
	 */
	Boolean authMobileServiceCode(String mobile, String code) throws BusinessException;
	
	/**
	 * 用户手机号授权验证
	 * 
	 * @param uid
	 * @param mobileCode
	 * @param picCode
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 */
	Boolean authUserMobile(long uid, String mobileCode, String picCode) throws BusinessException;
	
	/**
	 * 用户 银行卡认证
	 * 
	 * @param bankMap
	 * @return
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	Boolean authUserBank(Map<String, String> bankMap) throws BusinessException;
}
