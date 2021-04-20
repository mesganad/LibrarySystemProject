package business;

import java.io.Serializable;

public class AddBookException extends Exception implements Serializable {

	private static final long serialVersionUID = -5387531683856117621L;

	public AddBookException() {
		super();
	}

	public AddBookException(String msg) {
		super(msg);
	}

	public AddBookException(Throwable t) {
		super(t);
	}

}
