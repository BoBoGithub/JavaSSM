package com.migang.admin.dao;

import com.migang.admin.entity.UserJob;

/**
 * 用户职业信息Dao
 *
 *@param addTime 2017-09-25
 *@param author    ChengBo
 */
public interface UserJobDao {

	//新增用户职业信息
	Boolean insertUserJobInfo(UserJob userJob);
	
	//调取用户职业信息数据
	UserJob getUserJobInfoByUid(int uid);
}
