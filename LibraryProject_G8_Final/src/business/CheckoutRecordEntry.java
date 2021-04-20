package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckoutRecordEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3917841371640059290L;
	private BookCopy copy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;

	public CheckoutRecordEntry(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		this.copy = copy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}

	
	public BookCopy getCopy() {
		return copy;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public String toString() {
		return "[" + "checkoutdate:" + checkoutDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", dueDate: "
				+ dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", Item>: " + copy + "]";
	}
}