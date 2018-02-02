package com.migang.admin.web.admin;

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
import com.migang.admin.entity.Product;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.ProductService;

/**
 * 后台产品相关控制器
 *
 *@param addTime 2017-11-09
 *@param author    ChengBo
 */
@Controller
public class AdminProductController extends AdminBaseController {

	@Autowired
	private ProductService productService;
	
	/**
	 * 产品列表
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/product/list", method = RequestMethod.GET)
	private String productList(){
		return "admin/product/productList";
	}
	
	/**
	 * 新增产品页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/product/add", method = RequestMethod.GET)
	private String productAdd(){
		return "admin/product/productAdd";
	}
	
	/**
	 * 产品编辑
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/product/edit", method = RequestMethod.GET)
	private String productEdit(HttpServletRequest request, Model model){
		//接收参数
		int productId = Integer.parseInt(request.getParameter("productId"));
		
		//调取产品数据
		Product productData = productService.getProductById(productId);
		
		//页面赋值
		model.addAttribute("productData", productData);
		
		return "admin/product/productEdit";
	}
	
	/**
	 * 新增产品入库
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/product/do/add", method = RequestMethod.POST)
	@ResponseBody
	private Object doAddProduct(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置入库参数
		Map<String, String> productMap = new HashMap<String, String>();
		
		//接收参数
		productMap.put("productName", jsonObject.getString("productName"));
		productMap.put("borrowMoney", jsonObject.getString("borrowMoney"));
		productMap.put("borrowLimit", jsonObject.getString("borrowLimit"));
		productMap.put("dayRate", jsonObject.getString("dayRate"));
		productMap.put("serviceCharge", jsonObject.getString("serviceCharge"));
		productMap.put("productStatus", jsonObject.getString("productStatus"));
		
		//产品入库
		int productId = productService.addProduct(productMap);
		
		//设置返回结果
		this.set("addStatus", productId);
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 提交编辑产品
	 * 
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/product/do/edit", method = RequestMethod.POST)
	@ResponseBody
	private Object doEditProduct(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置入库参数
		Map<String, String> productMap = new HashMap<String, String>();
		
		//接收参数
		productMap.put("productId", jsonObject.getString("productId"));		
		productMap.put("productName", jsonObject.getString("productName"));
		productMap.put("borrowMoney", jsonObject.getString("borrowMoney"));
		productMap.put("borrowLimit", jsonObject.getString("borrowLimit"));
		productMap.put("dayRate", jsonObject.getString("dayRate"));
		productMap.put("serviceCharge", jsonObject.getString("serviceCharge"));
		productMap.put("productStatus", jsonObject.getString("productStatus"));
		
		//产品入库
		Boolean updStatus = productService.updProduct(productMap);
		
		//设置返回结果
		this.set("updStatus", (updStatus?1:0));
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 获取产品列表
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/product/get/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getProductList(@RequestBody String requestBody) throws BusinessException{
		//接收参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);

		//设置查询Map
		Map<String, String> productMap = new HashMap<String, String>();
		
		//接收参数
		productMap.put("page", jsonObject.getString("page"));
		productMap.put("pageSize", jsonObject.getString("pageSize"));
		
		//调取产品列表数据
		List<Product> productListData = productService.getProductList(productMap);
		
		//查询总条数
		int total = productService.countProduct();
		
		//设置返回结果
		this.set("productListData", productListData);
		this.set("total", total);
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 提交编辑产品
	 * 
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/admin/product/upd/status", method = RequestMethod.POST)
	@ResponseBody
	private Object updProductStatus(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);

		//接收参数
		int productId = jsonObject.getInteger("productId");
		int type = jsonObject.getInteger("type");
	
		//设置更新状态
		int status = (type == 1 ? 0 : (type == 2 ? 1 : -1));
		
		//产品入库
		Boolean updStatus = productService.updProductStatus(productId, status);
		
		//设置返回结果
		this.set("updStatus", (updStatus?1:0));
		
		//返回结果
		return this.getRet();
	}
}
