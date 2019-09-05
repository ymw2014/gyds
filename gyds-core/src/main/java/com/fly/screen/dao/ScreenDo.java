package com.fly.screen.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
/**
   * 大屏幕
 * @author Administrator
 *
 */
@Mapper
public interface ScreenDo {

	Map<String, Object> getOrgSurvey(Map<String,Object> map);
	Map<String, Object> getTeamByVolSurvey(Map<String,Object> map);
	Map<String, Object> getNewsByTeamSurvey(Map<String,Object> map);
	Map<String, Object> getCreateTeamSurvey(Map<String,Object> map);
	Map<String, Object> getPublicNewsSurvey(Map<String,Object> map);
	Map<String, Object> getPublicActivitySurvey(Map<String,Object> map);
	
}
