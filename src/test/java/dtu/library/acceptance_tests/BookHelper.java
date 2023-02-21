package dtu.library.acceptance_tests;

import dtu.library.app.LibraryApp;
import dtu.library.app.domain.Book;
import dtu.library.app.dto.BookInfo;
import dtu.library.app.exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;

public class BookHelper {
    private Book book;
    private final LibraryApp library_app;

    public BookHelper(LibraryApp library_app){
        this.library_app = library_app;
    }

    public void createBook(String title, String author, String signature){
        book = new Book(title, author, signature);
    }

    public Book getExampleBook(){
        return new Book("Title1123", "Authro123e", "ASdaw2ed21da");
    }

    public Book getBook() {
        if(getExampleBook() == null){
            book = getExampleBook();
        }
        return book;
    }

    public void setBook(Book b){
        this.book = b;
    }

    public void addBooksToLibrary(List<BookInfo> books) throws OperationNotAllowedException {
        library_app.adminLogin("adminadmin");
        for (BookInfo book : books) {
            library_app.addBook(book.asBook());
        }
        library_app.adminLogout();
    }

    public List<BookInfo> getExampleBooks(int amount_of_books) {
        List<BookInfo> list = new ArrayList<>();
        for (int index = 0; index < amount_of_books; index++) {
            BookInfo temp_book = new BookInfo("Title " + index, "Author " + index, "Signature " + index);
            list.add(temp_book);
        }
        return list;
    }

}
