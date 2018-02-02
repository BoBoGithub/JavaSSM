package com.migang.admin.dao;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.BorrowRecord;

/**
 * 借款记录Dao接口
 *
 *＠param addTime 2017-08-22
 *@param author   ChengBo
 */
public interface BorrowRecordDao {

	//新增借款入库
	Boolean insertBorrowRecord(BorrowRecord borrowRecord);
	
	//获取用户最近的一条申请借款记录
	BorrowRecord getLatelyBorrowRecordByUid(long uid);
	
	//查询最新的借款记录
	List<BorrowRecord> getLatelyBorrowList(int limit);
	
	//按条件查询借款记录列表
	List<BorrowRecord> getBorrowList(Map<String, Object> bRecordMap);
	
	//汇总借款记录总条数
	int countBorrowRecord(Map<String, Object> bRecordMap);
	
	//汇总指定用户的借款汇总数
	List<Map<String, String>> sumRecordNumByUids(Map<String, Object> recordMap);
	
	//调取指定借款id的订单数据
	BorrowRecord getBorrowRecordById(Integer orderId);
	
	//更新订单审核状态
	Boolean updBorrowRecordCheckStatus(Map<String, Object> updMap);
}
