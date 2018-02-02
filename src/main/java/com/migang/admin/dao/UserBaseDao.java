package com.migang.admin.dao;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.UserBase;

/**
 * 用户基础信息Dao
 *
 *@param addTime 2017-09-25
 *@param author    ChengBo
 */
public interface UserBaseDao {

	//新增用户基本信息
	Boolean insertUserBaseInfo(UserBase userBase);
	
	//根据用户uids查询用户基本信息数据
	List<UserBase> getUserBaseByUids(Map<String, Object> filterMap);
	
	//获取用户的基本信息
	UserBase getUserBaseByUid(int uid);
}
