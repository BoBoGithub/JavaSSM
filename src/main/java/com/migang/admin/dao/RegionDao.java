package com.migang.admin.dao;

import java.util.List;

import com.migang.admin.entity.Region;

/**
 * 中国省份城市区县数据实体
 *
 *@param addTime 2017-10-30
 *@param author    ChengBo
 */
public interface RegionDao {

	//查询指定父级下的地区数据
	List<Region> getRegionByParentId(int pid);
	
	//通过地区id查询地区数据
	List<Region> getRegionByIds(List<Integer> regionIds);
}