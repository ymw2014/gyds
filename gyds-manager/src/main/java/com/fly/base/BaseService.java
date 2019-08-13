package com.fly.base;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.fly.domain.UserDO;
import com.fly.system.dao.UserDao;

public class BaseService {
	@Autowired UserDao userDao;
	/**
	   *       更新账户余额
	 * @param userId 用户编号
	 * @param price  账户需增加的金额
	 * @return 
	 */
	public synchronized boolean increaseMoney(Long userId,BigDecimal price) {
		UserDO user = userDao.get(userId);
		user.setAccount(user.getAccount().add(price));
		if(userDao.update(user)>0) {
			return true;
		}else {
			return false;
		}
	}

}
