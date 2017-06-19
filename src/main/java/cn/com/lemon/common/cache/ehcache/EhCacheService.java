package cn.com.lemon.common.cache.ehcache;

import cn.com.lemon.common.cache.ICache;
import cn.com.lemon.common.cache.ICacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhCacheService implements ICacheService<Cache> {
	private CacheManager manager;

	public EhCacheService() {
		this.manager = new CacheManager();
	}

	public ICache<Cache> getCache(String cacheName) {
		Cache c = this.manager.getCache(cacheName);
		if (c == null) {
			return null;
		}
		return new EhCacheImpl(c);
	}

	public void addCache(ICache<Cache> cache) {
		this.manager.addCache((Cache) cache.getMetaCache());
	}

	public void removeCache(String cacheName) {
		this.manager.removeCache(cacheName);
	}
}
