package cn.com.lemon.framework;

import java.util.List;

public abstract interface BaseDao<T> {

	/* =========search========== */
	public abstract T find(int id);

	public abstract List<T> find(String... values);

	public abstract List<T> find(int start, int end, String... values);

	public abstract List<T> findAll();

	/* =========insert========== */
	public abstract int insert(T t);

	public abstract void inset(T t);

	public abstract void batchInsert(List<T> t);

	/* =========delete========== */
	public abstract boolean delete(int id);

	public abstract boolean delete(T t);

	public abstract boolean batchDelete(List<T> t);

	/* =========update========== */
	public abstract boolean update(T t);

	public abstract boolean batchUpdate(List<T> t);

}
