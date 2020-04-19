package com.tvl.midl.cacheclient;

/**
 * @description
 * @author st.z
 */
public class ServerAddress {

	private String host;

	private int port;

	private String weight;

//	private String initConn;

//	private String minConn;

//	private String maxConn;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

}
