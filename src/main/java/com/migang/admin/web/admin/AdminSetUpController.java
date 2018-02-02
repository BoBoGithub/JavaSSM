package com.migang.admin.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.entity.AdminMenu;
import com.migang.admin.entity.AdminRole;
import com.migang.admin.entity.AdminUser;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.AdminUserService;
import com.migang.admin.service.PermitService;

/**
 * 后台管理－系统设置相关控制器
 *
 *@param addTime 2017-10-15
 *@param author    ChengBo
 */
@Controller
public class AdminSetUpController extends AdminBaseController {

	@Autowired
	private PermitService permitService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	/**
	 * 后台管理用户列表页
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/user/list", method = RequestMethod.GET)
	private String adminUserList(){
		return "admin/setup/userList";
	}
	
	/**
	 * 新增后台管理用户
	 * 
	 * @param model
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/user/add", method = RequestMethod.GET)
	private String addUser(Model model) throws BusinessException{
		//调取角色列表数据
		List<AdminRole> roleListData = permitService.getRoleList(1, 1000);
		
		//页面赋值
		model.addAttribute("roleListData", roleListData);
		
		return "admin/setup/userAdd";
	}
	
	/**
	 * 用户编辑页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/user/edit", method = RequestMethod.GET)
	private String editUser(HttpServletRequest request, Model model) throws BusinessException{
	 	//接收用户uid
	   	Integer adminUid = Integer.parseInt(request.getParameter("adminUid"));
	   	
	   	//调取用户数据
	   	AdminUser adminUserInfo = adminUserService.getAdminUserInfoByUid(adminUid);
	   	
		//调取角色列表数据
		List<AdminRole> roleData = permitService.getRoleList(1, 10000);
		
		//设置当前用户的角色数据
		AdminRole adminUserRole = new AdminRole();
		
		//设置选中状态
		List<Map<String, String>> roleListData = new ArrayList<Map<String, String>>();
		if(roleData.size()>0){
			for(int i=0;i<roleData.size();i++){
				//初始化Map
				Map<String, String> roleMap = new HashMap<String, String>();
				
				//设置角色数据
				roleMap.put("roleId", roleData.get(i).getRoleId()+"");
				roleMap.put("roleName", roleData.get(i).getRoleName());
				if(roleData.get(i).getRoleId() == adminUserInfo.getRoleId()){
					//设置选中状态
					roleMap.put("checked", "checked");
					
					//设置用户的角色数据
					adminUserRole = roleData.get(i);
				}else{
					roleMap.put("checked", "");
				}
				
				//追加到返回值
				roleListData.add(roleMap);
			}
		}
	   	
	   	//页面赋值
	   	model.addAttribute("adminUserInfo", adminUserInfo);
	   	model.addAttribute("roleListData", roleListData);
	   	model.addAttribute("adminUserRole", adminUserRole);
	    
		return "admin/setup/userEdit";
	}
	
	/**
	 * 修改个人用户密码
	 * 
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年10月31日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/user/edit/pwd")
	private String editPwd(HttpServletRequest request, Model model) throws BusinessException{
		//调取用户信息
		AdminUser adminUserInfo = getAdminUser(request);
		
		//页面赋值
		model.addAttribute("adminUserInfo", adminUserInfo);
		
		//页面渲染
		return "admin/setup/userEditPwd";
	}
	
	/**
	 * 没有权限 访问页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月20日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/no/permit", method = RequestMethod.GET)
	private String requestNoPermit(){
		return "common/noPermit";
	}
	
	/**
	 * 用户角色管理列表页
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/role/list", method = RequestMethod.GET)
	private String userRoleList(){
		return "admin/setup/roleList";
	}
	
	/**
	 * 新增角色页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/role/add", method = RequestMethod.GET)
	private String userRoleAdd(){
		return "admin/setup/roleAdd";
	}
	
	/**
	 * 设置角色权限页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月18日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/role/permit", method = RequestMethod.GET)
	private String userRolePermit(HttpServletRequest request, Model model) throws BusinessException{
	 	//接收角色id
	   	Integer roleId = Integer.parseInt(request.getParameter("roleId"));
	   	
		//调取当前角色的权限数据
		List<String> privList = permitService.getAdminRolePrivByRoleId(roleId);
		
		//调取菜单数据
		List<Map<String, String>> menuListData = permitService.getMenuTree();
		
		//标记当前角色的权限
		if(menuListData.size()>0 && privList.size()>0){
			for(int i=0;i<menuListData.size();i++){
					//设置选中状态
					menuListData.get(i).put("checked", (privList.contains(menuListData.get(i).get("url")) ? "checked" : ""));
			}
		}
		
		//设置页面数据
		model.addAttribute("roleId", roleId);
		model.addAttribute("menuListData", menuListData);
		
		return "admin/setup/rolePermitAdd";
	}
	
	/**
	 * 角色修改页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/role/edit", method = RequestMethod.GET)
	private String userRoleEdit(HttpServletRequest request, Model model) throws BusinessException{
		 	//接收角色id
		   	Integer roleId = Integer.parseInt(request.getParameter("roleId"));
		
			//调取角色数据
			AdminRole roleData = permitService.getRoleDataById(roleId);
			
			//设置角色数据
			model.addAttribute("roleData", roleData);
			
			//返回模板
			return "admin/setup/roleEdit";
	}
	
	/**
	 * 角色用户列表
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/role/user", method = RequestMethod.GET)
	private String roleUserList(HttpServletRequest request, Model model){
	 	//接收角色id
	   	Integer roleId = Integer.parseInt(request.getParameter("roleId"));
	   	
		//设置角色ID
		model.addAttribute("roleId", roleId);
		
		return "admin/setup/roleUserList";
	}
	
	/**
	 * 菜单管理页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/menu/list", method = RequestMethod.GET)
	private String menuList(Model model) throws BusinessException{
		//调取菜单数据
		List<Map<String, String>> menuListData = permitService.getMenuTree();
		
		//设置页面数据
		model.addAttribute("menuListData", menuListData);
		
		return "admin/setup/menuList";
	}
	
	/**
	 * 新增菜单页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/menu/add", method = RequestMethod.GET)
	private String menuAdd(HttpServletRequest request, Model model) throws BusinessException{
		//接收父级菜单id
		Integer menuId = Integer.parseInt(request.getParameter("menuId"));
		
		//调取菜单数据
		List<Map<String, String>> menuListData = permitService.getMenuTree();
		
		//提取当前菜单的数据
		AdminMenu adminMenu = permitService.getAdminMenuById(menuId);
		
		//设置页面数据
		model.addAttribute("menuId", menuId);
		model.addAttribute("adminMenu", adminMenu);
		model.addAttribute("menuListData", menuListData);
		
		return"admin/setup/menuAdd";
	}
	
	/**
	 * 修改菜单页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/menu/edit", method = RequestMethod.GET)
	private String menuEdit(HttpServletRequest request, Model model) throws BusinessException{		
		//接收父级菜单id
		Integer menuId = Integer.parseInt(request.getParameter("menuId"));
		
		//调取菜单数据
		List<Map<String, String>> menuListData = permitService.getMenuTree();
		
		//提取当前菜单的数据
		AdminMenu adminMenu = permitService.getAdminMenuById(menuId);
		
		//调取上级菜单数据
		AdminMenu adminMenuP = permitService.getAdminMenuById(adminMenu.getParentId());
		
		//设置页面数据
		model.addAttribute("menuId", menuId);
		model.addAttribute("adminMenu", adminMenu);
		model.addAttribute("adminMenuP", adminMenuP);
		model.addAttribute("menuListData", menuListData);
		
		return"admin/setup/menuEdit";
	}
	
	/**
	 * 获取用户列表数据
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/get/user/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getUserList(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//接收参数
		String username	= jsonObject.getString("userName");
		int page					= Integer.parseInt(jsonObject.getString("page"));
		int pageSize			= Integer.parseInt(jsonObject.getString("pageSize"));
		
		//获取用户列表数据
		List<AdminUser> userList = adminUserService.getAdminUserList(username, page, pageSize);
		
		//获取数据总条数
		int userTotalNum		= adminUserService.getTotalUserNum(username);
		
		//设置返回数据
		this.set("list", userList);
		this.set("total", userTotalNum);
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 处理新增用户
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 */
	@RequestMapping("/admin/setup/user/post/add")
	@ResponseBody
	private Object postAddUser(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//接收参数
		String userName	=  jsonObject.getString("userName");
		String password	= jsonObject.getString("password");
		String realName	= jsonObject.getString("realName");
		int roleId					= Integer.parseInt(jsonObject.getString("roleId"));
		
		//检查参数完整性
		if(!(ValidateUtils.checkUserName(userName) && ValidateUtils.checkPassword(password))){
			throw new BusinessException(CommonConstant.USER_OR_PWD_ERROR, "用户名或密码格式错误");
		}
		
		//新用户入库
		int ret = adminUserService.addAdminUser(userName, password, realName, roleId);
		
		//设置返回结果
		this.set("ret", ret);
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 新增管理菜单提交
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/add/menu", method = RequestMethod.POST)
	@ResponseBody
	private Object addAdminMenu(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置参数
		Map<String, String> menuParam = new HashMap<String, String>();
		
		//设置角色名称
		menuParam.put("parentId", jsonObject.getString("parentId"));
		menuParam.put("menuName", jsonObject.getString("menuName"));
		menuParam.put("requestUrl", jsonObject.getString("requestUrl"));
		menuParam.put("menuStatus", jsonObject.getString("menuStatus"));
		
		//菜单入库
		int menuId = permitService.addAdminMenu(menuParam);
		
		//设置返回结果
		this.set("ret", menuId);
		
		//返回
		return this.getRet();
	}
	
	/**
	 * 编辑管理菜单提交
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/edit/menu", method = RequestMethod.POST)
	@ResponseBody
	private Object editAdminMenu(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置参数
		Map<String, String> menuParam = new HashMap<String, String>();
		
		//设置角色名称
		menuParam.put("menuId", jsonObject.getString("menuId"));
		menuParam.put("parentId", jsonObject.getString("parentId"));
		menuParam.put("menuName", jsonObject.getString("menuName"));
		menuParam.put("requestUrl", jsonObject.getString("requestUrl"));
		menuParam.put("menuStatus", jsonObject.getString("menuStatus"));
		
		//菜单入库
		Boolean ret = permitService.editAdminMenu(menuParam);
		
		//设置返回结果
		this.set("ret", (ret ? 1: 0));
		
		//返回
		return this.getRet();
	}
	
	/**
	 * 删除管理菜单提交
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/del/menu", method = RequestMethod.POST)
	@ResponseBody
	private Object deleteAdminMenu(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置参数
		Map<String, String> menuParam = new HashMap<String, String>();
		
		//设置角色名称
		menuParam.put("menuId", jsonObject.getString("menuId"));
		menuParam.put("menuStatus", "-1");
		
		//菜单入库
		Boolean ret = permitService.editAdminMenu(menuParam);
		
		//设置返回结果
		this.set("ret", (ret ? 1: 0));
		
		//返回
		return this.getRet();
	}
	
	/**
	 * 新增用户角色提交
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/add/role", method = RequestMethod.POST)
	@ResponseBody
	private Object addUserRole(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置参数
		Map<String, String> roleParam = new HashMap<String, String>();
		
		//设置角色名称
		roleParam.put("roleName", jsonObject.getString("roleName"));
		roleParam.put("roleDesc", jsonObject.getString("roleDesc"));
		roleParam.put("roleStatus", jsonObject.getString("roleStatus"));
		
		//角色入库
		int roleId = permitService.addAdminUserRole(roleParam);
		
		//设置返回结果
		this.set("ret", roleId);
		
		//返回
		return this.getRet();
	}
	
	/**
	 * 修改角色数据
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/edit/role", method = RequestMethod.POST)
	@ResponseBody
	private Object editUserRole(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置参数
		Map<String, String> roleParam = new HashMap<String, String>();
		
		//设置角色名称
		roleParam.put("roleId", jsonObject.getString("roleId"));
		roleParam.put("roleName", jsonObject.getString("roleName"));
		roleParam.put("roleDesc", jsonObject.getString("roleDesc"));
		roleParam.put("roleStatus", jsonObject.getString("roleStatus"));
		
		//角色入库
		Boolean ret = permitService.editRoleById(roleParam);
		
		//设置返回结果
		this.set("ret", ret);
		
		//返回
		return this.getRet();
	}
	
	/**
	 * 删除角色数据
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月17日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/del/role", method = RequestMethod.POST)
	@ResponseBody
	private Object delUserRole(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置参数
		Map<String, String> roleParam = new HashMap<String, String>();
		
		//设置角色名称
		roleParam.put("roleId", jsonObject.getString("roleId"));
		roleParam.put("roleStatus", "-1");
		
		//角色入库
		Boolean ret = permitService.editRoleById(roleParam);
		
		//设置返回结果
		this.set("ret", ret);
		
		//返回
		return this.getRet();
	}
	
	/**
	 * 获取用户角色列表数据
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月16日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/get/role/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getRoleList(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//接收查询页数
		int page			= Integer.parseInt(jsonObject.getString("page"));
		int pageSize	= Integer.parseInt(jsonObject.getString("pageSize"));
		
		//调取角色数据
		List<AdminRole> adminRoleData = permitService.getRoleList(page, pageSize);
		
		//调取总条数
		int totalNum = permitService.getCountRoleNum();
		
		//设置返回结果
		this.set("list", adminRoleData);
		
		//设置发那会总条数
		this.set("total", totalNum);
		
		//返回数据
		return this.getRet();
	}
	
	/**
	 * 设置角色权限
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/setup/set/role/permit", method = RequestMethod.POST)
	@ResponseBody
	private Object setRolePriv(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//提取参数
		int roleId = Integer.parseInt(jsonObject.getString("roleId"));
		
		//提取菜单id
		@SuppressWarnings("unchecked")
		List<Integer> menuIds = (List<Integer>) jsonObject.get("menuIds");
		
		//更新角色权限数据
		Boolean ret = permitService.setAdminRolePriv(roleId, menuIds);
		
		//设置返回结果
		this.set("ret", (ret?1:0));
		
		return this.getRet();
	}
	
	/**
	 * 获取角色用户列表数据
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年10月19日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/get/role/user/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getRoleUserList(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//接收查询页数
		int roleId			= Integer.parseInt(jsonObject.getString("roleId"));
		int page			= Integer.parseInt(jsonObject.getString("page"));
		int pageSize	= Integer.parseInt(jsonObject.getString("pageSize"));
		
		//调取角色数据
		List<AdminUser> adminRoleData = adminUserService.getAdminUserListByRoleId(roleId, page, pageSize);
		
		//调取总条数
		int totalNum = adminUserService.getTotalUserNumByRoleId(roleId);
		
		//设置返回结果
		this.set("list", adminRoleData);
		
		//设置发那会总条数
		this.set("total", totalNum);
		
		//返回数据
		return this.getRet();
	}
	
	/**
	 * 删除后台管理用户
	 * 
	 * @param requestBody
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月23日
	 * @param author     ChengBo
	 * @return 
	 */
	@RequestMapping(value = "/admin/setup/del/user", method = RequestMethod.POST)
	@ResponseBody
	private Object delAdminUser(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置Map
		Map<String, String> adminUserMap = new HashMap<String, String>();
		
		//接收参数
		String status = jsonObject.getString("status").equals("1") ? "1" : (jsonObject.getString("status").equals("0") ? "0" : "-1");
		
		//设置更新参数
		adminUserMap.put("uid", jsonObject.getString("adminUid"));
		adminUserMap.put("status", status);
		
		//更新管理用户状态
		Boolean ret = adminUserService.updAdminUserByUid(adminUserMap);
		
		//设置返回结果
		this.set("ret", (ret ? 1 : 0));
		
		//返回响应
		return this.getRet();
	}
	
	/**
	 * 修改后台管理用户数据
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月24日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/user/post/edit", method = RequestMethod.POST)
	@ResponseBody
	private Object editAdminUser(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置Map
		Map<String, String> adminUserMap = new HashMap<String, String>();
		
		//接收参数
		adminUserMap.put("uid", jsonObject.getString("adminUid"));
		adminUserMap.put("roleId", jsonObject.getString("roleId"));
		adminUserMap.put("userName", jsonObject.getString("userName"));
		adminUserMap.put("realName", jsonObject.getString("realName"));
		adminUserMap.put("password", jsonObject.getString("password"));
	
		//更新管理用户状态
		Boolean ret = adminUserService.updAdminUserByUid(adminUserMap);
		
		//设置返回结果
		this.set("ret", (ret ? 1 : 0));
		
		//返回响应
		return this.getRet();
	}
	
	/**
	 * 修改用户的密码
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月31日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/user/post/edit/pwd", method = RequestMethod.POST)
	@ResponseBody
	private Object editAdminUserPwd(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置Map
		Map<String, String> adminUserMap = new HashMap<String, String>();
		
		//接收参数
		adminUserMap.put("uid", getAdminUser(request).getUid()+"");
		adminUserMap.put("password", jsonObject.getString("password"));
		
		//更新用户密码
		Boolean ret = adminUserService.updAdminUserByUid(adminUserMap);
		
		//设置返回结果
		this.set("ret", (ret?1:0));
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 管理用户个人信息
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/admin/setup/user/info", method = RequestMethod.GET)
	private String userInfo(HttpServletRequest request, Model model) throws BusinessException{
		//调取当前的用户信息
		AdminUser adminUser = this.getAdminUser(request);
		
		//调取用户角色数据
		AdminRole roleData = permitService.getRoleDataById(adminUser.getRoleId());
		
		//页面赋值
		model.addAttribute("roleName", roleData.getRoleName());
		model.addAttribute("userName", adminUser.getUsername());
		model.addAttribute("realName", adminUser.getRealname());
		model.addAttribute("email", adminUser.getEmail());
		
		return "admin/setup/userInfo";
	}
	
	/**
	 * 更新用户个人信息
	 * 
	 * @param request
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/user/edit/info", method = RequestMethod.POST)
	@ResponseBody
	private Object updUserInfo(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);

		//设置更新Map
		Map<String, String> updMap = new HashMap<String, String>();
		
		//设置更新参数
		updMap.put("adminUid", this.getAdminUser(request).getUid()+"");
		updMap.put("realName", jsonObject.getString("realName"));
		updMap.put("email", jsonObject.getString("email"));
		
		//更新数据
		Boolean ret = adminUserService.updAdminInfo(updMap);
		
		//设置返回结果
		this.set("updRet", (ret ? 1 : 0));
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 调取子菜单
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月20日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/setup/get/sub/menu", method = RequestMethod.POST)
	@ResponseBody
	private Object getSubMenu(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置返回结果变量
		List<Map<String, Object>> subMenuList = new ArrayList<Map<String, Object>>();
		
		//提取父级菜单id 一级菜单id
		int pid = jsonObject.getIntValue("pid");
		if(pid != 0){
			//提取角色id
			int roleId				 = this.getAdminUser(request).getRoleId();
			long adminUid	= this.getAdminUser(request).getUid();
			
			//调取当前角色下的菜单数据
			List<String> menuPriveList = permitService.getAdminRolePrivByRoleId(roleId);
			
			//调取二级菜单数据
			List<AdminMenu> menuList = permitService.getAdminMenuByPid(pid);
			if(menuList.size()>0){
				//提取二级菜单
				for(AdminMenu adminMenu : menuList){
					//只提去显示状态的菜单
					if(adminMenu.getStatus() != 0 || (adminUid != 1 && !menuPriveList.contains(adminMenu.getUrl()))){
						continue;
					}
					
					//实例化三级Map
					List<Map<String, String>> thirdMenuList = new ArrayList<Map<String, String>>();
					
					//调取三级菜单
					List<AdminMenu> thirdMenuDataList = permitService.getAdminMenuByPid(adminMenu.getId());
					if(thirdMenuDataList.size() > 0){
						//提取三级菜单
						for(AdminMenu thirdAdminMenu : thirdMenuDataList){
							//只提去显示状态的菜单
							if(thirdAdminMenu.getStatus() == 0 && (adminUid == 1 || menuPriveList.contains(thirdAdminMenu.getUrl()))){
								//实例化三级Map
								Map<String, String> thirdMenuMap = new HashMap<String, String>();
								
								//设置三级菜单数据
								thirdMenuMap.put("name", thirdAdminMenu.getName());
								thirdMenuMap.put("url", thirdAdminMenu.getUrl());
								
								//追加到返回列表中
								thirdMenuList.add(thirdMenuMap);
							}
						}
					}
					
					//实例化二级Map
					Map<String, Object> secondMenuMap = new HashMap<String, Object>();
					
					//设置二级菜单名称
					secondMenuMap.put("name", adminMenu.getName());
					secondMenuMap.put("child", thirdMenuList);
					
					//追加到返回List中
					subMenuList.add(secondMenuMap);
				}
			}
		}
		
		//设置返回结果
		this.set("subMenuList", subMenuList);
		
		//返回结果
		return this.getRet();
	}
}
