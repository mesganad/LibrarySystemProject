package dataaccess;

import java.util.HashMap;

import business.Book;
import business.LibraryMember;
import javafx.beans.property.SimpleStringProperty;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();

	public HashMap<String, User> readUserMap();

	public HashMap<String, LibraryMember> readMemberMap();

	public LibraryMember searchMember(String id);

	public Book searchBook(String isbn);

	public void saveMember(LibraryMember member);

	public void saveNewMember(LibraryMember member);

	public void saveBook(Book book);

	void saveNewBook(Book book);
}
