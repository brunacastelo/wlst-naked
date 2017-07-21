package objects;

public class DeliveryFailure {

	private int redeliveryDelayOverride;
	private int redeliveryLimit;
	private String expirationPolicy;
	private String errorDestination;
	private int ttlOverride;
	private String deliveryModeOverride;

	public DeliveryFailure(int redeliveryDelayOverride, int redeliveryLimit,
			String expirationPolicy, String errorDestination, int ttlOverride,
			String deliveryModeOverride) {
		super();
		this.redeliveryDelayOverride = redeliveryDelayOverride;
		this.redeliveryLimit = redeliveryLimit;
		this.expirationPolicy = expirationPolicy;
		this.errorDestination = errorDestination;
		this.ttlOverride = ttlOverride;
		this.deliveryModeOverride = deliveryModeOverride;
	}

	public int getRedeliveryDelayOverride() {
		return redeliveryDelayOverride;
	}

	public void setRedeliveryDelayOverride(int redeliveryDelayOverride) {
		this.redeliveryDelayOverride = redeliveryDelayOverride;
	}

	public int getRedeliveryLimit() {
		return redeliveryLimit;
	}

	public void setRedeliveryLimit(int redeliveryLimit) {
		this.redeliveryLimit = redeliveryLimit;
	}

	public String getExpirationPolixy() {
		return expirationPolicy;
	}

	public void setExpirationPolixy(String expirationPolixy) {
		this.expirationPolicy = expirationPolixy;
	}

	public String getErrorDestination() {
		return errorDestination;
	}

	public void setErrorDestination(String errorDestination) {
		this.errorDestination = errorDestination;
	}

	public int getTtlOverride() {
		return ttlOverride;
	}

	public void setTtlOverride(int ttlOverride) {
		this.ttlOverride = ttlOverride;
	}

	public String getDeliveryModeOverride() {
		return deliveryModeOverride;
	}

	public void setDeliveryModeOverride(String deliveryModeOverride) {
		this.deliveryModeOverride = deliveryModeOverride;
	}

}
