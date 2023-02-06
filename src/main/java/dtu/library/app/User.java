package dtu.library.app;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String CPR, name, email, address, city;
    private int post_code, borrowed_books_amount;
    private final List<Book> borrowed_books = new ArrayList<>();

    public boolean match(String CPR){
        return getCPR().contentEquals(CPR);
    }

    public void borrowBook(Book book_to_borrow) throws TooManyBookException {
        if(getAmountOfBorrowedBooks() > 10){
            throw new TooManyBookException("Can't borrow more than 10 books");
        }
        borrowed_books.add(book_to_borrow);
    }

    public boolean userHasBorrowedBook(Book book){
        return borrowed_books.stream().anyMatch(b -> b.getSignature().equals(book.getSignature()));
    }

    public int getAmountOfBorrowedBooks(){
        return borrowed_books_amount;
    }

    /**
     * Field getters
     */

    public String getCPR() {
        return CPR;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getPostCode() {
        return post_code;
    }

    public String getCity() {
        return city;
    }


    /**
     * Field setters
     */
    public User setCPR(String CPR) {
        this.CPR = CPR;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public User setPostCode(int post_code) {
        this.post_code = post_code;
        return this;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public void setBorrowedBooksAmount(int num){
        borrowed_books_amount = num;
    }
}
