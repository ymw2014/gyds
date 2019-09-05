package com.fly.screen.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
/**
   * 大屏幕
 * @author Administrator
 *
 */
@Mapper
public interface ScreenDao {

	List<Map<String, Object>> getOrgSurvey(Map<String,Object> map);
	List<Map<String, Object>> getTeamByVolSurvey(Map<String,Object> map);
	List<Map<String, Object>> getNewsByTeamSurvey(Map<String,Object> map);
	List<Map<String, Object>> getCreateTeamSurvey(Map<String,Object> map);
	List<Map<String, Object>> getPublicNewsSurvey(Map<String,Object> map);
	List<Map<String, Object>> getPublicActivitySurvey(Map<String,Object> map);
	
}
