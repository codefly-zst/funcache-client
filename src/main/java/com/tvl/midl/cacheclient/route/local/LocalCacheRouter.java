package com.tvl.midl.cacheclient.route.local;

import java.util.ArrayList;
import java.util.List;

import com.tvl.midl.cacheclient.ServerAddress;
import com.tvl.midl.cacheclient.config.CacheConfig;
import com.tvl.midl.cacheclient.route.ICacheRouter;


/**
 * 
 * @description
 * @author st.z
 */
public class LocalCacheRouter implements ICacheRouter {

	@Override
	public List<ServerAddress> getAddressList(CacheConfig cacheConfig) {
		String[] srvs = this.getSrvs(cacheConfig.getServers());
		int[] ports = this.getPorts(cacheConfig.getServers());
		String[] weights = this.getWeis(cacheConfig.getWeights());
		List<ServerAddress> list = new ArrayList<ServerAddress>();
		for (int i = 0; i < srvs.length; i++) {
			ServerAddress fr = new ServerAddress();
			fr.setHost(srvs[i]);
			fr.setPort(ports[i]);
			if (weights != null && weights.length == srvs.length) {
				fr.setWeight(weights[i]);
			}
			list.add(fr);
		}
		return list;
	}

	/**
	 * 获取服务器地址列表
	 * 
	 * @return
	 */
	private String[] getSrvs(String servers) {
		String[] sArrs = servers.split(";");
		String[] srvs = new String[sArrs.length];
		for (int i = 0; i < sArrs.length; i++) {
			String srv = sArrs[i].split(":")[0];
			srvs[i] = srv;
		}
		return srvs;
	}

	/**
	 * 获取服务器端口列表
	 * 
	 * @return
	 */
	private int[] getPorts(String servers) {
		String[] sArrs = servers.split(";");
		int[] srvs = new int[sArrs.length];
		for (int i = 0; i < sArrs.length; i++) {
			String port = sArrs[i].split(":")[1];
			srvs[i] = Integer.parseInt(port);
		}
		return srvs;
	}

	/**
	 * 获取服务器权重列表
	 * 
	 * @return
	 */
	private String[] getWeis(String weights) {
		if (weights != null) {
			String[] sArrs = weights.split(";");
			return sArrs;
		}
		return null;
	}

}
