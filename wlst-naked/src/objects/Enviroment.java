package objects;

import java.util.List;

public class Enviroment {

	private String template;
	private String endereco;
	private Domain domain;
	private List<DataSource> dataSources;
	private List<FileStore> fileStores;
	private List<JdbcStore> jdbcStores;
	private List<JmsServer> jmsServer;
	private List<JmsModule> jmsModule;
	private List<WorkManager> workManager;
	private String weblogic;
	private String url;
	private String user;

	public Enviroment(String template, String endereco, Domain domain, List<DataSource> dataSources, List<FileStore> fileStores, List<JdbcStore> jdbcStores, List<JmsServer> jmsServer, List<JmsModule> jmsModule, String weblogic, String url, String user, List<WorkManager> workManagers) {
		super();
		this.template = template;
		this.endereco = endereco;
		this.domain = domain;
		this.dataSources = dataSources;
		this.fileStores = fileStores;
		this.jdbcStores = jdbcStores;
		this.jmsServer = jmsServer;
		this.jmsModule = jmsModule;
		this.setWorkManager(workManagers);
		this.weblogic = weblogic; 
		this.url = url; 
		this.user = user;
	}

	public List<JdbcStore> getJdbcStores() {
		return jdbcStores;
	}

	public void setJdbcStores(List<JdbcStore> jdbcStores) {
		this.jdbcStores = jdbcStores;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public List<DataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public List<FileStore> getFileStores() {
		return fileStores;
	}

	public void setFileStores(List<FileStore> fileStores) {
		this.fileStores = fileStores;
	}

	public List<JmsServer> getJmsServer() {
		return jmsServer;
	}

	public void setJmsServer(List<JmsServer> jmsServer) {
		this.jmsServer = jmsServer;
	}

	public List<JmsModule> getJmsModule() {
		return jmsModule;
	}

	public void setJmsModule(List<JmsModule> jmsModule) {
		this.jmsModule = jmsModule;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getWeblogicPassword() {
		return weblogic;
	}

	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}

	public List<WorkManager> getWorkManager() {
		return workManager;
	}

	public void setWorkManager(List<WorkManager> workManager) {
		this.workManager = workManager;
	}

}
