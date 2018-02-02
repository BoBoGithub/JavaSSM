package com.migang.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.migang.admin.entity.HelpFeedBack;
import com.migang.admin.entity.Region;
import com.migang.admin.exception.BusinessException;

/**
 * 帮助中心Service
 * 
 *＠param addTime 2017-10-27
 *@param author   ChengBo
 */
@Service
public interface HelpService {
	
	/**
	 * 问题返回入库
	 * 
	 * @param feedMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	Boolean addFeedback(Map<String, String> feedMap) throws BusinessException;
	
	/**
	 * 查询反馈列表
	 * 
	 * @param feedMap
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	List<HelpFeedBack> getFeedBackList(Map<String, String> feedMap);
	
	/**
	 * 汇总反馈记录总条数
	 * 
	 * @param feedMap
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	int countFeedBack(Map<String, String> feedMap);
	
	/**
	 * 查询指定父级下的地区数据
	 * 
	 * @param pid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月30日
	 * @param author     ChengBo
	 */
	List<Region> getRegionByParentId(int pid) throws BusinessException;
	
	/**
	 * 通过地区id数组调取地区名称数据
	 * 
	 * @param regionIds
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	Map<Integer, String>	getRegionByIds(List<Integer> regionIds) throws BusinessException;
}
