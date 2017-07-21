package objects;

public class PriorityDestinationKey {

	private String name;
	private String sortKey;
	private String keyType;
	private String direction;

	public PriorityDestinationKey(String name, String sortKey, String keyType,
			String direction) {
		super();
		this.setName(name);
		this.sortKey = sortKey;
		this.keyType = keyType;
		this.direction = direction;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
