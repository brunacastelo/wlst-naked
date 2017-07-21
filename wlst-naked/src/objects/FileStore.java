package objects;

public class FileStore {

	private String name;
	private String path;
	private String target;
	private String targetType;

	public FileStore(String name, String path, String target, String targetType) {
		super();
		this.name = name;
		this.path = path;
		this.target = target;
		this.targetType = targetType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

}
