package cn.com.lemon.common.cache;

public abstract interface ICache<T> {
	public abstract void put(Object key, Object value);

	public abstract Object get(Object key);

	public abstract boolean remove(Object key);

	public abstract String getName();

	public abstract void removeAll();

	public abstract int getSize();

	public abstract T getMetaCache();
}
