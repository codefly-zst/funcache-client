package com.tvl.midl.cacheclient;

import com.tvl.midl.cacheclient.exception.TvlCacheException;

/**
 * @description
 * @author shite.zhu
 * @date 2016年10月10日 上午10:04:32
 * 
 */
public interface ICache {

	/**
	 * 从缓存中获取数据
	 * 
	 * @param key
	 * @return
	 * @throws TvlCacheException
	 */
	public String get(String key) throws TvlCacheException;

	/**
	 * 添加K/V对
	 * 
	 * @param key
	 * @param value
	 * @throws TvlCacheException
	 */
	public void put(String key, String value) throws TvlCacheException;

	/**
	 * 添加K/V对，并指定过期时间
	 * 
	 * @param key
	 * @param expire second
	 * @param value
	 * @throws TvlCacheException
	 */
	public void put(String key, int expire, String value) throws TvlCacheException;

	/**
	 * 从缓存中移除指定Key
	 */
	public void remove(String key) throws TvlCacheException;

	/**
	 * 设置key过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(String key, int seconds) throws TvlCacheException;

	/**
	 * 刷新缓存
	 * 
	 * @throws TvlCacheException
	 */
	public void flushAll() throws TvlCacheException;

}
