package objects;

public class DistributedQueue extends Queue {

	public DistributedQueue(String jndiName, String subdeployment,
			String template, DeliveryFailure deliveryFailure,
			String destinationKeys, PriorityDestinationKey priorityDestinationKey) {
		super(jndiName, subdeployment, template, destinationKeys, deliveryFailure, priorityDestinationKey);
	}
}
