package objects;

public class Transaction {

	private boolean supportsGlobalTransactions;
	private boolean setXATransactionTimeout;
	private int XATransactionTimeout;
	private boolean LastLoggingResource;

	public Transaction(boolean supportsGlobalTransactions,
			boolean setXATransactionTimeout, int xATransactionTimeout,
			boolean lastLoggingResource) {
		super();
		this.supportsGlobalTransactions = supportsGlobalTransactions;
		this.setXATransactionTimeout = setXATransactionTimeout;
		XATransactionTimeout = xATransactionTimeout;
		LastLoggingResource = lastLoggingResource;
	}

	public boolean isSupportsGlobalTransactions() {
		return supportsGlobalTransactions;
	}

	public void setSupportsGlobalTransactions(boolean supportsGlobalTransactions) {
		this.supportsGlobalTransactions = supportsGlobalTransactions;
	}

	public boolean isSetXATransactionTimeout() {
		return setXATransactionTimeout;
	}

	public void setSetXATransactionTimeout(boolean setXATransactionTimeout) {
		this.setXATransactionTimeout = setXATransactionTimeout;
	}

	public int getXATransactionTimeout() {
		return XATransactionTimeout;
	}

	public void setXATransactionTimeout(int xATransactionTimeout) {
		XATransactionTimeout = xATransactionTimeout;
	}

	public boolean isLastLoggingResource() {
		return LastLoggingResource;
	}

	public void setLastLoggingResource(boolean lastLoggingResource) {
		LastLoggingResource = lastLoggingResource;
	}

}
