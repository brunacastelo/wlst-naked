package objects;

import java.util.List;

public class WorkManager {
	private String name;
	private List<Target> targets;
	private int minCount;
	private int maxCount;

	public WorkManager(String name, List<Target> targets, int maxCount, int minCount) {
		this.name = name;
		this.targets = targets;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Target> getTargets() {
		return targets;
	}

	public void setTargets(List<Target> target) {
		this.targets = target;
	}
	
	public int getMinCount() {
		return minCount;
	}

	public void getMinCount(int minCount) {
		this.minCount = minCount;
	}
	
	public int getMaxCount() {
		return maxCount;
	}

	public void getMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
}
