package objects;

public class AdminServer {

	private String name;
	private int port;
	private int sslPort;

	public AdminServer(String name, int port, int sslPort) {
		super();
		this.name = name;
		this.port = port;
		this.sslPort = sslPort;
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

	public int getSslPort() {
		return sslPort;
	}

	public void setSslPort(int sslPort) {
		this.sslPort = sslPort;
	}
}
