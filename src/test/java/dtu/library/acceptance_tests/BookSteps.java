package dtu.library.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import dtu.library.app.Book;
import dtu.library.app.LibraryApp;
import dtu.library.app.OperationNotAllowedException;
import dtu.library.app.TooManyBookException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSteps {

	private final LibraryApp libraryApp;
	private final ErrorMessageHolder errorMessageHolder;
	private final UserHelper user_helper;
	private final BookHelper book_helper;
	private List<Book> books;
	private int books_borrowed;

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
	public BookSteps(LibraryApp libraryApp, ErrorMessageHolder errorMessageHolder, UserHelper user_helper, BookHelper book_helper) {
		this.libraryApp = libraryApp;
		this.errorMessageHolder = errorMessageHolder;
		this.user_helper = user_helper;
		this.book_helper = book_helper;
	}

	@Given("there is a book with title {string}, author {string}, and signature {string}")
	public void thereIsABookWithTitleAuthorAndSignature(String title, String author, String signature) throws Exception {
		book_helper.createBook(title, author, signature);
	}

	@Given("the book is not in the library")
	public void theBookIsNotInTheLibrary() {
		assertFalse(libraryApp.containsBookWithSignature(book_helper.getBook().getSignature()));
	}


	@Given("these books are contained in the library")
	public void theseBooksAreContainedInTheLibrary(List<List<String>> books) throws Exception {
		for (List<String> bookInfo : books) {
			libraryApp.addBook(new Book(bookInfo.get(0), bookInfo.get(1), bookInfo.get(2)));
		}
	}

	@When("the book is added to the library")
	public void bookIsAddedToTheLibrary() {
		try {
			libraryApp.addBook(book_helper.getBook());
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the book with title {string}, author {string}, and signature {string} is contained in the library")
	public void theBookWithTitleAuthorAndSignatureIsContainedInTheLibrary(String title, String author, String signature)
			throws Exception {
		assertTrue(libraryApp.containsBookWithSignature(signature));
	}

	@Then("the error message {string} is given")
	public void theErrorMessageIsGiven(String errorMessage) throws Exception {
		assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());
	}

	@Given("the library has a book with title {string}, author {string}, and signature {string}")
	public void theLibraryHasABookWithTitleAuthorAndSignature(String title, String author, String signature)
			throws Exception {
		Book book = new Book(title, author, signature);
		libraryApp.addBook(book);
	}

	@When("the user searches for the text {string}")
	public void theUserSearchesForTheText(String searchText) throws Exception {
		books = libraryApp.search(searchText);
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
		libraryApp.adminLogin("adminadmin");
		try {
			libraryApp.addBook(book_helper.getBook());
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
		libraryApp.adminLogout();
	}
	@And("the user has not borrowed the book")
	public void theUserHasNotBorrowedTheBook() {
		assertFalse(libraryApp.userHasBorrowedBook(user_helper.getUser(), book_helper.getBook()));
	}

	@When("the user borrows the book")
	public void theUserBorrowsTheBook() {
		try {
			libraryApp.borrowBook(user_helper.getUser(), book_helper.getBook());
		} catch (TooManyBookException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
	}

	@Then("the book is borrowed by the user")
	public void theBookIsBorrowedByTheUser() {
		assertTrue(libraryApp.userHasBorrowedBook(user_helper.getUser(), book_helper.getBook()));
	}

	@Given("the user has borrowed {int} books")
	public void theUserHasBorrowedBooks(int amount_of_books) {
		books_borrowed = amount_of_books;
	}

	@And("a book is in the library")
	public void aBookIsInTheLibrary() {
		book_helper.createBook("Test", "John Doe", "1234567890");
		libraryApp.adminLogin("adminadmin");
		try {
			libraryApp.addBook(book_helper.getBook());
		} catch (OperationNotAllowedException e) {
			errorMessageHolder.setErrorMessage(e.getMessage());
		}
		libraryApp.adminLogout();
	}

	@Then("the book is not borrowed by the user")
	public void theBookIsNotBorrowedByTheUser() {
		assertFalse(libraryApp.userHasBorrowedBook(user_helper.getUser(), book_helper.getBook()));
	}

	@And("the user gets the error message {string}")
	public void theUserGetsTheErrorMessage(String error_message) {
		assertEquals(error_message, this.errorMessageHolder.getErrorMessage());
	}
}
