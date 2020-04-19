package com.tvl.midl.cacheclient;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;

import com.tvl.midl.cacheclient.config.CacheConfig;
import com.tvl.midl.cacheclient.exception.TvlCacheException;
import com.tvl.midl.cacheclient.provider.memcache.IMemcachedCache;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.transcoders.Transcoder;

/**
 * @description
 * @author shite.zhu
 * @date 2016年10月10日
 * 
 */
public final class MemcacheClient extends CacheBuilder implements IMemcachedCache {

	private static final String CACHE_MEM = "memcached";

	private IMemcachedCache memCache;

	public MemcacheClient(CacheConfig cacheConfig) {
		super(CACHE_MEM, cacheConfig);
	}

	/**
	 * 初始化方法
	 */
	public void init() {
		ICache cache = createCache();
		if (cache instanceof IMemcachedCache) {
			this.memCache = (IMemcachedCache) cache;
		} else {
			throw new TvlCacheException("----创建memCache对象失败");
		}
	}

	@Override
	public void put(String key, String value) throws TvlCacheException {
		this.memCache.put(key, value);
	}

	@Override
	public void put(String key, int expire, String value) throws TvlCacheException {
		this.memCache.put(key, expire, value);
	}

	@Override
	public String get(String key) throws TvlCacheException {
		return this.memCache.get(key);
	}

	@Override
	public void remove(String key) throws TvlCacheException {
		this.memCache.remove(key);
	}

	@Override
	public void flushAll() throws TvlCacheException {
		this.memCache.flushAll();
	}

	@Override
	public Long expire(String key, int seconds) throws TvlCacheException {
		return this.memCache.expire(key, seconds);
	}

	@Override
	public void oput(String key, Object value) throws TvlCacheException {
		this.memCache.oput(key, value);
	}

	@Override
	public void oput(String key, int expire, Object value) throws TvlCacheException {
		this.memCache.oput(key, expire, value);
	}

	@Override
	public Object oget(String key) throws TvlCacheException {
		return this.memCache.oget(key);
	}

	@Override
	public Map<String, Object> omget(Collection<String> keys) throws TvlCacheException {
		return this.memCache.omget(keys);
	}

	@Override
	public long getConnectTimeout() throws TvlCacheException {
		return this.memCache.getConnectTimeout();
	}

	@Override
	public void setConnectTimeout(long connectTimeout) throws TvlCacheException {
		this.memCache.setConnectTimeout(connectTimeout);
	}

	@Override
	public <T> T get(String key, long timeout, Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.get(key, timeout, transcoder);
	}

	@Override
	public <T> T get(String key, long timeout) throws TvlCacheException {
		return this.memCache.get(key, timeout);
	}

	@Override
	public <T> T get(String key, Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.get(key, transcoder);
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout, Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.gets(key, timeout, transcoder);
	}

	@Override
	public <T> GetsResponse<T> gets(String key) throws TvlCacheException {
		return this.memCache.gets(key);
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout) throws TvlCacheException {
		return this.memCache.gets(key, timeout);
	}

	@Override
	public <T> GetsResponse<T> gets(String key, Transcoder transcoder) throws TvlCacheException {
		return this.memCache.gets(key, transcoder);
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long opTimeout, Transcoder<T> transcoder)
			throws TvlCacheException {
		return this.memCache.get(keyCollections, opTimeout, transcoder);
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, Transcoder<T> transcoder)
			throws TvlCacheException {
		return this.memCache.get(keyCollections, transcoder);
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections) throws TvlCacheException {
		return this.memCache.get(keyCollections);
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long timeout) throws TvlCacheException {
		return this.memCache.get(keyCollections);
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long opTime,
			Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.gets(keyCollections, opTime, transcoder);
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections) throws TvlCacheException {
		return this.memCache.gets(keyCollections);
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long timeout)
			throws TvlCacheException {
		return this.memCache.gets(keyCollections, timeout);
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, Transcoder<T> transcoder)
			throws TvlCacheException {
		return this.memCache.gets(keyCollections, transcoder);
	}

	@Override
	public <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		return this.memCache.set(key, exp, value, transcoder, timeout);
	}

	@Override
	public boolean set(String key, int exp, Object value) throws TvlCacheException {
		return this.memCache.set(key, exp, value);
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout) throws TvlCacheException {
		return this.memCache.set(key, exp, value, timeout);
	}

	@Override
	public <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.set(key, exp, value, transcoder);
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		this.memCache.setWithNoReply(key, exp, value);
	}

	@Override
	public <T> void setWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		this.memCache.setWithNoReply(key, exp, value, transcoder);
	}

	@Override
	public <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		return this.memCache.add(key, exp, value, transcoder, timeout);
	}

	@Override
	public boolean add(String key, int exp, Object value) throws TvlCacheException {
		return this.memCache.add(key, exp, value);
	}

	@Override
	public boolean add(String key, int exp, Object value, long timeout) throws TvlCacheException {
		return this.memCache.add(key, exp, value, timeout);
	}

	@Override
	public <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.add(key, exp, value, transcoder);
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		this.memCache.addWithNoReply(key, exp, value);
	}

	@Override
	public <T> void addWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		this.memCache.addWithNoReply(key, exp, value, transcoder);
	}

	@Override
	public <T> boolean replace(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		return this.memCache.replace(key, exp, value, transcoder, timeout);
	}

	@Override
	public boolean replace(String key, int exp, Object value) throws TvlCacheException {
		return this.memCache.replace(key, exp, value);
	}

	@Override
	public boolean replace(String key, int exp, Object value, long timeout) throws TvlCacheException {
		return this.memCache.replace(key, exp, value, timeout);
	}

	@Override
	public <T> boolean replace(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.replace(key, exp, value, transcoder);
	}

	@Override
	public void replaceWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		this.memCache.replaceWithNoReply(key, exp, value);
	}

	@Override
	public <T> void replaceWithNoReply(String key, int exp, T value, Transcoder<T> transcoder)
			throws TvlCacheException {
		this.memCache.replaceWithNoReply(key, exp, value, transcoder);
	}

	@Override
	public boolean append(String key, Object value) throws TvlCacheException {
		return this.memCache.append(key, value);
	}

	@Override
	public boolean append(String key, Object value, long timeout) throws TvlCacheException {
		return this.memCache.append(key, value, timeout);
	}

	@Override
	public void appendWithNoReply(String key, Object value) throws TvlCacheException {
		this.memCache.appendWithNoReply(key, value);
	}

	@Override
	public boolean prepend(String key, Object value) throws TvlCacheException {
		return this.memCache.prepend(key, value);
	}

	@Override
	public boolean prepend(String key, Object value, long timeout) throws TvlCacheException {
		return this.memCache.prepend(key, value, timeout);
	}

	@Override
	public void prependWithNoReply(String key, Object value) throws TvlCacheException {
		this.memCache.prependWithNoReply(key, value);
	}

	@Override
	public boolean cas(String key, int exp, Object value, long cas) throws TvlCacheException {
		return this.memCache.cas(key, exp, value, cas);
	}

	@Override
	public <T> boolean cas(String key, int exp, T value, Transcoder<T> transcoder, long timeout, long cas)
			throws TvlCacheException {
		return this.memCache.cas(key, exp, value, transcoder, cas);
	}

	@Override
	public boolean cas(String key, int exp, Object value, long timeout, long cas) throws TvlCacheException {
		return this.memCache.cas(key, exp, value, timeout, cas);
	}

	@Override
	public <T> boolean cas(String key, int exp, T value, Transcoder<T> transcoder, long cas) throws TvlCacheException {
		return this.memCache.cas(key, exp, value, transcoder, cas);
	}

	@Override
	public <T> boolean cas(String key, int exp, CASOperation<T> operation, Transcoder<T> transcoder)
			throws TvlCacheException {
		return this.memCache.cas(key, exp, operation, transcoder);
	}

	@Override
	public <T> boolean cas(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation,
			Transcoder<T> transcoder) throws TvlCacheException {
		return this.memCache.cas(key, exp, getsReponse, operation, transcoder);
	}

	@Override
	public <T> boolean cas(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation)
			throws TvlCacheException {
		return this.memCache.cas(key, exp, getsReponse, operation);
	}

	@Override
	public <T> boolean cas(String key, GetsResponse<T> getsResponse, CASOperation<T> operation)
			throws TvlCacheException {
		return this.memCache.cas(key, getsResponse, operation);
	}

	@Override
	public <T> boolean cas(String key, int exp, CASOperation<T> operation) throws TvlCacheException {
		return this.memCache.cas(key, exp, operation);
	}

	@Override
	public <T> boolean cas(String key, CASOperation<T> operation) throws TvlCacheException {
		return this.memCache.cas(key, operation);
	}

	@Override
	public <T> void casWithNoReply(String key, GetsResponse<T> getsResponse, CASOperation<T> operation)
			throws TvlCacheException {
		this.memCache.casWithNoReply(key, getsResponse, operation);
	}

	@Override
	public <T> void casWithNoReply(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation)
			throws TvlCacheException {
		this.memCache.casWithNoReply(key, exp, getsReponse, operation);
	}

	@Override
	public <T> void casWithNoReply(String key, int exp, CASOperation<T> operation) throws TvlCacheException {
		this.memCache.casWithNoReply(key, exp, operation);
	}

	@Override
	public <T> void casWithNoReply(String key, CASOperation<T> operation) throws TvlCacheException {
		this.memCache.casWithNoReply(key, operation);
	}

	@Override
	public boolean delete(String key, int time) throws TvlCacheException {
		return this.memCache.delete(key, time);
	}

	@Override
	public boolean delete(String key, long opTimeout) throws TvlCacheException {
		return this.memCache.delete(key, opTimeout);
	}

	@Override
	public boolean delete(String key, long cas, long opTimeout) throws TvlCacheException {
		return this.memCache.delete(key, cas, opTimeout);
	}

	@Override
	public boolean delete(String key) throws TvlCacheException {
		return this.memCache.delete(key);
	}

	@Override
	public void deleteWithNoReply(String key, int time) throws TvlCacheException {
		this.memCache.deleteWithNoReply(key, time);
	}

	@Override
	public void deleteWithNoReply(String key) throws TvlCacheException {
		this.memCache.deleteWithNoReply(key);
	}

	@Override
	public boolean touch(String key, int exp, long opTimeout) throws TvlCacheException {
		return this.memCache.touch(key, exp, opTimeout);
	}

	@Override
	public boolean touch(String key, int exp) throws TvlCacheException {
		return this.memCache.touch(key, exp);
	}

	@Override
	public <T> T getAndTouch(String key, int newExp, long opTimeout) throws TvlCacheException {
		return this.memCache.getAndTouch(key, newExp, opTimeout);
	}

	@Override
	public <T> T getAndTouch(String key, int newExp) throws TvlCacheException {
		return this.memCache.getAndTouch(key, newExp);
	}

	@Override
	public long incr(String key, long delta) throws TvlCacheException {
		return this.memCache.incr(key, delta);
	}

	@Override
	public long incr(String key, long delta, long initValue) throws TvlCacheException {
		return this.memCache.incr(key, delta, initValue);
	}

	@Override
	public long incr(String key, long delta, long initValue, long timeout) throws TvlCacheException {
		return this.memCache.incr(key, delta, initValue, timeout);
	}

	@Override
	public long incr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException {
		return this.memCache.incr(key, delta, initValue, timeout, exp);
	}

	@Override
	public void incrWithNoReply(String key, long delta) throws TvlCacheException {
		this.memCache.incrWithNoReply(key, delta);
	}

	@Override
	public long decr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException {
		return this.memCache.decr(key, delta, initValue, timeout, exp);
	}

	@Override
	public long decr(String key, long delta) throws TvlCacheException {
		return this.memCache.decr(key, delta);
	}

	@Override
	public long decr(String key, long delta, long initValue) throws TvlCacheException {
		return this.memCache.decr(key, delta, initValue);
	}

	@Override
	public long decr(String key, long delta, long initValue, long timeout) throws TvlCacheException {
		return this.memCache.decr(key, delta, initValue, timeout);
	}

	@Override
	public void decrWithNoReply(String key, long delta) throws TvlCacheException {
		this.memCache.decrWithNoReply(key, delta);
	}

	@Override
	public void flushAllWithNoReply() throws TvlCacheException {
		this.memCache.flushAllWithNoReply();
	}

	@Override
	public void flushAll(long timeout) throws TvlCacheException {
		this.memCache.flushAll(timeout);
	}

	@Override
	public void flushAll(InetSocketAddress address) throws TvlCacheException {
		this.memCache.flushAll(address);
	}

	@Override
	public void flushAllWithNoReply(InetSocketAddress address) throws TvlCacheException {
		this.memCache.flushAllWithNoReply(address);
	}

	@Override
	public void flushAll(InetSocketAddress address, long timeout) throws TvlCacheException {
		this.memCache.flushAll(address);
	}

	@Override
	public void flushAll(String host) throws TvlCacheException {
		this.memCache.flushAll(host);
	}

	@Override
	public void flushAllWithNoReply(int exptime) throws TvlCacheException {
		this.memCache.flushAllWithNoReply(exptime);
	}

	@Override
	public void flushAll(int exptime, long timeout) throws TvlCacheException {
		this.memCache.flushAll(exptime, timeout);
	}

	@Override
	public void flushAllWithNoReply(InetSocketAddress address, int exptime) throws TvlCacheException {
		this.memCache.flushAllWithNoReply(address, exptime);
	}

	@Override
	public void flushAll(InetSocketAddress address, long timeout, int exptime) throws TvlCacheException {
		this.memCache.flushAll(address, timeout, exptime);
	}

}
