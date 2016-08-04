package org.sprintdragon.ipc;

import java.net.InetSocketAddress;

public class Config {
	private int soLinger = -1;
	private int connectTimeout = 10;
	private int readTimeout = 30;
	private int idleTimeout = 30;
	private int heartBeatRate = 15;
	private int heartBeatTimeout = 10;
	private int sendBufferSize = 256;
	private boolean ssl = false;
	private boolean tcpNoDelay = true;
	private boolean reuseAddress = true;
	private int receiveBufferSize = 1024;
	private InetSocketAddress remoteAddress = new InetSocketAddress(
			"localhost", 8099);

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public boolean isSsl() {
		return ssl;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	public boolean isReuseAddress() {
		return reuseAddress;
	}

	public void setReuseAddress(boolean reuseAddress) {
		this.reuseAddress = reuseAddress;
	}

	public int getSoLinger() {
		return soLinger;
	}

	public void setSoLinger(int soLinger) {
		this.soLinger = soLinger;
	}

	public int getSendBufferSize() {
		return sendBufferSize;
	}

	public void setSendBufferSize(int sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
	}

	public int getReceiveBufferSize() {
		return receiveBufferSize;
	}

	public void setReceiveBufferSize(int receiveBufferSize) {
		this.receiveBufferSize = receiveBufferSize;
	}

	public InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String addr, int port) {
		this.remoteAddress = new InetSocketAddress(addr, port);
	}

	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public int getHeartBeatRate() {
		return heartBeatRate;
	}

	public void setHeartBeatRate(int heartBeatRate) {
		this.heartBeatRate = heartBeatRate;
	}

	public int getHeartBeatTimeout() {
		return heartBeatTimeout;
	}

	public void setHeartBeatTimeout(int heartBeatTimeout) {
		this.heartBeatTimeout = heartBeatTimeout;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Config config = (Config) o;

		if (soLinger != config.soLinger) return false;
		if (connectTimeout != config.connectTimeout) return false;
		if (readTimeout != config.readTimeout) return false;
		if (idleTimeout != config.idleTimeout) return false;
		if (heartBeatRate != config.heartBeatRate) return false;
		if (heartBeatTimeout != config.heartBeatTimeout) return false;
		if (sendBufferSize != config.sendBufferSize) return false;
		if (ssl != config.ssl) return false;
		if (tcpNoDelay != config.tcpNoDelay) return false;
		if (reuseAddress != config.reuseAddress) return false;
		if (receiveBufferSize != config.receiveBufferSize) return false;
		return remoteAddress != null ? remoteAddress.equals(config.remoteAddress) : config.remoteAddress == null;

	}

	@Override
	public int hashCode() {
		int result = soLinger;
		result = 31 * result + connectTimeout;
		result = 31 * result + readTimeout;
		result = 31 * result + idleTimeout;
		result = 31 * result + heartBeatRate;
		result = 31 * result + heartBeatTimeout;
		result = 31 * result + sendBufferSize;
		result = 31 * result + (ssl ? 1 : 0);
		result = 31 * result + (tcpNoDelay ? 1 : 0);
		result = 31 * result + (reuseAddress ? 1 : 0);
		result = 31 * result + receiveBufferSize;
		result = 31 * result + (remoteAddress != null ? remoteAddress.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Config{" +
				"soLinger=" + soLinger +
				", connectTimeout=" + connectTimeout +
				", readTimeout=" + readTimeout +
				", idleTimeout=" + idleTimeout +
				", heartBeatRate=" + heartBeatRate +
				", heartBeatTimeout=" + heartBeatTimeout +
				", sendBufferSize=" + sendBufferSize +
				", ssl=" + ssl +
				", tcpNoDelay=" + tcpNoDelay +
				", reuseAddress=" + reuseAddress +
				", receiveBufferSize=" + receiveBufferSize +
				", remoteAddress=" + remoteAddress +
				'}';
	}
}