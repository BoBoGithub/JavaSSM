package com.migang.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.dao.HelpFeedBackDao;
import com.migang.admin.dao.RegionDao;
import com.migang.admin.entity.HelpFeedBack;
import com.migang.admin.entity.Region;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.HelpService;

/**
 * 帮助中心Service
 * 
 *＠param addTime 2017-10-27
 *@param author   ChengBo
 */
@Service
public class HelpServiceImpl implements HelpService {

	@Autowired
	private HelpFeedBackDao feedBackDao;
	
	@Autowired
	private RegionDao regionDao;
	
	/**
	 * 问题返回入库
	 * 
	 * @param feedMap
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Override
	public Boolean addFeedback(Map<String, String> feedMap) throws BusinessException {
		//检查入库参数
		HelpFeedBack feedBack = checkAddFeedBack(feedMap);
		
		//反馈入库
		return feedBackDao.addHelpFeedBack(feedBack);
	}
	
	/**
	 * 检查反馈数据
	 * 
	 * @param feedMap
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	private HelpFeedBack checkAddFeedBack(Map<String, String> feedMap) throws BusinessException{
		//检查参数是否为空
		if(feedMap == null){
			throw new BusinessException(CommonConstant.HELP_FEED_BACK_PARAM_ERROR, "问题反馈参数错误");
		}
		
		//实例化问题反馈Bean
		HelpFeedBack feedBack = new HelpFeedBack();
		
		//检查反馈内容
		if(!feedMap.containsKey("content") || feedMap.get("content").isEmpty()){
			throw new BusinessException(CommonConstant.HELP_FEED_BACK_CONTENT_ERROR, "问题反馈内容错误");
		}else{
			feedBack.setContent(feedMap.get("content"));
		}
		
		//检查是否存在uid
		if(feedMap.containsKey("uid")){
			if(!ValidateUtils.checkIsNumber(feedMap.get("uid"))){
				throw new BusinessException(CommonConstant.USER_UID_ERROR, "问题反馈用户状态错误");
			}else{
				feedBack.setUid(Integer.parseInt(feedMap.get("uid")));
			}
		}
		
		//检查是否存在udid
		if(feedMap.containsKey("udid")){
			if(feedMap.get("udid").isEmpty()){
				throw new BusinessException(CommonConstant.USER_UDID_ERROR, "用户设备标识错误");
			}else{
				feedBack.setUdid(feedMap.get("udid"));
			}
		}
		
		//设置入库时间
		feedBack.setAddTime(CommonUtils.getCurrTimeStamp());
		
		return feedBack;
	}

	/**
	 * 查询反馈列表
	 * 
	 * @param feedMap
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	public List<HelpFeedBack> getFeedBackList(Map<String, String> feedMap){
		//设置查询条件
		Map<String, Object> feedBackMap = checkGetFeedBackParam(feedMap);

		//查询反馈数据
		List<HelpFeedBack> feedListData = feedBackDao.getFeedBackList(feedBackMap);
		
		return feedListData;
	}
	
	/**
	 * 检查查询反馈条件
	 * 
	 * @param feedMap
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	private Map<String, Object> checkGetFeedBackParam(Map<String, String> feedMap){
		//设置查询条件
		Map<String, Object> feedBackMap = new HashMap<String, Object>();
		
		//检查查询参数
		int uid					= ValidateUtils.checkIsNumber(feedMap.get("uid")) ? Integer.parseInt(feedMap.get("uid")) : 0;
		int page				= ValidateUtils.checkIsNumber(feedMap.get("page")) ? Integer.parseInt(feedMap.get("page")) : 1;
		int pageSize		= ValidateUtils.checkIsNumber(feedMap.get("pageSize")) && Integer.parseInt(feedMap.get("pageSize")) < 100  ? Integer.parseInt(feedMap.get("pageSize")) : 10;
		int startTime		= !feedMap.get("startTime").isEmpty() ? Integer.parseInt(CommonUtils.getTimeStampByDate(feedMap.get("startTime")+" 00:00:00")) : 0;
		int endTime		= !feedMap.get("endTime").isEmpty() ? Integer.parseInt(CommonUtils.getTimeStampByDate(feedMap.get("endTime")+" 23:59:59")) : 0;
		String udid			= feedMap.get("udid") == null || feedMap.get("udid").isEmpty()?"":feedMap.get("udid");
		
		//设置查询条件
		feedBackMap.put("offset", 		(page>0 ? (page -1)*pageSize : 1));
		feedBackMap.put("pageSize", 	pageSize);
		feedBackMap.put("udid", 			udid);
		feedBackMap.put("uid", 			uid);
		feedBackMap.put("startTime",startTime);
		feedBackMap.put("endTime", 	endTime);
		
		return feedBackMap;
	}
	
	/**
	 * 汇总反馈记录总条数
	 * 
	 * @param feedMap
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	public int countFeedBack(Map<String, String> feedMap){
		//设置查询条件
		Map<String, Object> feedBackMap = checkGetFeedBackParam(feedMap);
		
		//查询反馈数据
		return feedBackDao.counFeedBack(feedBackMap);
	}
	
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
	public List<Region> getRegionByParentId(int pid) throws BusinessException{
		//检查参数
		if(!ValidateUtils.checkIsNumber(pid)){
			throw new BusinessException(CommonConstant.HELP_REGION_PID_ERROR, "上级地区参数错误");
		}
		
		//调取地区数据
		List<Region> regionList = regionDao.getRegionByParentId(pid);
		
		return regionList;
	}
	
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
	public Map<Integer, String> getRegionByIds(List<Integer> regionIds){
		//设置返回值
		Map<Integer, String> regionMap = new HashMap<Integer, String>();
		
		//检查参数
		if(regionIds.size() <= 0){
			return regionMap;
		}
		
		//调取地区数据
		List<Region> regionData = regionDao.getRegionByIds(regionIds);
		
		//提取返回地区数据
		if(regionData.size() > 0){
			for(int i = 0; i<regionData.size();i++){
				if(regionIds.contains(regionData.get(i).getRegionId())){
					regionMap.put(regionData.get(i).getRegionId(), regionData.get(i).getName());
				}
			}
		}
		
		return regionMap;
	}
}
