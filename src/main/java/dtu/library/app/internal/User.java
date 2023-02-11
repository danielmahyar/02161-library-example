package dtu.library.app.internal;

import dtu.library.app.*;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String CPR, name, email;
    private Address address;
    private final List<Book> borrowed_books = new ArrayList<>();

    public User(String CPR, String name, String email){
        this.CPR = CPR;
        this.name = name;
        this.email = email;
    }

    public User(UserInfo user_info) {
        this.CPR = user_info.getCPR();
        this.email = user_info.getEmail();
        this.name = user_info.getName();
        this.address = user_info.getAddress();
    }

    public boolean match(String CPR){
        return getCPR().contentEquals(CPR);
    }

    public void borrowBook(Book book_to_borrow) throws TooManyBookException {
        if(getAmountOfBorrowedBooks() >= 10){
            throw new TooManyBookException("Can’t borrow more than 10 books");
        }
        borrowed_books.add(book_to_borrow);
    }

    public boolean hasBorrowed(Book book) {
        return borrowed_books.contains(book);
    }

    public void returnBook(Book book) throws OperationNotAllowedException {
        if(!borrowed_books.contains(book)) {
            throw new OperationNotAllowedException("Book is not borrowed by the user");
        }
        borrowed_books.remove(book);
    }

    public int getAmountOfBorrowedBooks() {
        return borrowed_books.size();
    }

    public String getCPR() {
        return CPR;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address){
        this.address = address;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return this.CPR.equals(user.getCPR()) && this.name.equals(user.getName()) && this.email.equals(user.getEmail())
                && ((this.address == null && user.getAddress() == null) || address.equals(user.getAddress()));
    }

    @Override
    public int hashCode() {
        return CPR.hashCode() ^ name.hashCode() ^ email.hashCode() ^ address.hashCode();
    }


}
