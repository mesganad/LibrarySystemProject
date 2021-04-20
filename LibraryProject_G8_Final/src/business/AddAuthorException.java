package business;

import java.io.Serializable;

public class AddAuthorException extends Exception implements Serializable {

	private static final long serialVersionUID = -5387531683856117621L;

	public AddAuthorException() {
		super();
	}

	public AddAuthorException(String msg) {
		super(msg);
	}

	public AddAuthorException(Throwable t) {
		super(t);
	}

}
