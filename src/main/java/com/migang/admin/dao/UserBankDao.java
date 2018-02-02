package com.migang.admin.dao;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.UserBank;

/**
 * 用户银行卡信息Dao
 *
 *@param addTime 2017-09-25
 *@param author    ChengBo
 */
public interface UserBankDao {

	//新增用户银行卡
	Boolean insertUserBankInfo(UserBank userBank);
	
	//获取用户银行卡信息
	UserBank getUserBankByUid(long uid);
	
	//查询指定用户的银行信息
	List<UserBank> getUserBankByUids(Map<String, Object> filterMap);
}