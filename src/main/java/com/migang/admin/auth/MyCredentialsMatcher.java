package com.migang.admin.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migang.admin.common.utils.CryptoUtils;

/**
 * 自定义密码校验类
 *
 *＠param addTime 2017-08-24
 *@param author   ChengBo
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

	//引入日志操作
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 自定义密码校验
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		//获取基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		//获取登陆填写的密码
		String loginPwd = String.valueOf(token.getPassword());
		
		//获取数据库密码
		String dbPwd = (String) getCredentials(info);
		
		//获取加密盐值
		ByteSource saltStr = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
		String saltString = CryptoUtils.decodeBASE64(saltStr.toString());
		
		//加密登陆密码
		String encodePasswd = CryptoUtils.encodeMD5(CryptoUtils.encodeMD5(loginPwd) + saltString);
		
		//记录日志
		logger.info("doCredentialsMatch loginPwd:"+loginPwd+" dbPwd:"+dbPwd+" saltString:"+saltString+" encodePasswd:"+encodePasswd);
		
		//对比密码是否正确
		return encodePasswd.equals(dbPwd);
	}

}
