package dtu.library.acceptance_tests;

import dtu.library.app.Book;

public class BookHelper {
    private Book book;

    public void createBook(String title, String author, String signature){
        book = new Book(title, author, signature);
    }

    public Book getBook() {
        return book;
    }
}
