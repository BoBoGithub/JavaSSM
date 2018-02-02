package com.migang.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.migang.admin.entity.Product;

/**
 * 操作产品的Dao
 *
 *@param addTime 2017-11-09
 *@param author   ChengBo
 */
public interface ProductDao {

	//新增产品入库
	int addProduct(Product product);
	
	//查询能产品列表
	List<Product> getProductList(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	//汇总产品总条数
	int countProductNum();
	
	//查询指定产品的数据
	Product getProductById(int productId);
	
	//更新产品数据
	Boolean updProductById(Product product);
	
	//更新产品状态信息　
	Boolean updProductStatusById(@Param("productId") int productId, @Param("status") int status);
}
