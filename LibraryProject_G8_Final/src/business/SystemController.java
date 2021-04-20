package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static String idTemp = null;
	//LocalDate dueDate=LocalDate.now();

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		idTemp = map.get(id).getId();

	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	/*
	 * private void saveNewMember(LibraryMember member) { DataAccess da = new
	 * DataAccessFacade(); da.saveNewMember(member);
	 * 
	 * }
	 */

	public void addMember(String id, String firstName, String lastName, String street, String city, String state,
			String zip, String telephone) throws AddMemberException {

		if (Validations.isEmpty(id, firstName, lastName, street, city, state, zip, telephone))
			throw new AddMemberException("All fields must be not empty.");

		if (!Validations.isNumeric(id))
			throw new AddMemberException("ID field must be Numeric.");

		if (!Validations.isNumeric(zip)) {
			throw new AddMemberException("Zip field must be Numeric.");
		}

		if (!Validations.isExactLength(zip, 5)) {
			throw new AddMemberException("Zip field must be Numeric with exqctly 5 digits.");
		}
		if (!Validations.isExactLength(state, 2)) {
			throw new AddMemberException("State field must have exactly two characters.");
		}

		if (!Validations.isAllCapitals(state)) {
			throw new AddMemberException("State field must have exactly two characters in the range A-Z.");
		}

		if (Validations.isIdEqualZip(id, zip)) {
			throw new AddMemberException("ID field may not equal zip.");
		}
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		if (member != null) {
			throw new AddMemberException("Member ID exist please change ID");
		}

		Address newMemberAddress = new Address(street, city, state, zip);
		LibraryMember newMember = new LibraryMember(id, firstName, lastName, telephone, newMemberAddress);
		// saveNewMember(newMember);
		da.saveNewMember(newMember);
	}

	public String checkoutBook(String memberId, String isbn, String chkoutDate) throws CheckoutException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if (member == null) {
			throw new CheckoutException("Member not found.");
		}
		Book book = da.searchBook(isbn);
		if (book == null) {
			throw new CheckoutException("Book not found.");
		}
		// check availability of a book copy
		if (!book.isAvailable()) {
			throw new CheckoutException("No Book copy available.");
		}
		BookCopy bookCopy = book.getNextAvailableCopy();

		// get max number of days for checkout of the book
		int maxCheckoutLength = book.getMaxCheckoutLength();
		LocalDate today = LocalDate.now();
		if (chkoutDate != "") {
			today = LocalDate.parse(chkoutDate);
		}

		 LocalDate dueDate = LocalDate.now().plusDays(maxCheckoutLength);
		dueDate = today.plusDays(maxCheckoutLength);
		member.checkout(bookCopy, today, dueDate);
		da.saveMember(member);
		da.saveBook(book);
		if (LocalDate.now().compareTo(dueDate) > 0) {
			throw new CheckoutException("This Book Is Overdue");
		}
		return "Successfully Checkedout.";
	}

	public String addBookCopy(String isbn) throws AddCopyException {
		DataAccess da = new DataAccessFacade();
		Book book = da.searchBook(isbn);
		if (book == null) {
			throw new AddCopyException(" Book with isbn '" + isbn + "' is not found.");
		}
		int previous = book.getNumCopies();
		book.addCopy();
		da.saveBook(book);

		return "Successfully Copy Added. previous number of book copy was " + previous + " and now is "
				+ book.getNumCopies();

	}

	List<Author> authors = new ArrayList<>();

	@Override
	public void addAuthor(String firstName, String lastName, String bio, String street, String city, String state,
			String zip, String telephone) throws AddAuthorException {
		if (firstName.isEmpty() || lastName.isEmpty() || bio.isEmpty()||street.isEmpty()||city.isEmpty()||state.isEmpty()||zip.isEmpty()||telephone.isEmpty()) {
			throw new AddAuthorException("Field cannot be empty!");
		}
		Address newAuthorAddress = new Address(street, city, state, zip);
		Author newAuthor = new Author(firstName, lastName, telephone, newAuthorAddress, bio);
		authors.add(newAuthor);

	}

	@Override
	public void addBooks(String title, String isbn, String maxCheckoutLen) throws AddBookException {
		
		if (title.isEmpty() || isbn.isEmpty() || maxCheckoutLen.isEmpty()) {
			throw new AddBookException("Field cannot be empty!");
		}
			

		int maxChkLen = Integer.parseInt(maxCheckoutLen);

		Book newBook = new Book(isbn, title, maxChkLen, authors);
		DataAccess da = new DataAccessFacade();
		if(da.searchBook(isbn)!=null) {
			throw new AddBookException("The book already exists!");
		}
		da.saveNewBook(newBook);
		
		/*
		 * if (da.searchBook("28-12341") == null) { throw new
		 * AddBookException("The book is not found"); }
		 */
	}

	/*
	 * @Override public void checkoutOverdue(String isbn) throws
	 * CheckoutOverdueException {
	 * 
	 * DataAccess da = new DataAccessFacade(); Map<String,Book>
	 * booksMap=da.readBooksMap(); List<Book> bookList=new ArrayList<>();
	 * Set<String>keys=booksMap.keySet(); for(String x: keys) { if(x.equals(isbn)) {
	 * bookList.add(booksMap.get(x)); } } Map<String,
	 * LibraryMember>memberList=da.readMemberMap(); List<LibraryMember>members=new
	 * ArrayList<>(); for(LibraryMember m: memberList.values()) { members.add(m); }
	 * 
	 * List<CheckoutRecordEntry>entryList=new ArrayList<>(); for(int
	 * i=0;i<members.size();i++) {
	 * entryList.addAll(members.get(i).getCheckoutRecord().getEntries());
	 * 
	 * } for(int j=0;j<entryList.size();j++)
	 * 
	 * if((entryList.get(j).getCopy().getBook().getIsbn().equals(isbn))&&
	 * entryList.get(j).getDueDate().compareTo(LocalDate.now())>0) {
	 * 
	 * 
	 * }
	 * 
	 * entryList.get(j).getCopy()
	 * 
	 * if(LocalDate.now().compareTo(dueDate)>0) {
	 * 
	 * 
	 * } }
	 */
	
	public void print(String memberId) throws AddMemberException {
		if(memberId.isEmpty()) {
			throw new AddMemberException("User Id Cannot be Empty!");
		}
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		CheckoutRecord cor = member.getCheckoutRecord();

		List<CheckoutRecordEntry> CheckoutRecords = cor.getEntries();
		for (CheckoutRecordEntry coe : CheckoutRecords) {
		System.out.println(coe.toString() + "checked out by: "+ member.getMemberId());
		}
}
}
