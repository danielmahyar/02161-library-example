package dtu.library.acceptance_tests;

import dtu.library.app.Book;
import dtu.library.app.TooManyBookException;
import dtu.library.app.User;

import java.util.List;

public class UserHelper {
    private User user;
    private int amount_of_borrowed_books = 0;

    public void createUser(String CPR, String name, String email, String city, String address, int post_code){
        user = new User();
        user.setCPR(CPR).setName(name).setEmail(email).setCity(city).setAddress(address).setPostCode(post_code);
        user.setBorrowedBooksAmount(amount_of_borrowed_books);
    }

    public void createUser(String CPR, String name, String email){
        user = new User();
        user.setCPR(CPR).setName(name).setEmail(email);
        user.setBorrowedBooksAmount(amount_of_borrowed_books);
    }

    public void addFinalInfo(String address, int post_code, String city){
        user.setCity(city).setAddress(address).setPostCode(post_code);
    }

    public void createUser(String CPR){
        createUser(CPR, "John Doe", "johndoe@gmail.com", "Lyngby", "Skodsborgvej 190", 2850);
    }

    public void setAmountOfBorrowedBooks(int num) {
        this.amount_of_borrowed_books = num;
    }

    public User getUser(){
        return user;
    }
}
