package objects;

public class JdbcStore {

	private String name;
	private String prefixName;
	private String target;
	private String dataSource;

	public JdbcStore(String name, String prefixName, String target,
			String dataSource) {
		super();
		this.name = name;
		this.prefixName = prefixName;
		this.target = target;
		this.dataSource = dataSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

}
