package wlst;

public class WLSTException extends RuntimeException {

	private static final long serialVersionUID = 504355901551947578L;

	public WLSTException() {
		super();
	}

	public WLSTException(String message) {
		super(message);
	}

	public WLSTException(Throwable t) {
		super(t);
	}

	public WLSTException(String s, Throwable t) {
		super(s, t);
	}
}
