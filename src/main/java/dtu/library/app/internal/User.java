package dtu.library.app.internal;

import dtu.library.app.*;
import dtu.library.app.exceptions.OperationNotAllowedException;
import dtu.library.app.exceptions.TooManyBookException;

import java.util.*;

public class User {

    private String CPR, name, email;
    private Address address;
    private final List<Book> borrowed_books = new ArrayList<>();
    private final HashMap<String, Long> date_history = new HashMap<>();

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

    public void borrowBook(Book book_to_borrow, Calendar cal) throws TooManyBookException, OperationNotAllowedException {
        if(getAmountOfBorrowedBooks() >= 10){
            throw new TooManyBookException("Can’t borrow more than 10 books");
        }
        if(hasBorrowed(book_to_borrow)){
            throw new OperationNotAllowedException("Book has already been borrowed");
        }
        borrowed_books.add(book_to_borrow);
        System.out.println("Millis when borrowing the book" + cal.getTimeInMillis());
        date_history.put(book_to_borrow.getSignature(), cal.getTimeInMillis());
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

    public HashMap<String, Long> getBorrowHistory(){
        return date_history;
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
