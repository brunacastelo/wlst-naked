package objects;

import java.util.List;

public class Domain {

	private String name;
	private String user;
	private String password;
	private String url;
	private List<Machine> machines;
	private AdminServer adminServer;
	private List<ManagedServer> managedServers;
	private List<Cluster> clusters;
	

	public Domain(String name, String user, String password, String url, List<Machine> machines, AdminServer adminServer, List<ManagedServer> managedServers, List<Cluster> clusters) {
		super();
		this.setName(name);
		this.user = user;
		this.password = password;
		this.url = url;
		this.machines = machines;
		this.adminServer = adminServer;
		this.managedServers = managedServers;
		this.clusters = clusters;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Machine> getMachines() {
		return machines;
	}

	public void setMachines(List<Machine> machines) {
		this.machines = machines;
	}

	public AdminServer getAdminServer() {
		return adminServer;
	}

	public void setAdminServer(AdminServer adminServer) {
		this.adminServer = adminServer;
	}

	public List<ManagedServer> getManagedServers() {
		return managedServers;
	}

	public void setManagedServers(List<ManagedServer> managedServers) {
		this.managedServers = managedServers;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
