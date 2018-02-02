package com.migang.admin.service;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.BorrowRecord;
import com.migang.admin.entity.RepayRecord;
import com.migang.admin.exception.BusinessException;

/**
 * 贷款Service接口
 *
 * @param addTime 2017年9月27日
 * @param author     ChengBo
 */
public interface LoanService {

	/**
	 * 获取贷款项目配置
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 */
	Map<String, Object> getLoanItem();

	/**
	 * 新增借款记录
	 * 
	 * @param uid
	 * @param itemLimit
	 * @param borrowMoney
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月28日
	 * @param author     ChengBo
	 */
	int addBorrowRecord(long uid, String itemLimit, String borrowMoney) throws BusinessException;
	
	/**
	 * 获取用户最近的一条申请借款记录
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 */
	BorrowRecord getLatelyBorrowRecordByUid(long uid) throws BusinessException;
	
	/**
	 * 获取最近的借款记录
	 * 
	 * @param limit
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 */
	List<BorrowRecord> getLatelyBorrowRecordList(int limit) throws BusinessException;
	
	/**
	 * 按条件查询借款记录数据
	 * 
	 * @param bRecord
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 */
	List<BorrowRecord> getBorrowRecordList(Map<String, String> recordMap) throws BusinessException;
	
	/**
	 * 汇总订单总数
	 * 
	 * @param recordMap
	 * @return
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	int countUserNum(Map<String, String> recordMap) throws BusinessException;
	
	/**
	 * 汇总指定用户的借款记录数
	 * 
	 * @param uidList
	 * @return
	 * 
	 * @param addTime 2017年10月13日
	 * @param author     ChengBo
	 */
	Map<Long, Map<String, String>> sumRecordNumByUids(List<Long> uidList);
	
	/**
	 * 获取指定订单ID的订单数据
	 * 
	 * @param orderId
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	BorrowRecord getBorrowRecordById(Integer orderId);
	
	/**
	 * 查询还款记录数据
	 * 
	 * @param orderId
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	RepayRecord getRepayRecordByBid(int orderId);
	
	/**
	 * 人工审核订单提交操作
	 * 
	 * @param updOrderParam
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	Boolean updPersonCheckOrderStatus(Map<String, String> updOrderParam) throws BusinessException;
	
	/**
	 *  还款操作
	 * 
	 * @param uid
	 * @param orderId
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月16日
	 * @param author     ChengBo
	 */
	Boolean repayLoan(long uid, int orderId) throws BusinessException;
}