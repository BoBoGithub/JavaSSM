package com.migang.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.enums.LoanOrderStatusEnum;
import com.migang.admin.entity.BorrowRecord;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.LoanService;

/**
 * 测试 借款Service相关
 *
 *@param addTime 2017-09-28
 *@param author     ChengBo
 */
public class LoanServiceImplTest extends BaseTest {

	@Autowired
	private LoanService loanService;
	
	/**
	 * 测试新增借款记录
	 * 
	 * @param addTime 2017年9月28日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testAddBorrowRecord() {
		try{
			//新增借款记录
			int lastInsertId = loanService.addBorrowRecord(1, "7天", "500");
			System.out.println("=== "+ lastInsertId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 获取用户最近的一条申请借款记录
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetLatelyBorrowRecordByUid() throws BusinessException{
		//调取数据
		BorrowRecord borrowRecord = loanService.getLatelyBorrowRecordByUid(1);
		System.out.println("==="+ borrowRecord);
	}
	
	/**
	 * 测试　获取最近的借款记录
	 * 
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetLatelyBorrowRecordList() throws BusinessException{
		//调取数据
		List<BorrowRecord> borrowRecordList = loanService.getLatelyBorrowRecordList(3);
		if(borrowRecordList.size() > 0){
			for(BorrowRecord br : borrowRecordList){
				System.out.println("=== "+ br);
			}
		}
	}
	
	/**
	 * 测试　查询借款记录列表
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testGetBorrowRecordList() throws BusinessException{
		//实例化Map
		Map<String, String> recordMap = new HashMap<String, String>();
		
		//设置查询条件
		recordMap.put("page", "1");
		recordMap.put("pageSize", "14");
		
		//查询数据
		List<BorrowRecord> listData = loanService.getBorrowRecordList(recordMap);
		if(listData.size()>0){
			for(BorrowRecord bRecord : listData){
				logger.debug("=== "+ bRecord);
			}
		}else{
			logger.debug("=== no data");
		}
	}
	
	/**
	 * 测试　汇总借款记录总条数
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testCountBorrowRecord() throws BusinessException{
			//实例化条件Map
			Map<String, String> recordMap = new HashMap<String, String>();
			
			//设置条件
			recordMap.put("uid", "1");
			
			//查询数据
			int totalNum = loanService.countUserNum(recordMap);
			logger.debug("==== {}", totalNum);
	}
	
	/**
	 * 测试订单状态的转换
	 * 
	 * @param addTime 2017年10月12日
	 * @param author     ChengBo
	 */
	@Test
	public void testLoanOrderStatusEnum(){
		logger.debug("=== "+LoanOrderStatusEnum.getValueByIndex(1));
	}
}
