package objects;

public class ConnectionFactory {

	private String name;
	private String jndiName;
	private long sendTimeout;
	private int maxMessagesPerSession;
	private String reconnectPolicy;
	private boolean serverAffinityEnabled;
	private boolean xaConnectionFactoryEnabled;

	public ConnectionFactory(String name, String jndiName, long sendTimeout, int maxMessagesPerSession, String reconnectPolicy, boolean serverAffinityEnabled, boolean xaConnectionFactoryEnabled) {
		super();
		this.setName(name);
		this.jndiName = jndiName;
		this.sendTimeout = sendTimeout;
		this.maxMessagesPerSession = maxMessagesPerSession;
		this.reconnectPolicy = reconnectPolicy;
		this.serverAffinityEnabled = serverAffinityEnabled;
		this.xaConnectionFactoryEnabled = xaConnectionFactoryEnabled;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public long getSendTimeout() {
		return sendTimeout;
	}

	public void setSendTimeout(long sendTimeout) {
		this.sendTimeout = sendTimeout;
	}

	public int getMaxMessagesPerSession() {
		return maxMessagesPerSession;
	}

	public void setMaxMessagesPesSession(int maxMessagesPesSession) {
		this.maxMessagesPerSession = maxMessagesPesSession;
	}

	public String getReconnectPolicy() {
		return reconnectPolicy;
	}

	public void setReconnectPolicy(String reconnectPolicy) {
		this.reconnectPolicy = reconnectPolicy;
	}

	public boolean isServerAffinityEnabled() {
		return serverAffinityEnabled;
	}

	public void setserverAffinityEnabled(boolean serverAffinityEnabled) {
		this.serverAffinityEnabled = serverAffinityEnabled;
	}

	public boolean isXaConnectionFactoryEnabled() {
		return xaConnectionFactoryEnabled;
	}

	public void setXaConnectionFactoryEnabled(boolean xaConnectionFactoryEnabled) {
		this.xaConnectionFactoryEnabled = xaConnectionFactoryEnabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
