package com.fly.system.servicce;

import java.util.List;
import java.util.Map;
import com.fly.domain.RegionDO;
import com.fly.domain.Tree;

/**
 * 
 * 
 * @author WangYanke
 * @email 15638836857@163.com
 * @date 2019-07-03 15:01:06
 */
public interface RegionService {
	/**
	 * 通过主键获取实体
	 * @param regionCode
	 * @return
	 */
	RegionDO get(Long regionCode);
	/**
	 * 通过条件参数获取list
	 * @param map
	 * @return
	 */
	List<RegionDO> list(Map<String, Object> map);
	/**
	 * 查询数据数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);
	/**
	 * 保存
	 * @param region
	 * @return
	 */
	int save(RegionDO region);
	/**
	 * 修改
	 * @param region
	 * @return
	 */
	int update(RegionDO region);
	/**
	 * 删除
	 * @param regionCode
	 * @return
	 */
	int remove(Long regionCode);
	/**
	 * 批量删除
	 * @param regionCodes
	 * @return
	 */
	int batchRemove(Integer[] regionCodes);
	/**
	 * 获取属性结构数据
	 * @param params
	 * @return
	 */
	Tree<RegionDO> getTree(Map<String,Object> params);
	/**
	 * 验证此分组下是否有管理员
	 * @param regionCode
	 * @return
	 */
	boolean checkRegionHasUser(Integer regionCode);
	/**
	 * 获取树形结构数据(包含团级)
	 * @param params
	 * @return
	 */
	Tree<RegionDO> getRegionTree(Map<String, Object> params);

}
