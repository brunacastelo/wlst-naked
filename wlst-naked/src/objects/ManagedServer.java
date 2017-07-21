package objects;

public class ManagedServer {

	private String name;
	private String address;
	private int port;
	private String machine;
	private String cluster;

	public ManagedServer(String name, String address, int port, String machine, String cluster) {
		super();
		this.name = name;
		this.setAddress(address);
		this.port = port;
		this.machine = machine;
		this.cluster = cluster;
	}

	public ManagedServer(String name, int port, String machine) {
		super();
		this.name = name;
		this.port = port;
		this.machine = machine;
		this.cluster = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
