package business;

import java.io.Serializable;

public class EntryDataModelException extends Exception implements Serializable {

	private static final long serialVersionUID = -5387531683856117621L;

	public EntryDataModelException() {
		super();
	}

	public EntryDataModelException(String msg) {
		super(msg);
	}

	public EntryDataModelException(Throwable t) {
		super(t);
	}

}
