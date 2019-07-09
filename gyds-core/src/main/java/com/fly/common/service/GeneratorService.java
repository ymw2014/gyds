/**
 * 
 */
package com.fly.common.service;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @Time
 * @description
 * 
 */

public interface GeneratorService {
	List<Map<String, Object>> list();

	byte[] generatorCode(String[] tableNames);
}
