package com.tvl.midl.cacheclient.provider.memcache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.ICache;
import com.tvl.midl.cacheclient.ServerAddress;
import com.tvl.midl.cacheclient.config.CacheConfig;
import com.tvl.midl.cacheclient.exception.TvlCacheException;
import com.tvl.midl.cacheclient.provider.AbstractCacheFactory;

import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

/**
 * @description
 * @author st.z
 */
public class MemCacheFactory extends AbstractCacheFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemCacheFactory.class);

	private static final String DEFAULT_MEM_CACHE_TYPE = "m1";

	private static final String MEM_CACHE_M1 = "m1";

	private static final String MEM_CACHE_SPY = "spy";

	public MemCacheFactory() {
	}

	public MemCacheFactory(String clientType) {
		super(clientType);
	}

	@Override
	public ICache createCache(List<ServerAddress> addressList, CacheConfig cacheConfig) {
		if (this.clientType == null) {
			this.clientType = DEFAULT_MEM_CACHE_TYPE;
		}
		ICache cache = null;
		if (MEM_CACHE_M1.equals(this.clientType)) {
			cache = createXMemcacheWrap(addressList, cacheConfig);
		} else if (MEM_CACHE_SPY.equals(this.clientType)) {
			// TODO:spy暂未支持
			cache = createXMemcacheWrap(addressList, cacheConfig);
		} else {
			throw new TvlCacheException("----暂未支持的memcache客户端!");
		}
		if (cache == null) {
			throw new TvlCacheException("----创建memcache-cache实例失败.");
		}
		return cache;
	}

	/**
	 * xmemcached 客户端(包装类)
	 * 
	 * @param routerCfg
	 * @return
	 */
	private ICache createXMemcacheWrap(List<ServerAddress> addressList, CacheConfig cacheConfig) {
		if (addressList == null || addressList.isEmpty() || cacheConfig == null) {
			throw new TvlCacheException("----createXMemcacheWrap失败:addressList、cacheconfig参数为null!");
		}
		try {
			if (addressList.size() == 1) {
				LOGGER.info("----单节点模式...");
				ServerAddress srvAddress = addressList.get(0);
				XMemcachedClient mec = new XMemcachedClient(srvAddress.getHost(), srvAddress.getPort());
				return new XMemcachedCacheWrap(this.initXMemcachedClient(mec, cacheConfig));
			}

			LOGGER.info("----shard模式...");
			if (cacheConfig.getClusterMode() == 1) {
				List<InetSocketAddress> list = new ArrayList<InetSocketAddress>();
				int[] weights = new int[addressList.size()];
				for (int i = 0; i < addressList.size(); i++) {
					ServerAddress srvAddress = addressList.get(i);
					InetSocketAddress address = new InetSocketAddress(srvAddress.getHost(), srvAddress.getPort());
					list.add(address);
					weights[i] = 1;
				}
				MemcachedClientBuilder builder = new XMemcachedClientBuilder(list, weights);
				// 设置最大连接数，默认为1，除非确认对性能有影响，否则不建议修改
				if (cacheConfig.getMaxConn() != null && !cacheConfig.getMaxConn().isEmpty()) {
					builder.setConnectionPoolSize(Integer.parseInt(cacheConfig.getMaxConn()));
				}
				XMemcachedClient mec = (XMemcachedClient) builder.build();
				return new XMemcachedCacheWrap(this.initXMemcachedClient(mec, cacheConfig));
			} else if (cacheConfig.getClusterMode() == 2) {
				LOGGER.info("----开启异步复制的集群模式...");
				List<IMemcachedCache> mecList = new ArrayList<IMemcachedCache>();
				for (int i = 0; i < addressList.size(); i++) {
					ServerAddress srvAddress = addressList.get(i);
					XMemcachedClient mecClient = new XMemcachedClient(srvAddress.getHost(), srvAddress.getPort());
					XMemcachedCacheWrap mec = new XMemcachedCacheWrap(
							this.initXMemcachedClient(mecClient, cacheConfig));
					mecList.add(mec);
				}
				return new XMemcachedClusterCache(mecList);
			} else {
				throw new TvlCacheException("----不支持该集群模式.");
			}
		} catch (IOException e) {
			throw new TvlCacheException("创建xMemcachedClient错误", e);
		}
	}

	/**
	 * 初始化MemcacheClient连接池
	 * 
	 * @param xMemcachedClient
	 * @param cachePool
	 * @return
	 */
	private XMemcachedClient initXMemcachedClient(XMemcachedClient xMemcachedClient, CacheConfig cacheConfig) {
		if (cacheConfig == null) {
			LOGGER.info("----cacheConfig为null!");
			return xMemcachedClient;
		}
		// 设置最大超时时间
		if (cacheConfig.getMaxWait() != null && !cacheConfig.getMaxWait().isEmpty()) {
			xMemcachedClient.setOpTimeout(Long.parseLong(cacheConfig.getMaxWait()));
		}
		return xMemcachedClient;

	}
}
