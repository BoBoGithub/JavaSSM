package com.migang.admin.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.AuthService;
import com.migang.admin.service.UserService;

/**
 * 第三方那个服务认证实现类
 *
 *＠param addTime 2017-09-26
 *@param author   ChengBo
 */
@Service
public class AuthServiceImpl implements AuthService {
	//引入日志操作
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private UserService userService;
	
	/**
	 * 验证 手机服务密码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public Boolean authMobileServiceCode(String mobile, String code) throws BusinessException {
		//验证手机号
		if(!ValidateUtils.checkMobile(mobile)){
			throw new BusinessException(CommonConstant.USER_MOBILE_ERROR, "用户手机号错误");
		}
		
		//检查服务密码
		if(code.isEmpty()){
			throw new BusinessException(CommonConstant.MOBILE_CODE_ERROR, "手机号服务码错误");
		}
		
		//调取用三方服务认证 TODO
		
		return true;
	}
	
	/**
	 * 用户手机号授权验证
	 * 
	 * @param mobileCode
	 * @param picCode
	 * @return
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public Boolean authUserMobile(long uid, String mobileCode, String picCode) throws BusinessException{
		//检查手机验证码
		if(mobileCode.isEmpty()){
			throw new BusinessException(CommonConstant.MOBILE_CAPTCHA_ERROR, "短信验证码错误");
		}
		
		//检查图片验证码
		if(picCode.isEmpty()){
			throw new BusinessException(CommonConstant.PICTURE_CODE_ERROR, "图片验证码错误");
		}
		
		//调取用三方服务认证 TODO
		Boolean ret = false;
		
		//更新手机验证状态
		if(ret){
			userService.updateUserMobileAuth(uid);
		}
		
		return true;
	}

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
	public Boolean authUserBank(Map<String, String> bankMap) throws BusinessException{
		//检查参数
		if(bankMap == null){
			throw new BusinessException(CommonConstant.USER_BANK_INFO_PARAM_ERROR, "验证银行卡信息参数错误");
		}
		
		//调用三方服务　TODO
		Boolean ret = true;
		logger.debug("AuthServiceImpl authUserBank param: "+ bankMap+" ret:"+ret);
		
		return ret;
	}
}
