package com.fly.signin.dao;

import com.fly.signin.domain.SigninDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 签到表
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-29 11:28:41
 */
@Mapper
public interface SigninDao {

	SigninDO get(Integer id);
	
	List<SigninDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SigninDO signin);
	
	int update(SigninDO signin);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
