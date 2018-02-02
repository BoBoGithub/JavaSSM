package com.migang.admin.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.dao.ProductDao;
import com.migang.admin.entity.Product;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.ProductService;

/**
 * 产品相关的Service
 *
 *@param addTime 2017-11-09
 *@param author     ChengBo
 */
@Service
public class ProductServiceImpl implements ProductService {

	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDao productDao;
	
	/**
	 * 新增产品入库
	 * 
	 * @param productMap
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Override
	public int addProduct(Map<String, String> productMap) throws BusinessException {
		//检查参数
		Product insertParam = checkProduct(productMap);
		
		try{
			//产品入库
			productDao.addProduct(insertParam);
			
			//返回插入的id
			return insertParam.getProductId();
		}catch(Exception ex){
			//记录入库失败日志
			logger.error("addProduct param: {}, Exception: {}", productMap, ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.PRODUCT_INSERT_FAILURE, "产品入库失败");
		}
	}
	
	/**
	 * 检查产品参数
	 * 
	 * @param productMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	private Product checkProduct(Map<String, String> productMap) throws BusinessException{
		//检查参数是否为空
		if(productMap == null || productMap.size() == 0){
			throw new BusinessException(CommonConstant.PRODUCT_PARAM_ERROR, "产品参数错误");
		}
		
		//检查产品名称
		if(!productMap.containsKey("productName") || productMap.get("productName").isEmpty()){
			throw new BusinessException(CommonConstant.PRODUCT_NAME_PARAM_ERROR, "产品名称参数错误");
		}
		
		//检查产品名称
		if(!productMap.containsKey("borrowMoney") || !ValidateUtils.checkIsDouble(productMap.get("borrowMoney")) || Double.parseDouble(productMap.get("borrowMoney")) <= 0){
			throw new BusinessException(CommonConstant.PRODUCT_BORROW_MONEY_PARAM_ERROR, "产品借款金额参数错误");
		}
		
		//检查借款额度是否是100的整数倍
		if(Double.parseDouble(productMap.get("borrowMoney"))%100 !=0){
			throw new BusinessException(CommonConstant.PRODUCT_BORROW_MONEY_PARAM_ERROR, "产品借款金额必须是100的倍数");
		}
		
		//借款期限
		if(!productMap.containsKey("borrowLimit") || !ValidateUtils.checkIsNumber(productMap.get("borrowLimit")) || Integer.parseInt(productMap.get("borrowLimit")) <= 0){
			throw new BusinessException(CommonConstant.PRODUCT_BORROW_LIMIT_PARAM_ERROR, "产品借款期限参数错误");
		}
		
		//检查日利率
		if(!productMap.containsKey("dayRate") || !ValidateUtils.checkIsDouble(productMap.get("dayRate")) || Double.parseDouble(productMap.get("dayRate")) <= 0 || Double.parseDouble(productMap.get("dayRate")) >= 99){
			throw new BusinessException(CommonConstant.PRODUCT_DAY_RATE_PARAM_ERROR, "产品日利率参数错误");
		}
		
		//检查服务费
		if(!productMap.containsKey("serviceCharge") || !ValidateUtils.checkIsDouble(productMap.get("serviceCharge")) || Double.parseDouble(productMap.get("serviceCharge")) <= 0){
			throw new BusinessException(CommonConstant.PRODUCT_SERVICE_PARAM_ERROR, "产品服务费参数错误");
		}

		//检查产品状态
		if(!productMap.containsKey("productStatus") || !Arrays.asList(new String[]{"-1","0","1"}).contains(productMap.get("productStatus"))){
			throw new BusinessException(CommonConstant.PRODUCT_BORROW_LIMIT_PARAM_ERROR, "产品状态参数错误");
		}

		//实例化返回结果
		Product productParam = new Product();
		
		//设置返回结果
		productParam.setProductName(productMap.get("productName"));
		productParam.setBorrowMoney(new BigDecimal(productMap.get("borrowMoney")));
		productParam.setBorrowLimit(Integer.parseInt(productMap.get("borrowLimit")));
		productParam.setDayRate(new BigDecimal(productMap.get("dayRate")));
		productParam.setServiceCharge(new BigDecimal(productMap.get("serviceCharge")));
		productParam.setStatus(Integer.parseInt(productMap.get("productStatus")));
		
		//返回产品实体
		return productParam;
	}

	/**
	 * 查询产品列表数据
	 * 
	 * @param productMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@Override
	public List<Product> getProductList(Map<String, String> productMap) throws BusinessException {
		//检查查询参数
		int page = productMap.containsKey("page") && Integer.parseInt(productMap.get("page")) > 0 ? Integer.parseInt(productMap.get("page")) : 1;
		int pageSize = productMap.containsKey("pageSize") && Integer.parseInt(productMap.get("pageSize")) > 0 ? Integer.parseInt(productMap.get("pageSize")) : 9;
		int offset = (page-1)*pageSize;
		
		//查询产品数据
		List<Product> productListData = productDao.getProductList(offset, pageSize);
		
		return productListData;
	}

	/**
	 * 汇总产品的总条数
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@Override
	public int countProduct() {
		//汇总总条数
		int total = productDao.countProductNum();
		
		return total;
	}

	/**
	 * 获取指定产品的数据
	 * 
	 * @param productId
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@Override
	public Product getProductById(int productId) {
		//查询指定产品的数据
		Product productData = productDao.getProductById(productId);
		
		return productData;
	}

	/**
	 * 更新产品信息
	 * 
	 * @param productMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	@Override
	public Boolean updProduct(Map<String, String> productMap) throws BusinessException {
		//检查产品id数据
		if(!productMap.containsKey("productId") || !ValidateUtils.checkIsNumber(productMap.get("productId"))){
			throw new BusinessException(CommonConstant.PRODUCT_ID_PARAM_ERROR, "更新的产品信息错误");
		}
		
		//检查更新参数
		Product productInfo = checkProduct(productMap);
		
		//设置产品id参数
		productInfo.setProductId(Integer.parseInt(productMap.get("productId")));
		
		//更新产品数据
		Boolean ret = productDao.updProductById(productInfo);
		
		return ret;
	}

	/**
	 * 更新产品状态
	 * 
	 * @param productId
	 * @param status
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Override
	public Boolean updProductStatus(int productId, int status) throws BusinessException {
		//检查参数
		if(productId == 0){
			throw new BusinessException(CommonConstant.PRODUCT_ID_PARAM_ERROR, "更新的产品信息错误");
		}
		
		//检查产品状态
		if(!Arrays.asList(new String[]{"-1","0","1"}).contains(status+"")){
			throw new BusinessException(CommonConstant.PRODUCT_BORROW_LIMIT_PARAM_ERROR, "产品状态参数错误");
		}
		
		//更新产品状态数据
		Boolean updStatus = productDao.updProductStatusById(productId, status);
		
		return updStatus;
	}

}
