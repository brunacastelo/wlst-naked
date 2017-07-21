package objects;

import java.util.List;

public class Cluster {

	private String name;
	private String multicastAddress;
	private int multicastPort;
	private List<Address> address;

	public Cluster(String name, String multicastAddress, int multicastPort,
			List<Address> address) {
		super();
		this.name = name;
		this.multicastAddress = multicastAddress;
		this.multicastPort = multicastPort;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMulticastAddress() {
		return multicastAddress;
	}

	public void setMulticastAddress(String multicastAddress) {
		this.multicastAddress = multicastAddress;
	}

	public int getMulticastPort() {
		return multicastPort;
	}

	public void setMulticastPort(int multicastPort) {
		this.multicastPort = multicastPort;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
}
