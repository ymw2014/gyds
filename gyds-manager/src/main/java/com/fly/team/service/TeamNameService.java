package com.fly.team.service;

public interface TeamNameService {
	/**
	 * 	建团申请审核
	 * @param id
	 * @param status
	 * @return
	 */
	int createTeamExamine(Integer id, Integer status);
	
	/**
	 * 	用户申请入团审核
	 * @param id
	 * @param status
	 * @return
	 */
	int userIntoTeamExamine(Integer id, Integer status);
	/**
	 * 	代理商入驻审核
	 * @param id
	 * @param status
	 * @return
	 */
	int createProxyBus(Integer id, Integer status);

}
