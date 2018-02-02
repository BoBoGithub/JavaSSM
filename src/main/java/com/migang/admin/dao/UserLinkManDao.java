package com.migang.admin.dao;

import java.util.List;

import com.migang.admin.entity.UserLinkMan;

/**
 * 用户联系人信息Dao
 *
 *@param addTime 2017-09-26
 *@param author    ChengBo
 */
public interface UserLinkManDao {

	//新增用户联系人信息
	Boolean insertUserLinkManInfo(UserLinkMan userLinkMan);
	
	//查询指定用户联系人信息
	List<UserLinkMan> getUserLinkManByUid(int uid);
}