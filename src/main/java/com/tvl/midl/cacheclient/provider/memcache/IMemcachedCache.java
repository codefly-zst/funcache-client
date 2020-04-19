package com.tvl.midl.cacheclient.provider.memcache;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.tvl.midl.cacheclient.ICache;
import com.tvl.midl.cacheclient.exception.TvlCacheException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.Transcoder;

/**
 * @description
 * @author st.z
 */
public interface IMemcachedCache extends ICache {

	/**
	 * Add an object to the cache
	 * 
	 * @param key
	 * @param value
	 * @throws TvlCacheException
	 */
	public void oput(String key, Object value) throws TvlCacheException;

	/**
	 * Add an object to the cache
	 * 
	 * @param key
	 * @param expire second
	 * @param value
	 * @throws TvlCacheException
	 */
	public void oput(String key, int expire, Object value) throws TvlCacheException;

	/**
	 * oget
	 * 
	 * @param key
	 * @return
	 * @throws TvlCacheException
	 */
	public Object oget(String key) throws TvlCacheException;

	/**
	 * Get more objects from the cache
	 * 
	 * @param keys
	 * @return the cached map
	 * @throws TvlCacheException
	 */
	public Map<String, Object> omget(Collection<String> keys) throws TvlCacheException;

	/**
	 * Get the connect timeout
	 * 
	 */
	public long getConnectTimeout() throws TvlCacheException;

	/**
	 * Set the connect timeout,default is 1 minutes
	 * 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(long connectTimeout) throws TvlCacheException;

	/**
	 * Get value by key
	 * 
	 * @param <T>
	 * @param key        Key
	 * @param timeout    Operation timeout,if the method is not returned in this
	 *                   time,throw TimeoutException
	 * @param transcoder The value's transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> T get(final String key, final long timeout, final Transcoder<T> transcoder) throws TvlCacheException;

	public <T> T get(final String key, final long timeout) throws TvlCacheException;

	public <T> T get(final String key, final Transcoder<T> transcoder) throws TvlCacheException;

	// -----------------------------v2------------------------------

	/**
	 * Just like get,But it return a GetsResponse,include cas value for cas update.
	 * 
	 * @param <T>
	 * @param key        key
	 * @param timeout    operation timeout
	 * @param transcoder
	 * 
	 * @return GetsResponse
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(final String key, final long timeout, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(final String key) throws TvlCacheException;

	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> GetsResponse<T> gets(final String key, final long timeout) throws TvlCacheException;

	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@SuppressWarnings("rawtypes")
	public <T> GetsResponse<T> gets(final String key, final Transcoder transcoder) throws TvlCacheException;

	/**
	 * Bulk get items
	 * 
	 * @param <T>
	 * @param keyCollections key collection
	 * @param opTimeout      opTimeout
	 * @param transcoder     Value transcoder
	 * @return Exists items map
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, T> get(final Collection<String> keyCollections, final long opTimeout,
			final Transcoder<T> transcoder) throws TvlCacheException;

	/**
	 * @see #get(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, T> get(final Collection<String> keyCollections, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * @see #get(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, T> get(final Collection<String> keyCollections) throws TvlCacheException;

	/**
	 * @see #get(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, T> get(final Collection<String> keyCollections, final long timeout) throws TvlCacheException;

	/**
	 * Bulk gets items
	 * 
	 * @param <T>
	 * @param keyCollections key collection
	 * @param opTime         Operation timeout
	 * @param transcoder     Value transcoder
	 * @return Exists GetsResponse map
	 * @see net.rubyeye.xmemcached.GetsResponse
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections, final long opTime,
			final Transcoder<T> transcoder) throws TvlCacheException;

	/**
	 * @see #gets(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections) throws TvlCacheException;

	/**
	 * @see #gets(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections, final long timeout)
			throws TvlCacheException;

	/**
	 * @see #gets(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections,
			final Transcoder<T> transcoder) throws TvlCacheException;

	/**
	 * Store key-value item to memcached
	 * 
	 * @param <T>
	 * @param key        stored key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value      stored data
	 * @param transcoder transocder
	 * @param timeout    operation timeout,in milliseconds
	 * @return boolean result
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean set(final String key, final int exp, final T value, final Transcoder<T> transcoder,
			final long timeout) throws TvlCacheException;

	/**
	 * @see #set(String, int, Object, Transcoder, long)
	 */
	public boolean set(final String key, final int exp, final Object value) throws TvlCacheException;

	/**
	 * @see #set(String, int, Object, Transcoder, long)
	 */
	public boolean set(final String key, final int exp, final Object value, final long timeout)
			throws TvlCacheException;

	/**
	 * @see #set(String, int, Object, Transcoder, long)
	 */
	public <T> boolean set(final String key, final int exp, final T value, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * Store key-value item to memcached,doesn't wait for reply
	 * 
	 * @param <T>
	 * @param key        stored key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value      stored data
	 * @param transcoder transocder
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void setWithNoReply(final String key, final int exp, final Object value) throws TvlCacheException;

	/**
	 * @see #setWithNoReply(String, int, Object, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void setWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * Add key-value item to memcached, success only when the key is not exists in
	 * memcached.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @return boolean result
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean add(final String key, final int exp, final T value, final Transcoder<T> transcoder,
			final long timeout) throws TvlCacheException;

	/**
	 * @see #add(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean add(final String key, final int exp, final Object value) throws TvlCacheException;

	/**
	 * @see #add(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean add(final String key, final int exp, final Object value, final long timeout)
			throws TvlCacheException;

	/**
	 * @see #add(String, int, Object, Transcoder, long)
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean add(final String key, final int exp, final T value, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * Add key-value item to memcached, success only when the key is not exists in
	 * memcached.This method doesn't wait for reply.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */

	public void addWithNoReply(final String key, final int exp, final Object value) throws TvlCacheException;

	/**
	 * @see #addWithNoReply(String, int, Object, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void addWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * Replace the key's data item in memcached,success only when the key's data
	 * item is exists in memcached.This method will wait for reply from server.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @return boolean result
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean replace(final String key, final int exp, final T value, final Transcoder<T> transcoder,
			final long timeout) throws TvlCacheException;

	/**
	 * @see #replace(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean replace(final String key, final int exp, final Object value) throws TvlCacheException;

	/**
	 * @see #replace(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean replace(final String key, final int exp, final Object value, final long timeout)
			throws TvlCacheException;

	/**
	 * @see #replace(String, int, Object, Transcoder, long)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean replace(final String key, final int exp, final T value, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * Replace the key's data item in memcached,success only when the key's data
	 * item is exists in memcached.This method doesn't wait for reply from server.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void replaceWithNoReply(final String key, final int exp, final Object value) throws TvlCacheException;

	/**
	 * @see #replaceWithNoReply(String, int, Object, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void replaceWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder)
			throws TvlCacheException;

	/**
	 * @see #append(String, Object, long)
	 * @param key
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean append(final String key, final Object value) throws TvlCacheException;

	/**
	 * Append value to key's data item,this method will wait for reply
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return boolean result
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean append(final String key, final Object value, final long timeout) throws TvlCacheException;

	/**
	 * Append value to key's data item,this method doesn't wait for reply.
	 * 
	 * @param key
	 * @param value
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void appendWithNoReply(final String key, final Object value) throws TvlCacheException;

	/**
	 * @see #prepend(String, Object, long)
	 * @param key
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean prepend(final String key, final Object value) throws TvlCacheException;

	/**
	 * Prepend value to key's data item in memcached.This method doesn't wait for
	 * reply.
	 * 
	 * @param key
	 * @param value
	 * @return boolean result
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean prepend(final String key, final Object value, final long timeout) throws TvlCacheException;

	/**
	 * Prepend value to key's data item in memcached.This method doesn't wait for
	 * reply.
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void prependWithNoReply(final String key, final Object value) throws TvlCacheException;

	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param cas
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean cas(final String key, final int exp, final Object value, final long cas) throws TvlCacheException;

	/**
	 * Cas is a check and set operation which means "store this data but only if no
	 * one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @param cas
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, final T value, final Transcoder<T> transcoder,
			final long timeout, final long cas) throws TvlCacheException;

	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @param cas
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean cas(final String key, final int exp, final Object value, final long timeout, final long cas)
			throws TvlCacheException;

	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @param cas
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, final T value, final Transcoder<T> transcoder,
			final long cas) throws TvlCacheException;

	/**
	 * Cas is a check and set operation which means "store this data but only if no
	 * one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp        An expiration time, in seconds. Can be up to 30 days. After
	 *                   30 days, is treated as a unix timestamp of an exact date.
	 * @param operation  CASOperation
	 * @param transcoder object transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, final CASOperation<T> operation,
			final Transcoder<T> transcoder) throws TvlCacheException;

	/**
	 * cas is a check and set operation which means "store this data but only if no
	 * one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp         An expiration time, in seconds. Can be up to 30 days.
	 *                    After 30 days, is treated as a unix timestamp of an exact
	 *                    date.
	 * @param getsReponse gets method's result
	 * @param operation   CASOperation
	 * @param transcoder
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, GetsResponse<T> getsReponse,
			final CASOperation<T> operation, final Transcoder<T> transcoder) throws TvlCacheException;

	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param getsReponse
	 * @param operation
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, GetsResponse<T> getsReponse,
			final CASOperation<T> operation) throws TvlCacheException;

	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param getsResponse
	 * @param operation
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, GetsResponse<T> getsResponse, final CASOperation<T> operation)
			throws TvlCacheException;

	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param operation
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final int exp, final CASOperation<T> operation) throws TvlCacheException;

	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param operation
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> boolean cas(final String key, final CASOperation<T> operation) throws TvlCacheException;

	/**
	 * 
	 * @param <T>
	 * @param key
	 * @param getsResponse
	 * @param operation
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void casWithNoReply(final String key, GetsResponse<T> getsResponse, final CASOperation<T> operation)
			throws TvlCacheException;

	/**
	 * cas noreply
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param getsReponse
	 * @param operation
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void casWithNoReply(final String key, final int exp, GetsResponse<T> getsReponse,
			final CASOperation<T> operation) throws TvlCacheException;

	/**
	 * @see #casWithNoReply(String, int, GetsResponse, CASOperation)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param operation
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void casWithNoReply(final String key, final int exp, final CASOperation<T> operation)
			throws TvlCacheException;

	/**
	 * @see #casWithNoReply(String, int, GetsResponse, CASOperation)
	 * @param <T>
	 * @param key
	 * @param operation
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> void casWithNoReply(final String key, final CASOperation<T> operation) throws TvlCacheException;

	/**
	 * Delete key's data item from memcached.It it is not exists,return false.</br>
	 * time is the amount of time in seconds (or Unix time until</br>
	 * which) the client wishes the server to refuse "add" and "replace"</br>
	 * commands with this key. For this amount of item, the item is put into a</br>
	 * delete queue, which means that it won't possible to retrieve it by the</br>
	 * "get" command, but "add" and "replace" command with this key will also</br>
	 * fail (the "set" command will succeed, however). After the time passes,</br>
	 * the item is finally deleted from server memory. </br>
	 * <strong>Note: This method is deprecated,because memcached 1.4.0 remove the
	 * optional argument "time".You can still use this method on old version,but is
	 * not recommended.</strong>
	 * 
	 * @param key
	 * @param time
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@Deprecated
	public boolean delete(final String key, final int time) throws TvlCacheException;

	/**
	 * Delete key's date item from memcached
	 * 
	 * @param key
	 * @param opTimeout Operation timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @since 1.3.2
	 */
	public boolean delete(final String key, long opTimeout) throws TvlCacheException;

	/**
	 * Delete key's date item from memcached only if its cas value is the same as
	 * what was read.
	 * 
	 * @param key
	 * @cas cas on delete to make sure the key is deleted only if its value is same
	 *      as what was read.
	 * @param opTimeout Operation timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 * @since 1.3.2
	 */
	public boolean delete(final String key, long cas, long opTimeout) throws TvlCacheException;

	public boolean delete(final String key) throws TvlCacheException;

	/**
	 * Delete key's data item from memcached.This method doesn't wait for reply.
	 * This method does not work on memcached 1.3 or later version.See <a href=
	 * 'http://code.google.com/p/memcached/issues/detail?id=3&q=delete%20noreply ' >
	 * i s s u e 3</a> </br>
	 * <strong>Note: This method is deprecated,because memcached 1.4.0 remove the
	 * optional argument "time".You can still use this method on old version,but is
	 * not recommended.</strong>
	 * 
	 * @param key
	 * @param time
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@Deprecated
	public void deleteWithNoReply(final String key, final int time) throws TvlCacheException;

	public void deleteWithNoReply(final String key) throws TvlCacheException;

	/**
	 * Set a new expiration time for an existing item
	 * 
	 * @param key       item's key
	 * @param exp       New expiration time, in seconds. Can be up to 30 days. After
	 *                  30 days, is treated as a unix timestamp of an exact date.
	 * @param opTimeout operation timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean touch(final String key, int exp, long opTimeout) throws TvlCacheException;

	/**
	 * Set a new expiration time for an existing item,using default opTimeout
	 * second.
	 * 
	 * @param key item's key
	 * @param exp New expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean touch(final String key, int exp) throws TvlCacheException;

	/**
	 * Get item and set a new expiration time for it
	 * 
	 * @param <T>
	 * @param key       item's key
	 * @param newExp    New expiration time, in seconds. Can be up to 30 days. After
	 *                  30 days, is treated as a unix timestamp of an exact date.
	 * @param opTimeout operation timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> T getAndTouch(final String key, int newExp, long opTimeout) throws TvlCacheException;

	/**
	 * Get item and set a new expiration time for it,using default opTimeout
	 * 
	 * @param <T>
	 * @param key
	 * @param newExp
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public <T> T getAndTouch(final String key, int newExp) throws TvlCacheException;

	/**
	 * "incr" are used to change data for some item in-place, incrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for incr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @return the new value of the item's data, after the increment operation was
	 *         carried out.
	 * @param key
	 * @param num
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long incr(final String key, final long delta) throws TvlCacheException;

	public long incr(final String key, final long delta, final long initValue) throws TvlCacheException;

	/**
	 * "incr" are used to change data for some item in-place, incrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for incr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @param key       key
	 * @param num       increment
	 * @param initValue initValue if the data is not exists.
	 * @param timeout   operation timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long incr(final String key, final long delta, final long initValue, long timeout) throws TvlCacheException;

	/**
	 * "incr" are used to change data for some item in-place, incrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for incr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @param key       key
	 * @param delta     increment delta
	 * @param initValue the initial value to be added when value is not found
	 * @param timeout   operation timeout
	 * @param exp       the initial vlaue expire time, in seconds. Can be up to 30
	 *                  days. After 30 days, is treated as a unix timestamp of an
	 *                  exact date.
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	long incr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException;

	/**
	 * "incr" are used to change data for some item in-place, incrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for incr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @param key
	 * @param num
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void incrWithNoReply(final String key, final long delta) throws TvlCacheException;

	/**
	 * "incr" are used to change data for some item in-place, incrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for incr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @param key
	 * @param delta
	 * @param initValue the initial value to be added when value is not found
	 * @param timeout
	 * @param exp       the initial vlaue expire time, in seconds. Can be up to 30
	 *                  days. After 30 days, is treated as a unix timestamp of an
	 *                  exact date.
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	long decr(String key, long delta, long initValue, long timeout, int exp) throws TvlCacheException;

	/**
	 * "decr" are used to change data for some item in-place, decrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for decr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @return the new value of the item's data, after the decrement operation was
	 *         carried out.
	 * @param key
	 * @param num
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long decr(final String key, final long delta) throws TvlCacheException;

	/**
	 * @see decr
	 * @param key
	 * @param num
	 * @param initValue
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long decr(final String key, final long delta, long initValue) throws TvlCacheException;

	/**
	 * "decr" are used to change data for some item in-place, decrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for decr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @param key       The key
	 * @param num       The increment
	 * @param initValue The initial value if the data is not exists.
	 * @param timeout   Operation timeout
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long decr(final String key, final long delta, long initValue, long timeout) throws TvlCacheException;

	/**
	 * "decr" are used to change data for some item in-place, decrementing it. The
	 * data for the item is treated as decimal representation of a 64-bit unsigned
	 * integer. If the current data value does not conform to such a representation,
	 * the commands behave as if the value were 0. Also, the item must already exist
	 * for decr to work; these commands won't pretend that a non-existent key exists
	 * with value 0; instead, it will fail.This method doesn't wait for reply.
	 * 
	 * @param key
	 * @param num
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void decrWithNoReply(final String key, final long delta) throws TvlCacheException;

	public void flushAllWithNoReply() throws TvlCacheException;

	/**
	 * Make All connected memcached's data item invalid
	 * 
	 * @param timeout operation timeout
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void flushAll(long timeout) throws TvlCacheException;

	/**
	 * Invalidate all existing items immediately
	 * 
	 * @param address Target memcached server
	 * @param timeout operation timeout
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void flushAll(InetSocketAddress address) throws TvlCacheException;

	public void flushAllWithNoReply(InetSocketAddress address) throws TvlCacheException;

	public void flushAll(InetSocketAddress address, long timeout) throws TvlCacheException;

	/**
	 * This method is deprecated,please use flushAll(InetSocketAddress) instead.
	 * 
	 * @deprecated
	 * @param host
	 * 
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@Deprecated
	public void flushAll(String host) throws TvlCacheException;

	public void flushAllWithNoReply(int exptime) throws TvlCacheException;

	public void flushAll(int exptime, long timeout) throws TvlCacheException;

	public void flushAllWithNoReply(InetSocketAddress address, int exptime) throws TvlCacheException;

	public void flushAll(InetSocketAddress address, long timeout, int exptime) throws TvlCacheException;

}
