package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class EntryDataModel {
	private String title;
	private LocalDate checkoutD;
	private LocalDate dueD;

	public EntryDataModel(String title, LocalDate checkoutD, LocalDate dueD) {

		this.title = title;
		this.checkoutD = checkoutD;
		this.dueD = dueD;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getCheckoutD() {
		return checkoutD;
	}

	public LocalDate getDueD() {
		return dueD;
	}

	public static List<EntryDataModel> getEntryList(String id) throws EntryDataModelException {
		if (id.isEmpty()) {
			throw new EntryDataModelException("Id cannot be empty!");
		}
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		if (member == null) {
			throw new EntryDataModelException("Id doesn't exist!");
		}
		CheckoutRecord cor = member.getCheckoutRecord();

		List<CheckoutRecordEntry> CheckoutRecords = cor.getEntries();
		List<EntryDataModel> entryList = new ArrayList<>();
		for (CheckoutRecordEntry coe : CheckoutRecords) {
			entryList.add(new EntryDataModel(coe.getCopy().getBook().getTitle(),  coe.getCheckoutDate(),coe.getDueDate()));
		}

		return entryList;
	}

}
