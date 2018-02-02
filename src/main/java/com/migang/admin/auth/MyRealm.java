package com.migang.admin.auth;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.entity.AdminUser;
import com.migang.admin.service.AdminUserService;

/**
 * 自定义的指定Shiro验证用户登录的类 
 *
 */
public class MyRealm extends AuthorizingRealm {

	//实例化日志操作
	private Logger logger = LoggerFactory.getLogger(MyRealm.class);
	
	//引入用户操作类
	@Autowired
	private AdminUserService userService; 
	
	/**
	 * 重写密码验证，让shiro用重新的规则验证  
	 */
	@PostConstruct
	 public void initCredentialsMatcher() {  
		 setCredentialsMatcher(new MyCredentialsMatcher());  
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()  
		String currentUsername = (String) super.getAvailablePrincipal(principals);
			
		 //为当前用户设置角色和权限  
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		
		//实际中从数据库取得  
		if(null != currentUsername){
			 //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色  
			simpleAuthorInfo.addRole("admin");
			
			//添加权限
			simpleAuthorInfo.addStringPermission("admin:manager");
			logger.info("已为用户["+currentUsername+"]赋予[admin:manger]权限");
			
			return simpleAuthorInfo;
		}
		
		//若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址  
		//详见applicationContext.xml中的<bean id="shiroFilter">的配置
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//获取基于用户名和密码的令牌
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		//获取当前登陆的用户名
		String username = token.getUsername();
		
		//调取用户信息
		AdminUser userInfo = userService.getAdminUserInfoByUserName(username);
		
		//检查用户是否存在
		if(userInfo == null){
			throw new AuthenticationException("doGetAuthenticationInfo username not exist");
		}
		
		//检查用户状态是否正常
		if(userInfo.getStatus() != 0){
			throw new LockedAccountException("doGetAuthenticationInfo username status exception");
		}
	
		//设置加密盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(userInfo.getEncrypt());
		
		//验证用户名和密码
		AuthenticationInfo authInfo = new SimpleAuthenticationInfo(username, userInfo.getPassword(), credentialsSalt, getName());
		
		//设置用户缓存 TODO 这块根据用户数据使用需要 调整一个合适的缓存数据
		this.setSession("currentUser", username);
		this.setSession("adminUid", userInfo.getUid());
		this.setSession("userName", userInfo.getUsername());
		this.setSession("userInfo", userInfo);
		
		//返回验证信息
		return authInfo;
	}
	
	/**
	 * 设置登陆缓存
	 * 
	 * @param key
	 * @param value
	 */
	private void setSession(Object key, Object value){
		Subject currentUser = SecurityUtils.getSubject();
		if(null != currentUser){
			Session session = currentUser.getSession();
			logger.info("Session默认超时时间为["+session.getTimeout()+"]毫秒");
			if(null != session){
				session.setAttribute(key, value);
			}
		}
	}

}
