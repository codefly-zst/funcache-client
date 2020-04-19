package com.tvl.midl.cacheclient.exception;

/**
 * @description
 * @author st.z
 */
public class TvlCacheConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public TvlCacheConfigException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param ex
	 */
	public TvlCacheConfigException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
