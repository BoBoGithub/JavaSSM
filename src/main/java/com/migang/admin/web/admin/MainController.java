package com.migang.admin.web.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.migang.admin.entity.AdminMenu;
import com.migang.admin.entity.AdminUser;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.PermitService;

/**
 * 后台主页控制器
 * 
 * @param addTime 2017-08-25
 * @param author    ChengBo
 */
@Controller
@RequestMapping("/main")
public class MainController extends AdminBaseController {

	@Autowired
	private PermitService permitService;
	
	/**
	 * 后台主页面
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String main(HttpServletRequest request, Model model) throws BusinessException{
		//调取用户信息
		AdminUser adminUserInfo = this.getAdminUser(request);
		
		//设置用户拥有的一级菜单变量
		List<AdminMenu> userBigMenuList = getUserBigMenuList(adminUserInfo.getRoleId(), adminUserInfo.getUid());
		
		//设置用户信息
		model.addAttribute("username", adminUserInfo.getUsername());
		model.addAttribute("userBigMenuList", userBigMenuList);
		
		return "mainIndex";
	}
	
	/**
	 * 调取指定角色的一级菜单
	 * 
	 * @param roleId
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 */
	private List<AdminMenu> getUserBigMenuList(int roleId, long adminUid) throws BusinessException{
		//设置用户拥有的一级菜单变量
		List<AdminMenu> userBigMenuList = new ArrayList<AdminMenu>();
		
		//调取一级菜单数据
		List<AdminMenu> bigMenuList = permitService.getAdminMenuByPid(0);
		
		//调取当前角色下的菜单数据
		List<String> menuPriveList = permitService.getAdminRolePrivByRoleId(roleId);
		
		//提取用户的一级菜单 超级管理有有全部权限
		if(bigMenuList.size() >0 && (menuPriveList.size() > 0 || adminUid == 1)){
			for(AdminMenu adminMenu : bigMenuList){
				if(menuPriveList.contains(adminMenu.getUrl()) || adminUid == 1){
					userBigMenuList.add(adminMenu);
				}
			}
		}
		
		return userBigMenuList;
	}
	
	/**
	 * 后台欢迎页面
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	private String welcome(){
		return "welcome";
	}
}
