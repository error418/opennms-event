package com.github.error418.opennms.client.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpConnector implements Connector {
	private final DatagramSocket socket;
	
	private final InetAddress address;
	private final int port;

	public UdpConnector(final InetAddress onmsAddress, final int port) throws ConnectorException {
		this.address = onmsAddress;
		this.port = port;
		
		try {
			socket = new DatagramSocket();
		} catch (IOException e) {
			throw new ConnectorException("could not establish socket.", e);
		}
	}

	public void send(String data) throws ConnectorException {
		DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			throw new ConnectorException("could not send packet", e);
		}
	}
}
