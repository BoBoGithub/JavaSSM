package com.migang.admin.entity;

/**
 * 中国省份城市区县数据实体
 *
 *@param addTime 2017-10-30
 *@param author    ChengBo
 */
public class Region {

	//设置自增id
	private int regionId;
	
	//区域名称
	private String name;
	
	//父级id
	private int parentId;

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "Region: "+regionId+" "+name+" "+parentId;
	}
}
