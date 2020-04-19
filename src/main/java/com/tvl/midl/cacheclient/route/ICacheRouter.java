package com.tvl.midl.cacheclient.route;

import java.util.List;

import com.tvl.midl.cacheclient.ServerAddress;
import com.tvl.midl.cacheclient.config.CacheConfig;

/**
 * @description
 * @author st.z
 */
public interface ICacheRouter {

	/**
	 * 获取服务器列表
	 * 
	 * @param pool
	 * @return
	 */
	public List<ServerAddress> getAddressList(CacheConfig cacheConfig);

}
