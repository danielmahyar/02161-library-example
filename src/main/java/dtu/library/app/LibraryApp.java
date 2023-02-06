package dtu.library.app;

import io.cucumber.java.bs.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryApp {

	private final String ADMIN_PASSWORD = "adminadmin";
	private boolean is_admin_logged_in = false;
	private final List<Book> books = new ArrayList<>();
	private final List<User> users = new ArrayList<>();

	public void addBook(Book book) throws OperationNotAllowedException {
		if(!is_admin_logged_in) { throw new OperationNotAllowedException("Administrator login required"); }
		books.add(book);
	}

	public List<Book> search(String search_text){
		return books.stream()
				.filter(book -> book.match(search_text))
				.collect(Collectors.toList());
	}

	public boolean containsBookWithSignature(String signature) {
		return books.stream().anyMatch(book -> book.getSignature().equals(signature));
	}

	public boolean adminLoggedIn(){
		return is_admin_logged_in;
	}

	public boolean adminLogin(String password){
		is_admin_logged_in = ADMIN_PASSWORD.equals(password);
		return adminLoggedIn();
	}

	public void registerNewUser(User new_user) throws OperationNotAllowedException {
		if(!adminLoggedIn()) { throw new OperationNotAllowedException("Administrator login required"); }
		if(userIsRegistered(new_user.getCPR())){ throw new OperationNotAllowedException("User is already registered"); }
		users.add(new_user);
	}

	public boolean userHasBorrowedBook(User user, Book book){
		return user.userHasBorrowedBook(book);
	}

	public void borrowBook(User user, Book book) throws TooManyBookException {
		user.borrowBook(book);
	}

	public boolean userIsRegistered(String CPR){
		return users.stream().anyMatch(user -> user.match(CPR));
	}

	public void adminLogout(){
		is_admin_logged_in = false;
	}
}


