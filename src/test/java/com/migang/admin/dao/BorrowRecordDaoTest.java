package com.migang.admin.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.BorrowRecord;

/**
 * 用户BorrowRecordDao单元测试
 *
 * @param addTime 2017年9月27日
 * @param author     ChengBo
 */
public class BorrowRecordDaoTest extends BaseTest {

	@Autowired
	private BorrowRecordDao borrowRecordDao;
	
	/**
	 * 测试　新增借款记录数据
	 * 
	 * @param addTime 2017年9月28日
	 * @param author     ChengBo
	 */
	@Test
	public void testInsertBorrowRecord(){
		//实例化借款记录Bean
		BorrowRecord borrowRecord = new BorrowRecord();
		
		//设置入库参数
		borrowRecord.setUid(1);
		borrowRecord.setBorrowMoney(new BigDecimal("500"));
		borrowRecord.setBorrowLimit(1);
		borrowRecord.setBorrowInterest(new BigDecimal("2"));
		borrowRecord.setServiceCharge(new BigDecimal("25"));
		borrowRecord.setApplyTime(CommonUtils.getCurrTimeStamp());
		borrowRecord.setRemark("借款申请入库");
		
		//借款记录入库
		Boolean ret = borrowRecordDao.insertBorrowRecord(borrowRecord);
		System.out.println("==== "+ ret + " "+ borrowRecord.getBrecordId());
	}
	
	/**
	 * 测试　获取用户最近一条正在借款中的借款记录
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetLatelyBorrowRecordByUid(){
		//获取数据
		BorrowRecord recordData = borrowRecordDao.getLatelyBorrowRecordByUid(1);
		System.out.println("=== "+recordData);
	}
	
	/**
	 * 测试　查询最近的借款记录列表
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetLatelyBorrowList(){
		//查询数据
		List<BorrowRecord> recordList = borrowRecordDao.getLatelyBorrowList(10);
		if(recordList.size()>0){
			for(BorrowRecord br : recordList){
				System.out.println("==="+ br);
			}
		}
	}
	
	/**
	 * 测试　按条件查询借款记录列表
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetBorrowList(){
		//实例化条件Bean
		Map<String, Object> bRecordMap = new HashMap<String, Object>();
		
		//设置查询条件
		bRecordMap.put("uid", 1);
		bRecordMap.put("borrowLimit", 2);
		bRecordMap.put("offset", 1);
		bRecordMap.put("pageSize", 12);
		
		//查询借款记录数据
		List<BorrowRecord> recordList = borrowRecordDao.getBorrowList(bRecordMap);
		if(recordList.size() > 0){
			for(BorrowRecord borrowRecord : recordList){
				logger.debug("==== {}", borrowRecord);
			}
		}else{
			logger.debug("=== no data");
		}
	}
	
	/**
	 * 汇总借款记录条数
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 */
	@Test
	public void testCountBorrowRecord(){
		//实例化条件Bean
		Map<String, Object> bRecordMap = new HashMap<String, Object>();
		
		//设置查询条件
		bRecordMap.put("uid", 1);
		
		//汇总条数
		int totalNum = borrowRecordDao.countBorrowRecord(bRecordMap);
		logger.debug("=== {}", totalNum);
	}
	
	/**
	 * 测试　汇总指定用户的借款记录数
	 * 
	 * @param addTime 2017年10月13日
	 * @param author     ChengBo
	 */
	@Test
	public void testSumRecordNumByUids(){
		//实例化查询条件
		Map<String, Object> recordMap = new HashMap<String, Object>();
		
		//设置用户列表
		List<Integer> uidList = new ArrayList<Integer>();
		uidList.add(1);
		uidList.add(2);
		uidList.add(26);
		
		//设置查询条件
		recordMap.put("uids", uidList);
		
		//查询数据
		List<Map<String, String>> sumData = borrowRecordDao.sumRecordNumByUids(recordMap);
		logger.debug("=== testSumRecordNumByUids: {}", sumData);
	}
}