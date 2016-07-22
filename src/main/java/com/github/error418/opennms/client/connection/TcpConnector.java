package com.github.error418.opennms.client.connection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpConnector implements Connector {
	private final Socket socket;

	public TcpConnector(final InetAddress onmsAddress, final int port) throws ConnectorException {
		try {
			socket = new Socket(onmsAddress, port);
		} catch (IOException e) {
			throw new ConnectorException("could not establish socket connection.", e);
		}
	}

	public void send(String data) throws ConnectorException {
		try {
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			out.write(data, 0, data.length());
			out.flush();
		} catch (IOException e) {
			throw new ConnectorException("could not send message", e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				// print stacktrace
				e.printStackTrace();
			}
		}
	}
}
