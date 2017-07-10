package cn.com.lemon.framework;

import java.util.List;

import cn.com.lemon.framework.model.BaseEntity;

/**
 * The <code>Dao</code> interface is the basic data persistence api.
 * <p>
 * Support the third utilities(e.g.<code>Mybatis</code>) implement this
 * interface
 * 
 * @see List
 * @author shellpo shih
 * @version 1.0
 */
interface Dao<T extends BaseEntity> {

	/* =========search========== */
	T get(int id);

	List<T> list(String... values);

	List<T> listAll();

	List<T> listPage(int start, int size, String... values);

	/* =========count=========== */
	int count(String... values);

	/* =========insert========== */
	int save(T t);

	/* =========delete========== */
	boolean delete(int id);

	/* =========update========== */
	boolean update(T t);
}
