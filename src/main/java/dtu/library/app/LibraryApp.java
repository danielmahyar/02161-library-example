package dtu.library.app;

import dtu.library.app.dto.UserInfo;
import dtu.library.app.exceptions.MissingPaymentException;
import dtu.library.app.exceptions.OperationNotAllowedException;
import dtu.library.app.exceptions.OverdueMediaException;
import dtu.library.app.exceptions.TooManyMediaException;
import dtu.library.app.domain.Book;
import dtu.library.app.domain.User;
import dtu.library.app.servers.DateServer;
import dtu.library.app.servers.EmailServer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LibraryApp {

	private final String ADMIN_PASSWORD = "adminadmin";
	private boolean is_admin_logged_in = false;
	private final List<Book> books = new ArrayList<>();
	private final List<User> users = new ArrayList<>();

	private DateServer date_server = new DateServer();
	private EmailServer email_server = new EmailServer();

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

	public void borrowBook(String CPR, String signature) throws Exception {
		User user = getUserFromCPR(CPR);
		Book book = getBookFromSignature(signature);
		user.borrowBook(book, getDate());
	}

	public boolean userIsRegistered(String CPR) {
		return users.stream().anyMatch(user -> user.match(CPR));
	}

	public boolean userHasOverdueMedia(UserInfo ui){
		User user = getUserFromCPR(ui.getCPR());
		return user.hasOverdueMedia(getDate());
	}

	public void sendReminder() throws OperationNotAllowedException {
		checkIfAdminLoggedIn();
		Calendar current_date = getDate();
		users.stream()
				.filter(u ->  u.hasOverdueMedia(current_date))
				.forEach(u -> {
					u.sendEmailReminder(email_server, current_date);
			});
	}

	private void checkIfAdminLoggedIn() throws OperationNotAllowedException {
		if(!adminLoggedIn()){
			throw new OperationNotAllowedException("Administrator required to login");
		}
	}

	public double getFineForUser(UserInfo ui) {
		User user = getUserFromCPR(ui.getCPR());
		return user.getFine(getDate());
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

	public void setEmailServer(EmailServer email_server){
		this.email_server = email_server;
	}

	public Calendar getDate(){
		return date_server.getDate();
	}

	public void payFine(UserInfo u, int money) {
		User user = getUserFromCPR(u.getCPR());
		user.payFine(money);
	}

	public boolean canBorrow(UserInfo u) {
		User user = getUserFromCPR(u.getCPR());
		try {
			user.canBorrowMedium(getDate());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void unregisterUser(UserInfo u) throws Exception {
		User user = getUserFromCPR(u.getCPR());
		checkIfAdminLoggedIn();
		if (!users.contains(user)) {
			throw new Exception("User not registered");
		}
		if (!user.getBorrowedMedium().isEmpty()) {
			throw new Exception("Can't unregister user: user has still borrowed books/CDs");
		}
		if (user.getFine(date_server.getDate()) > 0) {
			throw new Exception("Can't unregister user: user has still fines to pay");
		}
		users.remove(user);
	}

	public Stream<User> getUsers(){
		return users.stream();
	}
}


