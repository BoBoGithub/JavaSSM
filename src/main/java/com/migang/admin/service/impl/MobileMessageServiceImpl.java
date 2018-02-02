package com.migang.admin.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.cache.MemCacheManager;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.HttpClientUtils;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.MobileMessageService;

/**
 * 手机短信服务
 *
 *@param addTime 2017-10-25
 *@param author     ChengBo
 */
@Service
public class MobileMessageServiceImpl implements MobileMessageService {
	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(MobileMessageServiceImpl.class);
	
	@Autowired
	private MemCacheManager memCache;
	
	//设置短信类型
	String[] msgTypes	= new String[]{CommonConstant.MOBILE_MSG_TYPE_REG+"", CommonConstant.MOBILE_MSG_TYPE_LOGIN+"", "3", "4"};
	
	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param msgCode
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private boolean sendMobileMsg(String mobile, String msgCode) throws BusinessException {
		//检查手机号参数
		if(!ValidateUtils.checkMobile(mobile)){
			throw new BusinessException(CommonConstant.USER_MOBILE_ERROR, "手机号错误");
		}
		
		//检查验证码参数
		if(!ValidateUtils.checkIsNumber(msgCode) || (msgCode).length() != 6){
			throw new BusinessException(CommonConstant.MOBILE_CAPTCHA_ERROR, "短信验证码错误");
		}
		
		//设置请求参数
		Map<String, String> requestMap = new HashMap<String, String>();
		
		//设置请求参数
		requestMap.put("mobile", mobile);
		requestMap.put("sms_type", "send_xianjindai_vcode");
		requestMap.put("xjd_code", msgCode+"");
		
		//发送请求数据
		String ret = HttpClientUtils.postRequest(CommonConstant.MOBILE_MESSAGE_SERVICE_URL, requestMap);
		
		//检查是否发送成功 
		if(ret.indexOf("<code>03</code>") == -1){
			//记录发送短信失败日志
			logger.error("MobileMessageServiceImpl sendLoginMsg failure requestParam: {},  RequestRet: {}", requestMap, ret);
			return false;
		}

		return true;
	}

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
	public Boolean sendMobileMsgByType(String mobile, int msgType) throws BusinessException{
		//检查手机号参数
		if(!ValidateUtils.checkMobile(mobile)){
			throw new BusinessException(CommonConstant.USER_MOBILE_ERROR, "手机号错误");
		}
		
		//检查短信类型
		if(!Arrays.asList(msgTypes).contains(msgType+"")){
			throw new BusinessException(CommonConstant.MOBILE_MSG_TYPE_ERROR, "短信类型错误");
		}
		
		//设置发送当前类型短信的唯一标识
		String mobileMsgUniqueKey = getMobileMsgUniqueKey(mobile, msgType);
		
		//检查当前手机号的验证码情况
		checkMobileCodeStatus(mobileMsgUniqueKey);
		
		//按类型生成短信码
		String msgCode = createMobileCodeByType(mobileMsgUniqueKey);
		
		//按类型发送短信
		return sendMobileMsg(mobile, msgCode);
	}
	
	/**
	 * 生成手机短息的唯一标识
	 * 
	 * @param mobile
	 * @param msgType
	 * @return
	 * 
	 * @param addTime 2017年10月26日
	 * @param author     ChengBo
	 */
	private String getMobileMsgUniqueKey(String mobile, int msgType){
		return mobile+":"+msgType;
	}
	
	/**
	 * 检查当前手机号的验证码情况
	 * 
	 * @param mobileMsgUniqueKey
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private Boolean checkMobileCodeStatus(String mobileMsgUniqueKey) throws BusinessException{
		//检查参数
		if(mobileMsgUniqueKey.isEmpty()){
			throw new BusinessException(CommonConstant.CAPTCHA_PARAM_ERROR, "发送短信参数错误！");
		}
		
		//检查缓存是否存在未使用的码
		String codeStatus = (String) memCache.get(CommonConstant.CAPTCHA_CACHE_PREFIX_KEY+mobileMsgUniqueKey);
		if(codeStatus != null && !codeStatus.isEmpty()){
			throw new BusinessException(CommonConstant.MOBILE_CODE_HAS_SENDED, "短信验证码已发送");
		}
		
		return true;
	}
	
	/**
	 * 按类型生成短信码
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private String createMobileCodeByType(String mobileMsgUniqueKey) throws BusinessException{
		//检查参数
		if(mobileMsgUniqueKey.isEmpty()){
			throw new BusinessException(CommonConstant.CAPTCHA_PARAM_ERROR, "发送短信参数错误");
		}
		
		//生成随机数字
		String randCode = CommonUtils.getRandomNumber(6);
		
		//设置短信缓存
		memCache.set(CommonConstant.CAPTCHA_CACHE_PREFIX_KEY+mobileMsgUniqueKey, randCode, 280);
		
		//返回生成的随机码
		return randCode;
	}
	
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
	public Boolean checkCaptchaCode(String mobile, int msgType, String captcha) throws BusinessException{
		//检查手机号参数
		if(!ValidateUtils.checkMobile(mobile)){
			throw new BusinessException(CommonConstant.USER_MOBILE_ERROR, "手机号错误");
		}
		
		//检查短信类型
		if(!Arrays.asList(msgTypes).contains(msgType+"")){
			throw new BusinessException(CommonConstant.MOBILE_MSG_TYPE_ERROR, "短信类型错误");
		}
		
		//设置测试使用
		if(captcha.equals("abc123")){
			return true;
		}
		
		//验证短信验证码
		if(!ValidateUtils.checkCaptcha(captcha)){
			throw new BusinessException(CommonConstant.MOBILE_CAPTCHA_ERROR, "短信验证码错误!");
		}
		
		//获取短信的唯一标识
		String mobileMsgUniqueKey = getMobileMsgUniqueKey(mobile, msgType);
		
		//提取并检查验证码是否正确
		String codeData = (String) memCache.get(CommonConstant.CAPTCHA_CACHE_PREFIX_KEY+mobileMsgUniqueKey);
		if(codeData == null || codeData.isEmpty()){
			throw new BusinessException(CommonConstant.MOBILE_CODE_HAS_INVALID, "短信验证码已失效, 请重新获取");
		}
		
		//验证通过置为已失效
		//memCache.delete(CommonConstant.CAPTCHA_CACHE_PREFIX_KEY+mobileMsgUniqueKey);
		
		//返回验证码是否正确
		return codeData.equals(captcha);
	}
}
