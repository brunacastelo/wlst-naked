package objects;

import java.util.List;

public class DataSource {

	private String jndiName;
	private List<Target> targets;
	private ConnectionPool connectionPool;
	private Transaction transaction;

	public DataSource(String jndiName, List<Target> targets,
			ConnectionPool connectionPool, Transaction transaction) {
		super();
		this.jndiName = jndiName;
		this.targets = targets;
		this.connectionPool = connectionPool;
		this.transaction = transaction;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public List<Target> getTargets() {
		return targets;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
