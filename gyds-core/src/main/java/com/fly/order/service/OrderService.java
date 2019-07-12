package com.fly.order.service;

import com.fly.order.domain.OrderDO;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * 
 * @author WangYaLei
 * @email 15638836857@163.com
 * @date 2019-07-12 15:10:18
 */
public interface OrderService {
	
	OrderDO get(Integer id);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
