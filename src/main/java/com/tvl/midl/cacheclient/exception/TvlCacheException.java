package com.tvl.midl.cacheclient.exception;

/**
 * @description
 * @author shite.zhu
 * @date 2016年8月16日 上午11:30:00
 * 
 */
public class TvlCacheException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TvlCacheException(String exceptionMsg){
		super(exceptionMsg);
	}

	public TvlCacheException(String exceptionMsg, Throwable tw){
		super(exceptionMsg,tw);
	}
	
}
