package com.migang.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.migang.admin.entity.User;

/**
 * 前台用户Dao接口
 *
 *＠param addTime 2017-09-05
 *@param author   ChengBo
 */
public interface UserDao {

	//根据用户uid获取用户信息
	User getUserByUid(long uid);
	
	//新增用户数据
	long insertUserInfo(User userInfo);
	
	//根据手机号查询用户信息
	User getUserByUserMobile(String mobile);
	
	//更新用户联系人认证时间
	Boolean updateUserContactsAuth(@Param("uid") long uid, @Param("timeStamp") int timeStamp);
	
	//更新用户职业认证时间
	Boolean updateUserJobAuth(@Param("uid") long uid, @Param("timeStamp") int timeStamp);
	
	//更新用户私人信息认证时间
	Boolean updateUserPersonAuth(@Param("uid") long uid, @Param("timeStamp") int timeStamp);
	
	//更新用户手机认证时间
	Boolean updateUserMobileAuth(@Param("uid") long uid, @Param("timeStamp") int timeStamp);
	
	//更新用户银行卡信息失败
	Boolean updateUserBankCardAuth(@Param("uid") long uid, @Param("timeStamp") int timeStamp);
	
	//按条件查询用户列表数据
	List<User> getUserList(User userInfo);
	
	//汇总指定条件的用户总数
	int countUserNum(User userInfo);
	
	//查询指定用户uid的用户信息
	List<User> getUserByUids(Map<String, Object> filter);
	
	//更新用户的密码
	Boolean updateUserPwdByMobile(User userInfo);
	
	//更新用户信息
	Boolean updateUserInfoByUid(User UserInfo);
}
