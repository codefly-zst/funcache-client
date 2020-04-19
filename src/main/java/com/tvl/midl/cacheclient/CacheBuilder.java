package com.tvl.midl.cacheclient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.config.CacheConfig;
import com.tvl.midl.cacheclient.exception.TvlCacheConfigException;
import com.tvl.midl.cacheclient.exception.TvlCacheException;
import com.tvl.midl.cacheclient.provider.memcache.MemCacheFactory;
import com.tvl.midl.cacheclient.route.CacheRouterFactory;
import com.tvl.midl.cacheclient.route.ICacheRouter;

/**
 * @description cache构造器
 * @author shite.zhu
 * @date 2016年10月10日
 */
public class CacheBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheBuilder.class);

	/**
	 * 默认cache类型
	 */
	private static final String CACHE_MEM = "memcached";

	private static final String CACHE_REDIS = "redis";

	/**
	 * cache类型:memcached或redis
	 */
	protected String cacheType;

	protected CacheConfig cacheConfig;

	public CacheBuilder(String cacheType, CacheConfig cacheConfig) {
		this.cacheType = cacheType;
		this.cacheConfig = cacheConfig;
	}

	/**
	 * 创建ICache实例
	 * 
	 * @param cacheConfig
	 * @return
	 */
	public ICache createCache() {
		// 验证连接池配置有效性
		this.validCacheConfig();
		// 创建cache实例
		return this.doCreateCache();
	}

	/**
	 * 校验缓存配置
	 * 
	 * @param cacheConfig
	 * @param cachePool
	 */
	private void validCacheConfig() {
		if(this.cacheType == null || this.cacheType.isEmpty()) {
			throw new TvlCacheConfigException("----cacheType不能为空!");
		}
		if (this.cacheConfig == null) {
			throw new TvlCacheConfigException("----cacheConfig配置不能为空!");
		}
		String router = this.cacheConfig.getRouterType();
		if ("static".equals(router)) {
			String servers = this.cacheConfig.getServers();
			if (servers == null || "".equals(servers)) {
				throw new TvlCacheConfigException("----cacheConfig.servers不能为空!");
			}
		} else {
			//TODO:动态获取ServerAddress配置
		}
	}

	/**
	 * 创建cache实例
	 */
	private ICache doCreateCache() {
		// 0、寻址
		ICacheRouter cacheRouter = CacheRouterFactory.createCacheRouter(this.cacheConfig.getRouterType());
		List<ServerAddress> srvAddressList = cacheRouter.getAddressList(this.cacheConfig);
		if (srvAddressList == null || srvAddressList.isEmpty()) {
			throw new TvlCacheException("----缓存服务器地址列表为空，请检查配置！");
		}
		LOGGER.info("----缓存服务器地址信息:");
		for (ServerAddress srvAddress : srvAddressList) {
			LOGGER.info("host:{},port:{}", srvAddress.getHost(), srvAddress.getPort());
		}

		// 1、创建实例
		ICacheFactory cacheFactory = null;
		if (this.cacheType.equals(CACHE_MEM)) {
			cacheFactory = new MemCacheFactory(this.cacheConfig.getClientType());
		} else if (this.cacheType.equals(CACHE_REDIS)) {

		} else {
			throw new TvlCacheConfigException("----不支持该类型cache.");
		}
		if (cacheFactory == null) {
			LOGGER.error("----cacheFactory创建失败.");
			throw new TvlCacheException("----cacheFactory创建失败.");
		}
		// 1-2、创建cache
		ICache cache = cacheFactory.createCache(srvAddressList, this.cacheConfig);
		if (cache == null) {
			LOGGER.error("----cache创建失败.");
			throw new TvlCacheException("----cache创建失败.");
		}
		return cache;
	}

	public String getCacheType() {
		return cacheType;
	}
 
}
