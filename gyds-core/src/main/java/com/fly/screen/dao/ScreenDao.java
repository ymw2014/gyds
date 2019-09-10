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
	/**
	 * 组织概况查询
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getOrgSurvey(Map<String,Object> map);
	/**
	 * 实名志愿者排行
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getTeamByVolSurvey(Map<String,Object> map);
	/**
	 * 活动发布量排行
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getNewsByTeamSurvey(Map<String,Object> map);
	/**
	 * 入驻团队量
	 * @param map
	 * @return
	 */
	Map<String, Object> getCreateTeamSurvey(Map<String,Object> map);
	/**
	 * 咨询发布量
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPublicNewsSurvey(Map<String,Object> map);
	/**
	 * 活动发布量
	 * @param map
	 * @return
	 */
	Map<String, Object> getPublicActivitySurvey(Map<String,Object> map);
	/**
	 * 活动类型
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPublicActTypeSurvey(Map<String,Object> map);
	
	/**
	 * 每月发布活动数量
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPublicNewsMon(Map<String,Object> map);
	
	/**
	 * 每年发布活动数量
	 * @param map
	 * @return
	 */
	Map<String, Object> getPublicNewsCount(Map<String,Object> map);
	
	/**
	 * 政治面貌统计
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPoliticsSurvey(Map<String,Object> map);
	
	/**
	 * 学历统计
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getEducationSurvey(Map<String,Object> map);
	
	/**
	 * 职业统计
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPostSurvey(Map<String,Object> map);
	
	/**
	 * 统计性别
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getSexCount(Map<String,Object> map);
	
	/**
	 * 统计年龄
	 * @param map
	 * @return
	 */
	Map<String, Object> getAgeCount(Map<String,Object> map);
	
	
	/**
	 * 统计年龄参加活动次数
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getAgeAndActCount(Map<String,Object> map);
	
	/**
	 * 总活动数
	 * @param map
	 * @return
	 */
	Map<String, Object> getActCount(Map<String,Object> map);
	
	/**
	 * 签到次数
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getPointsCount(Map<String,Object> map);
	
	Map<String, Object> getSigninCount(Map<String,Object> map);
	/**
	   *  实名成员数
	 * @param map
	 * @return
	 */
	Map<String, Object> getVolCount(Map<String,Object> map);
	
	/**
	   *   按日期统计签到人数
	 * @param map
	 * @return
	 */
	Map<String, Object> getPoinCount(Map<String,Object> map);
	
	/**
	 * 代理商统计
	 * @param map
	 * @return
	 */
	Map<String, Object> getProCount(Map<String,Object> map);
	
	/**
	 * 区域统计
	 * @param map
	 * @return
	 */
	Map<String, Object> getCuxCount(Map<String,Object> map);
	
	/**
	   *  人数统计
	 * @param map
	 * @return
	 */
	Map<String, Object> getUserCount(Map<String,Object> map);
	
	/**
	 * 签到概况
	 * @param map
	 */
	List<Map<String, Object>> getsigMon(Map<String,Object> map);
	
	/**
	 * 参与活动次数排行
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getactOrder(Map<String,Object> map);
	
	/**
	 * 月签到排名
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getsigOrder(Map<String,Object> map);
}
