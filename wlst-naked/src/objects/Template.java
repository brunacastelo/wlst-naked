package objects;

public class Template {

	private String name;
	private String destinationKey;

	public Template(String name, String destinationKey) {
		super();
		this.name = name;
		this.destinationKey = destinationKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestinationKeyName() {
		return destinationKey;
	}

	public void setDestinationKeyName(String destinationKeyName) {
		this.destinationKey = destinationKeyName;
	}

}
