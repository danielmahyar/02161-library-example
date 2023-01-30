package dtu.library.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryApp {

	private final String ADMIN_PASSWORD = "adminadmin";
	private boolean is_admin_logged_in = false;
	private final ArrayList<Book> books = new ArrayList<>();

	public void addBook(Book book) throws OperationNotAllowedException {
		if(!is_admin_logged_in) { throw new OperationNotAllowedException("Administrator login required"); }
		books.add(book);
	}

	public List<Book> search(String search_text){
		return books.stream()
				.filter(
					book ->
						book.getSignature().contains(search_text) ||
						book.getTitle().contains(search_text) ||
						book.getAuthor().contains(search_text)
				)
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

	public void adminLogout(){
		is_admin_logged_in = false;
	}
}


