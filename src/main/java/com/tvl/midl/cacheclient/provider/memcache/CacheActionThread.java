package com.tvl.midl.cacheclient.provider.memcache;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description
 * @author st.z
 */
public class CacheActionThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheActionThread.class);

	private volatile boolean toStop = false;

	private LinkedBlockingQueue<CacheAction> actionQueue = new LinkedBlockingQueue<CacheAction>();

	public void add(CacheAction action) {
		actionQueue.add(action);
	}

	@Override
	public void run() {
		while (!toStop) {
			CacheAction ca = null;
			try {
				ca = actionQueue.take();
				LOGGER.info("----async:[{}]", ca.getAction());
				String action = ca.getAction();
				if ("write".equals(action)) {
					writeCache(ca);
				} else if ("----remove".equals(action)) {
					removeCache(ca);
				}
			} catch (Exception e) {
				LOGGER.error("----async-action:[{}],异常:{}", ca, e.getMessage());
			}
		}
	}

	public void toStop() {
		this.toStop = true;
	}

	/**
	 * 异步写操作
	 * 
	 * @param ca
	 */
	void writeCache(CacheAction ca) {
		IMemcachedCache cache = ca.getCache();
		if (cache != null) {
			cache.oput(ca.getKey(), ca.getValue());
		}
	}

	/**
	 * 异步删除操作
	 * 
	 * @param ca
	 */
	void removeCache(CacheAction ca) {
		IMemcachedCache cache = ca.getCache();
		if (cache != null) {
			cache.remove(ca.getKey());
		}
	}

}
