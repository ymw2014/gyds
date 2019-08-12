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
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	int userIntoTeamExamine(Integer id, Integer status);

	int createProxyBus(Integer id, Integer status);

}
