package objects;

public class Target {
	
	public enum TargetType {
		Server,
		Cluster,
		JMSServer
	}

	private String name;
	private TargetType type;

	public Target(String name, TargetType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public TargetType getType() {
		return type;
	}

	public void setType(TargetType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
