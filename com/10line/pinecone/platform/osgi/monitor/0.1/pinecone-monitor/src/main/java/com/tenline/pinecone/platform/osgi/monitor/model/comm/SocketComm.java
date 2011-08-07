package com.tenline.pinecone.platform.osgi.monitor.model.comm;

/**
 * @author wyq
 */
public class SocketComm extends CommType {
	private String port;
	private String ip;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
