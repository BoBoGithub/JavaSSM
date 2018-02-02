package com.migang.admin.dao;

import java.util.List;
import java.util.Map;

import com.migang.admin.entity.HelpFeedBack;

/**
 * 帮助中心－问题反馈Dao
 *
 *＠param addTime 2017-10-27
 *@param author   ChengBo
 */
public interface HelpFeedBackDao {

	//新增问题反馈
	Boolean addHelpFeedBack(HelpFeedBack feedBack);
	
	//查询反馈数据列表
	List<HelpFeedBack> getFeedBackList(Map<String, Object> feedBackMap);
	
	//汇总反馈总条数
	int counFeedBack(Map<String, Object> feedBackMap);
}
