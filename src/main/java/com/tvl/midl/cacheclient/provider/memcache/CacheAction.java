package com.tvl.midl.cacheclient.provider.memcache;

/**
 * @description
 * @author st.z
 */
public class CacheAction {

	private IMemcachedCache cache;

	private String key;

	private int expire;

	private Object value;

	private String action;

	public IMemcachedCache getCache() {
		return cache;
	}

	public void setCache(IMemcachedCache cache) {
		this.cache = cache;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
