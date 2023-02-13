package dtu.library.app;

import dtu.library.app.exceptions.OperationNotAllowedException;
import dtu.library.app.exceptions.TooManyBookException;
import dtu.library.app.internal.User;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryApp {

	private final String ADMIN_PASSWORD = "adminadmin";
	private int OVERDUE_FINE_IN_DKK = 100;
	private boolean is_admin_logged_in = false;
	private final List<Book> books = new ArrayList<>();
	private final List<User> users = new ArrayList<>();

	private DateServer date_server = new DateServer();

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

	public void registerNewUser(UserInfo new_user) throws OperationNotAllowedException {
		User user = new User(new_user);
		if(!adminLoggedIn()) { throw new OperationNotAllowedException("Administrator login required"); }
		if(userIsRegistered(user.getCPR())){ throw new OperationNotAllowedException("User is already registered"); }
		users.add(user);
	}

	public UserInfo getUser(String CPR){
		return new UserInfo(getUserFromCPR(CPR));
	}


	public User getUserFromCPR(String CPR){
		return users.stream().filter(user -> user.getCPR().equals(CPR)).findFirst().orElse(null);
	}

	public boolean userHasBorrowedBook(String CPR, String signature) {
		User user = getUserFromCPR(CPR);
		Book book = getBookFromSignature(signature);
		return user.hasBorrowed(book);
	}

	public void borrowBook(String CPR, String signature) throws TooManyBookException, OperationNotAllowedException {
		User user = getUserFromCPR(CPR);
		Book book = getBookFromSignature(signature);
		user.borrowBook(book, getDate());
	}

	public boolean userIsRegistered(String CPR) {
		return users.stream().anyMatch(user -> user.match(CPR));
	}

	public boolean userHasOverdueMedia(UserInfo ui){
		User user = getUserFromCPR(ui.getCPR());
		HashMap<String, Long> history = user.getBorrowHistory();
		System.out.println("New time in millis" + getDate().getDisplayName(Calendar.SECOND, Calendar.SHORT, Locale.ENGLISH));
		return history.values().stream().anyMatch(date_in_millis -> (date_in_millis + (29L * 24 * 60 * 60 * 1000)) > getDate().getTimeInMillis());
	}

	public int getFineForUser(UserInfo ui) {
		User user = getUserFromCPR(ui.getCPR());
		return (int) user.getBorrowHistory()
				.values()
				.stream()
				.filter(millis -> millis > getDate().getTimeInMillis())
				.count() * OVERDUE_FINE_IN_DKK;
	}


	public void adminLogout(){
		is_admin_logged_in = false;
	}

	public void returnBook(String CPR, String signature) throws OperationNotAllowedException {
		User user = getUserFromCPR(CPR);
		Book book = getBookFromSignature(signature);
		user.returnBook(book);
	}

	public Book getBookFromSignature(String signature){
		return books.stream().filter(book -> book.getSignature().contentEquals(signature)).findFirst().orElse(null);
	}

	public void setDateServer(DateServer date_server){
		this.date_server = date_server;
	}

	public int getFine(){
		return this.OVERDUE_FINE_IN_DKK;
	}

	public void setFine(int new_fine){
		this.OVERDUE_FINE_IN_DKK = new_fine;
	}

	public Calendar getDate(){
		return date_server.getDate();
	}

}


