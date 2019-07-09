package com.fly.common.service;


import com.fly.domain.LogDO;
import com.fly.domain.PageDO;
import com.fly.utils.Query;

/**
 *
 */
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
