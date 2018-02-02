package com.migang.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.migang.admin.entity.Product;
import com.migang.admin.exception.BusinessException;

/**
 * 产品相关的Service
 *
 *@param addTime 2017-11-09
 *@param author     ChengBo
 */
@Service
public interface ProductService {

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
	int addProduct(Map<String, String> productMap) throws BusinessException;
	
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
	Boolean updProduct(Map<String, String> productMap) throws BusinessException;
	
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
	List<Product> getProductList(Map<String, String> productMap) throws BusinessException;
	
	/**
	 * 汇总产品的总条数
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	int countProduct();
	
	/**
	 * 获取指定产品的数据
	 * 
	 * @param productId
	 * @return
	 * 
	 * @param addTime 2017年11月9日
	 * @param author     ChengBo
	 */
	Product getProductById(int productId);
	
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
	Boolean updProductStatus(int productId, int status) throws BusinessException;
}
