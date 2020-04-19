package com.tvl.midl.cacheclient;

import java.util.List;

import com.tvl.midl.cacheclient.config.CacheConfig;

/**
 * @description
 * @author st.z
 */
public interface ICacheFactory {

	/**
	 * 创建cache
	 * 
	 * @param addressList
	 * @return
	 */
	public ICache createCache(List<ServerAddress> addressList, CacheConfig cacheConfig);

}
