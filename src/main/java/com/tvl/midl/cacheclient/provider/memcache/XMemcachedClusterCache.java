package com.tvl.midl.cacheclient.provider.memcache;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.ICache;
import com.tvl.midl.cacheclient.exception.TvlCacheException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.transcoders.Transcoder;

/**
 * @description
 * @author st.z
 */
public class XMemcachedClusterCache implements IMemcachedCache {
	private static final Logger LOGGER = LoggerFactory.getLogger(XMemcachedClusterCache.class);

	private List<IMemcachedCache> mecList = null;

	private CacheActionThread actionThread = null;

	public XMemcachedClusterCache(List<IMemcachedCache> mecList) {
		this.mecList = mecList;
		this.actionThread = new CacheActionThread();
		this.actionThread.setDaemon(true);
		this.actionThread.setName("actionThread");
		this.actionThread.start();
	}

	/**
	 * get write cahce list
	 * 
	 * @return
	 */
	List<IMemcachedCache> getWriteCacheList() {
		return mecList;
	}

	/**
	 * get read cache
	 * 
	 * @return
	 */
	IMemcachedCache getReadCache() {
		int size = mecList.size();
		Random rand = new Random();
		int index = rand.nextInt(size);
		LOGGER.info("----总节点数:" + size + ",当前读取节点:" + index);
		IMemcachedCache cache = mecList.get(index);
		return cache;
	}

	@Override
	public void put(String key, String value) throws TvlCacheException {
		this.put(key, value);
	}

	@Override
	public void put(String key, int expire, String value) throws TvlCacheException {
		put(key, expire, value);
	}

	@Override
	public String get(String key) throws TvlCacheException {
		ICache cache = getReadCache();
		if (cache != null) {
			return cache.get(key);
		}
		return null;
	}

	@Override
	public void oput(String key, Object value) throws TvlCacheException {
		List<IMemcachedCache> cacheList = getWriteCacheList();
		// 同步写主
		IMemcachedCache mainCache = cacheList.get(0);
		try {
			mainCache.oput(key, value);
		} catch (Exception ex) {
			LOGGER.error("write cache failed.");
			throw new TvlCacheException("write cache failed.", ex);
		}
		// 异步写从
		for (int i = 1; i < cacheList.size(); i++) {
			CacheAction action = new CacheAction();
			IMemcachedCache cache = cacheList.get(i);
			action.setCache(cache);
			action.setKey(key);
			action.setValue(value);
			action.setAction("write");
			actionThread.add(action);
		}
	}

	@Override
	public void oput(String key, int expire, Object value) throws TvlCacheException {
		List<IMemcachedCache> cacheList = getWriteCacheList();
		// 同步写主
		IMemcachedCache mainCache = cacheList.get(0);
		try {
			mainCache.oput(key, expire, value);
		} catch (Exception ex) {
			throw new TvlCacheException("write cache failed.", ex);
		}
		// 异步写从
		for (int i = 1; i < cacheList.size(); i++) {
			CacheAction action = new CacheAction();
			IMemcachedCache cache = cacheList.get(i);
			action.setCache(cache);
			action.setKey(key);
			action.setExpire(expire);
			action.setValue(value);
			action.setAction("write");
			actionThread.add(action);
		}
	}

	@Override
	public Object oget(String key) throws TvlCacheException {
		ICache cache = getReadCache();
		if (cache != null) {
			return cache.get(key);
		}
		return null;
	}

	@Override
	public Map<String, Object> omget(Collection<String> keys) throws TvlCacheException {
		IMemcachedCache cache = getReadCache();
		if (cache != null) {
			return cache.omget(keys);
		}
		return null;
	}

	@Override
	public void remove(String key) throws TvlCacheException {
		List<IMemcachedCache> cacheList = getWriteCacheList();
		// 同步写主
		ICache mainCache = cacheList.get(0);
		try {
			mainCache.remove(key);
		} catch (Exception ex) {
			throw new TvlCacheException("delete cache failed.", ex);
		}
		// 异步写从
		for (int i = 1; i < cacheList.size(); i++) {
			CacheAction action = new CacheAction();
			IMemcachedCache cache = cacheList.get(i);
			action.setCache(cache);
			action.setKey(key);
			action.setAction("delete");
			actionThread.add(action);
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public void flushAll() throws TvlCacheException {
		List<IMemcachedCache> cacheList = getWriteCacheList();
		try {
			for (IMemcachedCache cache : cacheList) {
				cache.flushAll();
			}
		} catch (Exception ex) {
			throw new TvlCacheException("flushAll failed:" + ex);
		}
	}

	@Override
	public long getConnectTimeout() throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public void setConnectTimeout(long connectTimeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> T get(String key, long timeout, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> T get(String key, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> T get(String key, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> GetsResponse<T> gets(String key) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> GetsResponse<T> gets(String key, Transcoder transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long opTimeout, Transcoder<T> transcoder)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, Transcoder<T> transcoder)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long opTime,
			Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long timeout)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, Transcoder<T> transcoder)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean set(String key, int exp, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> void setWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean add(String key, int exp, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean add(String key, int exp, Object value, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> void addWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> boolean replace(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean replace(String key, int exp, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean replace(String key, int exp, Object value, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean replace(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public void replaceWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> void replaceWithNoReply(String key, int exp, T value, Transcoder<T> transcoder)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public boolean append(String key, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean append(String key, Object value, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public void appendWithNoReply(String key, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public boolean prepend(String key, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean prepend(String key, Object value, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public void prependWithNoReply(String key, Object value) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public boolean cas(String key, int exp, Object value, long cas) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, int exp, T value, Transcoder<T> transcoder, long timeout, long cas)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean cas(String key, int exp, Object value, long timeout, long cas) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, int exp, T value, Transcoder<T> transcoder, long cas) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, int exp, CASOperation<T> operation, Transcoder<T> transcoder)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation,
			Transcoder<T> transcoder) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, GetsResponse<T> getsResponse, CASOperation<T> operation)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, int exp, CASOperation<T> operation) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> boolean cas(String key, CASOperation<T> operation) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> void casWithNoReply(String key, GetsResponse<T> getsResponse, CASOperation<T> operation)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> void casWithNoReply(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation)
			throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> void casWithNoReply(String key, int exp, CASOperation<T> operation) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public <T> void casWithNoReply(String key, CASOperation<T> operation) throws TvlCacheException {
		throwExceptionIfNotAccess();

	}

	@Override
	public boolean delete(String key, int time) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean delete(String key, long opTimeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean delete(String key, long cas, long opTimeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean delete(String key) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public void deleteWithNoReply(String key, int time) throws TvlCacheException {
		throwExceptionIfNotAccess();

	}

	@Override
	public void deleteWithNoReply(String key) throws TvlCacheException {
		throwExceptionIfNotAccess();

	}

	@Override
	public boolean touch(String key, int exp, long opTimeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public boolean touch(String key, int exp) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return false;
	}

	@Override
	public <T> T getAndTouch(String key, int newExp, long opTimeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public <T> T getAndTouch(String key, int newExp) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public long incr(String key, long delta) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public long incr(String key, long delta, long initValue) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public long incr(String key, long delta, long initValue, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public long incr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public void incrWithNoReply(String key, long delta) throws TvlCacheException {
		throwExceptionIfNotAccess();

	}

	@Override
	public long decr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public long decr(String key, long delta) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public long decr(String key, long delta, long initValue) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public long decr(String key, long delta, long initValue, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
		return 0;
	}

	@Override
	public void decrWithNoReply(String key, long delta) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAllWithNoReply() throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAll(long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAll(InetSocketAddress address) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAllWithNoReply(InetSocketAddress address) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAll(InetSocketAddress address, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAll(String host) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAllWithNoReply(int exptime) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAll(int exptime, long timeout) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAllWithNoReply(InetSocketAddress address, int exptime) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	@Override
	public void flushAll(InetSocketAddress address, long timeout, int exptime) throws TvlCacheException {
		throwExceptionIfNotAccess();
	}

	/**
	 * 若不允许使用对应方法，则抛出异常
	 */
	void throwExceptionIfNotAccess() {
		throw new TvlCacheException("Memcached cluster不支持该操作");
	}

}
