package com.tvl.midl.cacheclient.provider.memcache;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.tvl.midl.cacheclient.exception.TvlCacheException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.Transcoder;

/**
 * @description
 * @author st.z
 */
public class XMemcachedCacheWrap implements IMemcachedCache {

	private XMemcachedClient mec;

	public XMemcachedCacheWrap() {
	}

	public XMemcachedCacheWrap(XMemcachedClient mec) {
		this.mec = mec;
	}

	@Override
	public void put(String key, String value) {
		try {
			mec.set(key, 0, value);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void put(String key, int expire, String value) throws TvlCacheException {
		try {
			mec.set(key, expire, value);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public String get(String key) throws TvlCacheException {
		try {
			return mec.get(key);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void oput(String key, Object value) throws TvlCacheException {
		try {
			mec.set(key, 0, value);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void oput(String key, int expire, Object value) throws TvlCacheException {
		try {
			mec.set(key, expire, value);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public Object oget(String key) throws TvlCacheException {
		try {
			return mec.get(key);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public Map<String, Object> omget(Collection<String> keys) throws TvlCacheException {
		try {
			return mec.get(keys);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void remove(String key) throws TvlCacheException {
		try {
			mec.delete(key);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生超时异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public Long expire(String key, int seconds) {
		throwExceptionIfNotAccess();
		return null;
	}

	@Override
	public void flushAll() throws TvlCacheException {
		try {
			mec.flushAll();
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}

	}

	@Override
	public long getConnectTimeout() throws TvlCacheException {
		return mec.getConnectTimeout();
	}

	@Override
	public void setConnectTimeout(long connectTimeout) throws TvlCacheException {
		mec.setConnectTimeout(connectTimeout);
	}

	@Override
	public <T> T get(String key, long timeout, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			return mec.get(key, timeout, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> T get(String key, long timeout) throws TvlCacheException {
		try {
			return mec.get(key, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> T get(String key, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			return mec.get(key, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			return mec.gets(key, timeout, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> GetsResponse<T> gets(String key) throws TvlCacheException {
		try {
			return mec.gets(key);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> GetsResponse<T> gets(String key, long timeout) throws TvlCacheException {
		try {
			return mec.gets(key, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> GetsResponse<T> gets(String key, Transcoder transcoder) throws TvlCacheException {
		try {
			return mec.gets(key, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long opTimeout, Transcoder<T> transcoder)
			throws TvlCacheException {
		try {
			return mec.get(keyCollections, opTimeout, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, Transcoder<T> transcoder)
			throws TvlCacheException {
		try {
			return mec.get(keyCollections, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections) throws TvlCacheException {
		try {
			return mec.get(keyCollections);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, T> get(Collection<String> keyCollections, long timeout) throws TvlCacheException {
		try {
			return mec.get(keyCollections, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long opTime,
			Transcoder<T> transcoder) throws TvlCacheException {
		try {
			return mec.gets(keyCollections, opTime, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections) throws TvlCacheException {
		try {
			return mec.gets(keyCollections);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long timeout)
			throws TvlCacheException {
		try {
			return mec.gets(keyCollections, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, Transcoder<T> transcoder)
			throws TvlCacheException {
		try {
			return mec.gets(keyCollections, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		try {
			return mec.set(key, exp, value, transcoder, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean set(String key, int exp, Object value) throws TvlCacheException {
		try {
			return mec.set(key, exp, value);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean set(String key, int exp, Object value, long timeout) throws TvlCacheException {
		try {
			return mec.set(key, exp, value, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			return mec.set(key, exp, value, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void setWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		try {
			mec.setWithNoReply(key, exp, value);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> void setWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			mec.setWithNoReply(key, exp, value, transcoder);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder, long timeout)
			throws TvlCacheException {
		try {
			return mec.add(key, exp, value, transcoder, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean add(String key, int exp, Object value) throws TvlCacheException {
		try {
			return mec.add(key, exp, value);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean add(String key, int exp, Object value, long timeout) throws TvlCacheException {
		try {
			return mec.add(key, exp, value, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			return mec.add(key, exp, value, transcoder);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void addWithNoReply(String key, int exp, Object value) throws TvlCacheException {
		try {
			mec.addWithNoReply(key, exp, value);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> void addWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws TvlCacheException {
		try {
			mec.addWithNoReply(key, exp, value, transcoder);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
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
		try {
			return mec.delete(key, time);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean delete(String key, long opTimeout) throws TvlCacheException {
		try {
			return mec.delete(key, opTimeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean delete(String key, long cas, long opTimeout) throws TvlCacheException {
		try {
			return mec.delete(key, cas, opTimeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean delete(String key) throws TvlCacheException {
		try {
			return mec.delete(key);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void deleteWithNoReply(String key, int time) throws TvlCacheException {
		try {
			mec.deleteWithNoReply(key, time);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void deleteWithNoReply(String key) throws TvlCacheException {
		try {
			mec.deleteWithNoReply(key);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean touch(String key, int exp, long opTimeout) throws TvlCacheException {
		try {
			return mec.touch(key, exp, opTimeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public boolean touch(String key, int exp) throws TvlCacheException {
		try {
			return mec.touch(key, exp);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> T getAndTouch(String key, int newExp, long opTimeout) throws TvlCacheException {
		try {
			return mec.getAndTouch(key, newExp, opTimeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public <T> T getAndTouch(String key, int newExp) throws TvlCacheException {
		try {
			return mec.getAndTouch(key, newExp);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long incr(String key, long delta) throws TvlCacheException {
		try {
			return mec.incr(key, delta);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long incr(String key, long delta, long initValue) throws TvlCacheException {
		try {
			return mec.incr(key, delta, initValue);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long incr(String key, long delta, long initValue, long timeout) throws TvlCacheException {
		try {
			return mec.incr(key, delta, initValue, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long incr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException {
		try {
			return mec.incr(key, delta, initValue, timeout, exp);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void incrWithNoReply(String key, long delta) throws TvlCacheException {
		try {
			mec.incrWithNoReply(key, delta);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long decr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException {
		try {
			return mec.decr(key, delta, initValue, timeout, exp);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long decr(String key, long delta) throws TvlCacheException {
		try {
			return mec.decr(key, delta);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long decr(String key, long delta, long initValue) throws TvlCacheException {
		try {
			return mec.decr(key, delta, initValue);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public long decr(String key, long delta, long initValue, long timeout) throws TvlCacheException {
		try {
			return mec.decr(key, delta, initValue, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void decrWithNoReply(String key, long delta) throws TvlCacheException {
		try {
			mec.decrWithNoReply(key, delta);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAllWithNoReply() throws TvlCacheException {
		try {
			mec.flushAllWithNoReply();
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAll(long timeout) throws TvlCacheException {
		try {
			mec.flushAll(timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAll(InetSocketAddress address) throws TvlCacheException {
		try {
			mec.flushAll(address);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		}

	}

	@Override
	public void flushAllWithNoReply(InetSocketAddress address) throws TvlCacheException {
	}

	@Override
	public void flushAll(InetSocketAddress address, long timeout) throws TvlCacheException {
		try {
			mec.flushAll(address, timeout);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAll(String host) throws TvlCacheException {
		try {
			mec.flushAll(host);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAllWithNoReply(int exptime) throws TvlCacheException {
		try {
			mec.flushAllWithNoReply(exptime);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAll(int exptime, long timeout) throws TvlCacheException {
		try {
			mec.flushAll(exptime, timeout);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		}

	}

	@Override
	public void flushAllWithNoReply(InetSocketAddress address, int exptime) throws TvlCacheException {
		try {
			mec.flushAllWithNoReply(address, exptime);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	@Override
	public void flushAll(InetSocketAddress address, long timeout, int exptime) throws TvlCacheException {
		try {
			mec.flushAll(address, timeout, exptime);
		} catch (MemcachedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (InterruptedException e) {
			throw new TvlCacheException("发生异常", e);
		} catch (TimeoutException e) {
			throw new TvlCacheException("发生异常", e);
		}
	}

	/**
	 * 若不允许使用对应方法，则抛出异常
	 */
	void throwExceptionIfNotAccess() {
		throw new TvlCacheException("Memcached不支持该操作");
	}

}
