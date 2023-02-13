package dtu.library.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dtu.library.app.internal.Book;
import dtu.library.app.LibraryApp;
import dtu.library.app.exceptions.OperationNotAllowedException;
import dtu.library.app.exceptions.TooManyBookException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSteps {

	private final LibraryApp library_app;
	private final ErrorMessageHolder error_message_holder;
	private final UserHelper user_helper;
	private final BookHelper book_helper;
	private List<Book> books;

	/*
	 * Note that the constructor is apparently never called, but there are no null
	 * pointer exceptions regarding that libraryApp is not set. When creating the
	 * BookSteps object, the Cucumber libraries are using that constructor with an
	 * object of class LibraryApp as the default.
	 * 
	 * This also holds for all other step classes that have a similar constructor.
	 * In this case, the <b>same</b> object of class LibraryApp is used as an
	 * argument. This provides an easy way of sharing the same object, in this case
	 * the object of class LibraryApp and the errorMessage Holder, among all step classes.
	 * 
	 * This principle is called <em>dependency injection</em>. More information can
	 * be found in the "Cucumber for Java" book available online from the DTU Library.
	 */
	public BookSteps(LibraryApp library_app, ErrorMessageHolder error_message_holder, UserHelper user_helper, BookHelper book_helper) {
		this.library_app = library_app;
		this.error_message_holder = error_message_holder;
		this.user_helper = user_helper;
		this.book_helper = book_helper;
	}

	@Given("there is a book with title {string}, author {string}, and signature {string}")
	public void thereIsABookWithTitleAuthorAndSignature(String title, String author, String signature) throws Exception {
		book_helper.createBook(title, author, signature);
	}

	@Given("the book is not in the library")
	public void theBookIsNotInTheLibrary() {
		assertFalse(library_app.containsBookWithSignature(book_helper.getBook().getSignature()));
	}


	@Given("these books are contained in the library")
	public void theseBooksAreContainedInTheLibrary(List<List<String>> books) throws Exception {
		for (List<String> bookInfo : books) {
			library_app.addBook(new Book(bookInfo.get(0), bookInfo.get(1), bookInfo.get(2)));
		}
	}

	@When("the book is added to the library")
	public void bookIsAddedToTheLibrary() {
		try {
			library_app.addBook(book_helper.getBook());
		} catch (OperationNotAllowedException e) {
			error_message_holder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the book with title {string}, author {string}, and signature {string} is contained in the library")
	public void theBookWithTitleAuthorAndSignatureIsContainedInTheLibrary(String title, String author, String signature)
			throws Exception {
		assertTrue(library_app.containsBookWithSignature(signature));
	}

	@Then("the error message {string} is given")
	public void theErrorMessageIsGiven(String errorMessage) throws Exception {
		assertEquals(errorMessage, this.error_message_holder.getErrorMessage());
	}

	@Given("the library has a book with title {string}, author {string}, and signature {string}")
	public void theLibraryHasABookWithTitleAuthorAndSignature(String title, String author, String signature)
			throws Exception {
		Book book = new Book(title, author, signature);
		library_app.addBook(book);
	}

	@When("the user searches for the text {string}")
	public void theUserSearchesForTheText(String searchText) throws Exception {
		books = library_app.search(searchText);
	}

	@Then("the book with signature {string} is found")
	public void theBookWithSignatureIsFound(String signature) throws Exception {
		assertEquals(1, books.size());
		assertEquals(signature, books.get(0).getSignature());
	}

	@Then("no books are found")
	public void noBooksAreFound() throws Exception {
		assertTrue(books.isEmpty());
	}

	@Then("the books with signatures {string} and {string} are found")
	public void theBooksWithSignaturesAndAreFound(String signature1, String signature2) throws Exception {
		assertEquals(2, books.size());
		Book book1 = books.get(0);
		Book book2 = books.get(1);
		assertTrue((book1.getSignature().equals(signature1) && book2.getSignature().equals(signature2))
				|| (book1.getSignature().equals(signature2) && book2.getSignature().equals(signature1)));
	}

	@Given("a book in the library")
	public void aBookInTheLibrary() {
		book_helper.createBook("Test", "John Doe", "1234567890");
		library_app.adminLogin("adminadmin");
		try {
			library_app.addBook(book_helper.getBook());
		} catch (OperationNotAllowedException e) {
			error_message_holder.setErrorMessage(e.getMessage());
		}
		library_app.adminLogout();
	}
	@And("the user has not borrowed the book")
	public void theUserHasNotBorrowedTheBook() {
		assertFalse(library_app.userHasBorrowedBook(user_helper.getUser().getCPR(), book_helper.getBook().getSignature()));
	}

	@When("the user borrows the book")
	public void theUserBorrowsTheBook() {
		try {
			library_app.borrowBook(user_helper.getUser().getCPR(), book_helper.getBook().getSignature());
		} catch (Exception e) {
			error_message_holder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the book is borrowed by the user")
	public void theBookIsBorrowedByTheUser() {
		assertTrue(library_app.userHasBorrowedBook(user_helper.getUser().getCPR(), book_helper.getBook().getSignature()));
	}

	@Given("the user has borrowed {int} books")
	public void theUserHasBorrowedBooks(int amount_of_books) throws OperationNotAllowedException {
		List<Book> books = getExampleBooks(amount_of_books);
		addBooksToLibrary(books);
		for (Book book : books) {
			try{
				library_app.borrowBook(user_helper.getUser().getCPR(), book.getSignature());
			} catch (TooManyBookException e) {
				error_message_holder.setErrorMessage(e.getMessage());
			}
		}
	}

	@And("a book is in the library")
	public void aBookIsInTheLibrary() {
		book_helper.createBook("Test", "John Doe", "1234567890");
		library_app.adminLogin("adminadmin");
		try {
			library_app.addBook(book_helper.getBook());
		} catch (OperationNotAllowedException e) {
			error_message_holder.setErrorMessage(e.getMessage());
		}
		library_app.adminLogout();
	}

	@Then("the book is not borrowed by the user")
	public void theBookIsNotBorrowedByTheUser() {
		assertFalse(library_app.userHasBorrowedBook(user_helper.getUser().getCPR(), book_helper.getBook().getSignature()));
	}

	@And("the user gets the error message {string}")
	public void theUserGetsTheErrorMessage(String error_message) {
		assertEquals(error_message, this.error_message_holder.getErrorMessage());
	}

	@And("the user returns the book")
	public void theUserReturnsTheBook() {
		try {
			library_app.returnBook(user_helper.getUser().getCPR(), book_helper.getBook().getSignature());
		} catch (OperationNotAllowedException e) {
			error_message_holder.setErrorMessage(e.getMessage());
		}
	}

	private List<Book> getExampleBooks(int amount_of_books) {
		List<Book> list = new ArrayList<>();
		for (int index = 0; index < amount_of_books; index++) {
			Book temp_book = new Book("Title " + index, "Author " + index, "Signature " + index);
			list.add(temp_book);
		}
		return list;
	}

	private void addBooksToLibrary(List<Book> books) throws OperationNotAllowedException {
		library_app.adminLogin("adminadmin");
		for (Book book : books) {
			library_app.addBook(book);
		}
		library_app.adminLogout();
	}

	@Given("a book with signature {string} is in the library")
	public void aBookWithSignatureIsInTheLibrary(String signature) throws OperationNotAllowedException {
		book_helper.createBook("Mein Kampf", "A known person", signature);
		addBooksToLibrary(Collections.singletonList(book_helper.getBook()));
	}

	@Given("the user has borrowed a book")
	public void theUserHasBorrowedABook() throws Exception {
		library_app.adminLogin("adminadmin");
		library_app.registerNewUser(user_helper.getUser());
		book_helper.createBook("Mein Kampf", "A known person", "signaturefromexample00");
		library_app.addBook(book_helper.getBook());
		library_app.adminLogout();
		library_app.borrowBook(user_helper.getUser().getCPR(), book_helper.getBook().getSignature());
	}

	@And("the fine for one overdue book is {int} DKK")
	public void theFineForOneOverdueBookIsDKK(int fine) {
		library_app.setFine(fine);
		assertEquals(library_app.getFine(), fine);
	}
}
