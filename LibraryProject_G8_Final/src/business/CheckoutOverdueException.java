package business;

import java.io.Serializable;

public class CheckoutOverdueException extends Exception implements Serializable {

	private static final long serialVersionUID = -5387531683856117621L;

	public CheckoutOverdueException() {
		super();
	}

	public CheckoutOverdueException(String msg) {
		super(msg);
	}

	public CheckoutOverdueException(Throwable t) {
		super(t);
	}

}
