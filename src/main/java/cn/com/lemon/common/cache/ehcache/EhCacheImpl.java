package cn.com.lemon.common.cache.ehcache;

import java.util.logging.Logger;

import cn.com.lemon.common.cache.ICache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhCacheImpl implements ICache<Cache> {
	private Logger logger = Logger.getLogger("DefaultCache");
	private Cache cache;

	public EhCacheImpl(Cache cache) {
		this.cache = cache;
	}

	public EhCacheImpl(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal,
			long timeToLiveSeconds, long timeToIdleSeconds) {
		this.cache = new Cache(name, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds,
				timeToIdleSeconds);
	}

	public String getName() {
		return this.cache.getName();
	}

	public void put(Object key, Object value) {
		this.cache.put(new Element(key, value));
	}

	public Object get(Object key) {
		Element e = this.cache.get(key);
		if (e != null) {
			this.logger.finest("====>" + this.cache.getName() + ":get object by key:[" + key + "] ok.");
			return e.getObjectValue();
		}
		return null;
	}

	public boolean remove(Object key) {
		return this.cache.remove(key);
	}

	public void removeAll() {
		this.cache.removeAll();
	}

	public int getSize() {
		return this.cache.getSize();
	}

	public Cache getMetaCache() {
		return this.cache;
	}
}
