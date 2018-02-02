package com.migang.admin.dao;

import com.migang.admin.entity.RepayRecord;

/**
 * 操作还款记录Dao
 *
 *＠param addTime 2017-11-03
 *@param author   ChengBo
 */
public interface RepayRecordDao {

	//查询还款记录数据
	RepayRecord getRepayRecordByBid(int orderId);
}
