package com.tvl.midl.cacheclient.config;

/**
 * @description
 * @author shite.zhu
 * @date 2016年10月10日
 */
public class CacheConfig {
	/**
	 * 路由方式:静态配置、动态获取
	 */
	private String routerType;

	/**
	 * 服务器列表，路由类型为静态时，不能为空，以分号分割。
	 */
	private String servers;

	/**
	 * 客户端类型:Xmemcached、Spy、Jedis等等。对于Memcache目前只支持Xmemcached; 对于Redis目前只支持jedis
	 */
	private String clientType;

	/**
	 * 服务器权重,以分号分割。weights.size必须等于servers.size
	 */
	private String weights;

	private String maxWait = "";

	private String maxConn = "";

	private String minConn = "";

	/**
	 * memcache专用配置 是否启用异步复制。注意:对于memcache而言,启用后,所有写操作都将异步复制。
	 * 如果只想部分操作(如热点数据)启动异步复制,不建议在这里启动,而应该在调用set等事务方法时,告知是否为热点数据，对于热点数据将启动异步复制。
	 */
	private int clusterMode;

	/**
	 * redis专用配置：默认0,赋值为1时将通过JedisPool来产生实例。
	 */
	private int standAloneModel;

	public String getRouterType() {
		return routerType;
	}

	public void setRouterType(String routerType) {
		this.routerType = routerType;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

	public String getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(String maxConn) {
		this.maxConn = maxConn;
	}

	public int getClusterMode() {
		return clusterMode;
	}

	public void setClusterMode(int clusterMode) {
		this.clusterMode = clusterMode;
	}

	public String getMinConn() {
		return minConn;
	}

	public void setMinConn(String minConn) {
		this.minConn = minConn;
	}

	public int getStandAloneModel() {
		return standAloneModel;
	}

	public void setStandAloneModel(int standAloneModel) {
		this.standAloneModel = standAloneModel;
	}

}
