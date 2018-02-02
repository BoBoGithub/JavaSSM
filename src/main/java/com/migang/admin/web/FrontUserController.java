package com.migang.admin.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.migang.admin.dto.ResultData;
import com.migang.admin.entity.User;
import com.migang.admin.service.UserService;

/**
 * 前端用户操作控制器
 *
 *@param addTime 2017-09-05
 *@param author    ChengBo
 */
@Controller
@RequestMapping("/front")
public class FrontUserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 默认首页
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String indexPage(HttpSession session, Model model){
		//设置登陆缓存Key
		String loginCacheKey = session.getId();
		
		//获取用户缓存数据
		User user = userService.getUserInfoByUniqueKey(loginCacheKey);
		
		//检查是否登陆
		if(user == null || user.getMobile() == ""){
			return "redirect:/front/user/login";
		}
		
		//设置用户数据
		model.addAttribute("user", user);
		
		return "front/index";
	}
	
	/**
	 * 用户登陆页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	private String userLogin(HttpSession session){
		//设置登陆缓存Key
		String loginCacheKey = session.getId();
		
		User user = userService.getUserInfoByUniqueKey(loginCacheKey);
		
		//检查是否登陆
		if(user != null && user.getMobile() != ""){
			return "redirect:/front/index";
		}
		
		return "front/userLogin";
	}
	
	/**
	 * 用户登陆操作
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 * @throws Exception 
	 */
	@RequestMapping(value = "/user/dologin", method = RequestMethod.POST)
	@ResponseBody
	private ResultData<Map<String, Object>> userDoLogin(HttpSession session, HttpServletResponse response, @RequestParam String username, @RequestParam String password) throws Exception {	
		//设置用户登陆唯一标示
//		String loginUniqueKey = session.getId();
		
		//调取用户数据
//		User user =userService.doUserLogin(username, password, loginUniqueKey);
		
		//设置用户登陆Cooke
//		CookieUtils.addCookie(response, CookieUtils.COOKIE_USER_NAME, user.getMobile(), "localhost");	
		
		//验证通过 设置返回值
		this.set("isLogin", 1);
		
		//设置返回结果
		return this.getRet();
	}
	
	/**
	 * 用户退出操作
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	@ResponseBody
	private ResultData<Map<String, Object>> userLogout(HttpSession session){
		//设置登陆缓存Key
		String loginCacheKey = session.getId();
		
		//清除用户缓存信息
		userService.userLogout(loginCacheKey);
		
		//设置返回结果
		this.set("isLogin", 0);
		
		//返回结果
		return this.getRet();
	}
}
