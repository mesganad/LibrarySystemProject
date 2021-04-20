package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OverdueDataModel {
	private SimpleStringProperty isbn;
	private SimpleStringProperty id;
	private SimpleIntegerProperty copyNum;
	private SimpleStringProperty title;
	private SimpleStringProperty overdueStatus;

	public String getIsbn() {
		return isbn.get();
	}

	public int getCopyNum() {
		return copyNum.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getOverdueStatus() {
		return overdueStatus.get();
	}

	public String getId() {
		return id.get();
	}

	public OverdueDataModel(String isbn, String id, String title, int copyNum, String overdueStatus) {

		this.isbn = new SimpleStringProperty(isbn);
		this.id = new SimpleStringProperty(id);
		this.copyNum = new SimpleIntegerProperty(copyNum);
		this.title = new SimpleStringProperty(title);
		this.overdueStatus = new SimpleStringProperty(overdueStatus);
	}

	public List<OverdueDataModel> getOverdueList(String isbn) throws EntryDataModelException {
		if (isbn.isEmpty()) {
			throw new EntryDataModelException("isbn cannot be empty!");
		}

		DataAccess da = new DataAccessFacade();

		List<OverdueDataModel> overdueList = new ArrayList<>();

		Map<String, LibraryMember> memberList = da.readMemberMap();
		List<LibraryMember> members = new ArrayList<>();
		LocalDate today = LocalDate.now();
		String overDMessage = "";

		Set<String> keySet = memberList.keySet();
		for (String keys : keySet) {

			members.add(memberList.get(keys));
		}

		for (int k = 0; k < keySet.size(); k++) {

			int size = members.get(k).getCheckoutRecord().getEntries().size();
			for (int j = 0; j < size; j++) {

				if ((members.get(k).getCheckoutRecord().getEntries().get(j).getCopy().getBook().getIsbn()).equals(isbn)
						&& (members.get(k).getCheckoutRecord().getEntries().get(j) != null)) {

					LocalDate dueD = members.get(k).getCheckoutRecord().getEntries().get(j).getDueDate();
					int copyNum = members.get(k).getCheckoutRecord().getEntries().get(j).getCopy().getCopyNum();
					if (today.compareTo(dueD) > 0) {
						overDMessage = "Overdue";
					} else {
						overDMessage = "Not overdue";
					}

					overdueList.add(new OverdueDataModel(isbn, members.get(k).getMemberId(),
							members.get(k).getCheckoutRecord().getEntries().get(j).getCopy().getBook().getTitle(),
							copyNum, overDMessage));

				}

			}

		}

		return overdueList;
	}

}
