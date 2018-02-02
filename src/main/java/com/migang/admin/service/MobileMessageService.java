package com.migang.admin.service;

import org.springframework.stereotype.Service;

import com.migang.admin.exception.BusinessException;

/**
 * 手机短信服务
 *
 *@param addTime 2017-10-25
 *@param author     ChengBo
 */
@Service
public interface MobileMessageService {

	/**
	 * 按类型 发送登陆短信
	 * 
	 * @param mobile
	 * @param msgType
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	Boolean sendMobileMsgByType(String mobile, int msgType) throws BusinessException;
	
	/**
	 * 检查验证码是否正确
	 * 
	 * @param mobile
	 * @param msgType
	 * @param code
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月26日
	 * @param author     ChengBo
	 */
	Boolean checkCaptchaCode(String mobile, int msgType, String code) throws BusinessException;
}
