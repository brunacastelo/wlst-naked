package objects;

public class JmsServer {

	private String name;
	private String persistentStore;
	private String target;
	private int maxMessages;
	private int experationScanInterval;
	private String targetType;

	public JmsServer(String name, String persistentStore, String target, String targetType,
			int maxMessages, int experationScanInterval) {
		super();
		this.name = name;
		this.persistentStore = persistentStore;
		this.target = target;
		this.targetType = targetType;
		this.maxMessages = maxMessages;
		this.experationScanInterval = experationScanInterval;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersistentStore() {
		return persistentStore;
	}

	public void setPersistentStore(String persistentStore) {
		this.persistentStore = persistentStore;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	public int getMaxMessages() {
		return maxMessages;
	}

	public void setMaxMessages(int maxMessages) {
		this.maxMessages = maxMessages;
	}

	public int getExperationScanInterval() {
		return experationScanInterval;
	}

	public void setExperationScanInterval(int experationScanInterval) {
		this.experationScanInterval = experationScanInterval;
	}
	
	

}
