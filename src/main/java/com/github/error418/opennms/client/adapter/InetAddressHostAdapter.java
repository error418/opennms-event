package com.github.error418.opennms.client.adapter;

import java.net.InetAddress;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class InetAddressHostAdapter extends XmlAdapter<String, InetAddress> {

	@Override
	public String marshal(InetAddress address) throws Exception {
		if (address == null)
			return null;
		return address.getHostAddress();
	}

	@Override
	public InetAddress unmarshal(String address) throws Exception {
		if (address == null)
			return null;
		return InetAddress.getByName(address);
	}

}
