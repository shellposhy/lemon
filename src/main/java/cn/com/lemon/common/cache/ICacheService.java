package cn.com.lemon.common.cache;

public abstract interface ICacheService<T> {
	public abstract ICache<T> getCache(String cacheName);

	public abstract void addCache(ICache<T> cacheName);

	public abstract void removeCache(String cacheName);
}
