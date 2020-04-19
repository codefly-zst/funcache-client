package com.tvl.midl.cacheclient.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.exception.TvlCacheConfigException;
import com.tvl.midl.cacheclient.route.local.LocalCacheRouter;

public class CacheRouterFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheRouterFactory.class);

	private static String ROUTER_TYPE_DEFAULT = "static";

	private static String ROUTER_TYPE_STATIC = "static";

	private static String ROUTER_TYPE_DYNAMIC = "dynamic";

	public static ICacheRouter createCacheRouter(String routerType) {
		if (routerType == null || "".equals(routerType)) {
			routerType = ROUTER_TYPE_DEFAULT;
		}
		if (routerType.equals(ROUTER_TYPE_STATIC)) {
			return new LocalCacheRouter();
		}
		if (routerType.equals(ROUTER_TYPE_DYNAMIC)) {
			// 动态获取路由
			LOGGER.warn("----动态获取路由:暂未支持！采用本地配置路由");
			return new LocalCacheRouter();
		}
		throw new TvlCacheConfigException("----无效的路由类型:" + routerType);
	}

}
