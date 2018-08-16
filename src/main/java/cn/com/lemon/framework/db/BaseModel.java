package cn.com.lemon.framework.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 基于Spring JDBC模式的映射基础对象
 * 
 * @author shishb
 * @version 1.0
 */
public class BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> lowerFieldMap;
	private String tableName;

	public BaseModel() {
		lowerFieldMap = new HashMap<String, Object>();
	}

	public void put(String field, Object value) {
		lowerFieldMap.put(field.toLowerCase(), value);
	}

	public Object get(String field) {
		return lowerFieldMap.get(field.toLowerCase());
	}

	public void setLowerFieldMap(Map<String, Object> lowerFieldMap) {
		this.lowerFieldMap = lowerFieldMap;
	}

	public void remove(String field) {
		lowerFieldMap.remove(field.toLowerCase());
	}

	public Set<String> getLowerFieldSet() {
		return lowerFieldMap.keySet();
	}

	public Collection<Object> getValueSet() {
		return lowerFieldMap.values();
	}

	public int fieldCount() {
		return lowerFieldMap.size();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
