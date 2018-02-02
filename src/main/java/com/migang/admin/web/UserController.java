package com.migang.admin.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.exception.BusinessException;

/**
 * 用户相关的Controller处理
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	/**
	 * 用户登陆页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String userLogin(Model model){
		//获取用户登陆状态
		Subject us = SecurityUtils.getSubject(); 
		
		//检查是否登陆　若登陆直接跳转到登陆后页面
		if(us.isAuthenticated()){
			return "redirect:/main/index";
		}
		
		return "userLogin";
	}
	
	/**
	 * 检查用户登陆状态
	 */
	@RequestMapping(value = "/checks", method = RequestMethod.POST)
	@ResponseBody
	private Object checkUserStatus(){
		//获取用户登陆状态
		Subject us = SecurityUtils.getSubject();

		//设置用户状态
		this.set("status", (us.isAuthenticated() ? 1 : 0));

		return this.getRet();
	}
	
	/**
	 * 处理用户登陆请求
	 * 
	 * @param String username
	 * @param String password
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	@ResponseBody
	private Object userDoLogin(@RequestParam String username, @RequestParam String password) throws BusinessException {
		//检查参数完整性
		if(!(ValidateUtils.checkUserName(username) && ValidateUtils.checkPassword(password))){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或密码格式错误");
		}
		
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try{
			//处理登陆信息
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
			//设置记住我 TODO 可以处理成: 关闭浏览器再打开时 还是登陆状态
			//token.setRememberMe(true);
			
			//对用户进行登陆校验
			currentUser.login(token);
			
			//检查是否登陆成功
			if(!currentUser.isAuthenticated()){
				throw new BusinessException(CommonConstant.USER_LOGIN_FAILURE, "登陆认证失败");
			}
			
			//验证通过 设置返回值
			this.set("isLogin", 1);
		}catch(UnknownAccountException uae){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户名不存在");
		}catch(IncorrectCredentialsException ice){
			throw new BusinessException(CommonConstant.USER_PWD_ERROR, "密码错误");
		}catch(LockedAccountException lae){
			throw new BusinessException(CommonConstant.USER_IS_LOCKED, "该用户被禁用");
		}catch(ExcessiveAttemptsException eae){
			throw new BusinessException(CommonConstant.USER_LOGIN_NUM_MORE_THAN_LIMIT, "登陆次数超限");
		}catch(AuthenticationException ae){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或密码错误！");
		}catch(Exception aes){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或密码错误");
		}
		
		//设置返回结果
		return this.getRet();
	}

	/**
	 * 用户退出操作
	 * 
	 * @return
	 * 
	 * @param addTime 2017年8月28日
	 * @param author     ChengBo
	 */
	@RequestMapping("/logout")
	private String userLoginOut(){
		//退出当前用户
		SecurityUtils.getSubject().logout();  
		
		//跳转页面
		return "redirect:/user/login";
	}
}
