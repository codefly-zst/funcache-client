package com.tvl.midl.cacheclient.provider.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.ICache;
import com.tvl.midl.cacheclient.ICacheFactory;
import com.tvl.midl.cacheclient.ServerAddress;
import com.tvl.midl.cacheclient.config.CacheConfig;
import com.tvl.midl.cacheclient.exception.TvlCacheException;
import com.tvl.midl.cacheclient.provider.AbstractCacheFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisCacheFactory extends AbstractCacheFactory implements ICacheFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheFactory.class);

	private static final String DEFAULT_REDIS_CACHE_TYPE = "r1";

	private static final String REDIS_CACHE_JEDIS = "r1";

	private static final String REDIS_CACHE_OTHER = "r2";
	
	public static int STAND_ALONE_POOL_MODEL = 1;

	public RedisCacheFactory() {
	}

	public RedisCacheFactory(String clientType) {
		super(clientType);
	}

	@Override
	public ICache createCache(List<ServerAddress> addressList, CacheConfig cacheConfig) {
		if (this.clientType == null) {
			this.clientType = DEFAULT_REDIS_CACHE_TYPE;
		}
		ICache cache = null;
		if (REDIS_CACHE_JEDIS.equals(this.clientType)) {
			cache = createJedisCache(addressList, cacheConfig);
		} else if (REDIS_CACHE_OTHER.equals(this.clientType)) {
			// TODO:暂未支持
			cache = createJedisCache(addressList, cacheConfig);
		} else {
			throw new TvlCacheException("----暂未支持的redis客户端!");
		}
		if (cache == null) {
			throw new TvlCacheException("----创建redis-cache实例失败.");
		}
		return cache;
	}
	
	private ICache createJedisCache(List<ServerAddress> addressList, CacheConfig cacheConfig) {
		if (addressList == null || addressList.isEmpty() || cacheConfig == null) {
			throw new TvlCacheException("----createXMemcacheWrap失败:addressList、cacheconfig参数为null!");
		}
		if (addressList.size() == 1) {
			LOGGER.info("----单节点模式...");
			int model = cacheConfig.getClusterMode();
			ServerAddress srvAddress = addressList.get(0);
			if (model == 1) {
				Jedis jedis = new Jedis(srvAddress.getHost(), srvAddress.getPort());
				return new JedisCache(jedis);
			}
			if (model == 2) {
				JedisPool jedisPool = null;
				if (cacheConfig.getMaxWait() == null || cacheConfig.getMaxWait().isEmpty()) {
					jedisPool = new JedisPool(initJedisPoolConfig(cacheConfig), srvAddress.getHost(),srvAddress.getPort());
				} else {
					jedisPool = new JedisPool(initJedisPoolConfig(cacheConfig), srvAddress.getHost(),srvAddress.getPort(), Integer.parseInt(cacheConfig.getMaxWait()));
				}
				if (cacheConfig.getStandAloneModel() == 1) {
					LOGGER.info("----通过连接池方式获取jedis实例");
					return new JedisStandAloneCache(jedisPool);
				}
				LOGGER.info("----采用默认方式获取jedis实例");
				return new JedisCache(jedisPool.getResource());
			}
			throw new TvlCacheException("----不支持的Jedis实例创建模式");
		}
		
		LOGGER.info("----shard模式");
		Set<HostAndPort> nodes = new HashSet<>();
		for (int i = 0; i < addressList.size(); i++) {
			ServerAddress srvAddress = addressList.get(i);
			HostAndPort node = new HostAndPort(srvAddress.getHost(), srvAddress.getPort());
			nodes.add(node);
		}
		JedisCluster jedisCluster = null;
		if (cacheConfig.getMaxWait() == null || cacheConfig.getMaxWait().isEmpty()) {
			jedisCluster = new JedisCluster(nodes, initJedisPoolConfig(cacheConfig));
		} else {
			jedisCluster = new JedisCluster(nodes, Integer.parseInt(cacheConfig.getMaxWait()),initJedisPoolConfig(cacheConfig));
		}
		return  new JedisClusterCache(jedisCluster);
	}

	public static void main(String[] args) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(10000);
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxWaitMillis(100000);
		Set<HostAndPort> nodes = new HashSet<>();
		HostAndPort node = new HostAndPort("192.168.192.128", 6700);
		nodes.add(node);
		JedisCluster jedisCluster = new JedisCluster(nodes,jedisPoolConfig);
//		jedisCluster.hmget(key, fields)
	}
	
	private JedisPoolConfig initJedisPoolConfig(CacheConfig cacheConfig) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		if (cacheConfig == null) {
			LOGGER.info("----缓存配置不能为空！");
			return jedisPoolConfig;
		}

		if (cacheConfig.getMaxConn() != null && !cacheConfig.getMaxConn().isEmpty()) {
			jedisPoolConfig.setMaxTotal(Integer.parseInt(cacheConfig.getMaxConn()));
		}

		if (cacheConfig.getMinConn() != null && !cacheConfig.getMinConn().isEmpty()) {
			jedisPoolConfig.setMaxIdle(Integer.parseInt(cacheConfig.getMinConn()));
		}

		if (cacheConfig.getMaxWait() != null && !cacheConfig.getMaxWait().isEmpty()) {
			jedisPoolConfig.setMaxWaitMillis(Long.parseLong(cacheConfig.getMaxWait()));
		}

		return jedisPoolConfig;
	}

}
