package dtu.library.app.dto;

import dtu.library.app.domain.Book;

public class BookInfo {
    private String title;
    private String author;
    private String signature;

    public BookInfo(String title, String author, String signature) {
        this.title = title;
        this.author = author;
        this.signature = signature;
    }

    public Book asBook() {
        return new Book(this.getTitle(), this.getAuthor(), this.getSignature());
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSignature() {
        return signature;
    }
}