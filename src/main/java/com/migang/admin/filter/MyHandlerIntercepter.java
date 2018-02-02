package com.migang.admin.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.migang.admin.entity.AdminUser;
import com.migang.admin.service.PermitService;

/**
 * 自定义拦截器 处理后台权限控制
 *
 *@param addTime 2017-10-20
 *@param author    ChengBo
 */
public class MyHandlerIntercepter implements HandlerInterceptor {
	
	@Autowired
	private PermitService permitService; 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//提取用户信息
		AdminUser userInfo = (AdminUser) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
		if(userInfo.getUid() == 1){
			return true;
		}
		
		//查询能用户是否有访问权限
		permitService.checkVistPermit(Integer.parseInt(userInfo.getRoleId()+""), request.getRequestURI().toString());
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
