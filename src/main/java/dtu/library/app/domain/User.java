package dtu.library.app.domain;

import dtu.library.app.dto.UserInfo;
import dtu.library.app.exceptions.MissingPaymentException;
import dtu.library.app.exceptions.OperationNotAllowedException;
import dtu.library.app.exceptions.OverdueMediaException;
import dtu.library.app.exceptions.TooManyMediaException;
import dtu.library.app.servers.EmailServer;

import java.util.*;

public class User {

    private String CPR, name, email;
    private static final int MAX_NUMBER_OF_BOOKS = 10;
    private double fine = 0;
    private boolean hasFine = false;
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

    public void borrowBook(Book book_to_borrow, Calendar current_date) throws TooManyMediaException, OperationNotAllowedException, OverdueMediaException, MissingPaymentException {
        canBorrowMedium(current_date);
        if(hasBorrowed(book_to_borrow)){
            throw new OperationNotAllowedException("Book has already been borrowed");
        }
        book_to_borrow.setDueDateFromBorrowDate(current_date);
        borrowed_books.add(book_to_borrow);
    }

    public void canBorrowMedium(Calendar current_date) throws TooManyMediaException, OverdueMediaException, MissingPaymentException {
        if(getAmountOfBorrowedBooks() >= MAX_NUMBER_OF_BOOKS){
            throw new TooManyMediaException(String.format("Can't borrow more than %d books/CDs", MAX_NUMBER_OF_BOOKS));
        }
        if(hasOverdueMedia(current_date)){
            throw new OverdueMediaException("Can't borrow book/CD if user has overdue books/CDs");
        }
        if(hasFine()){
            throw new MissingPaymentException(String.format("Can't borrow book/CD if user has outstanding fines %f DKK", getFine(current_date)));
        }
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

    public List<Book> getBorrowedMedium(){
        return borrowed_books;
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


    public boolean hasOverdueMedia(Calendar current_date) {
        return borrowed_books.stream().anyMatch(b -> b.isOverdue(current_date));
    }

    public void sendEmailReminder(EmailServer email_server, Calendar current_date) {
        long amount_of_overdue_media = borrowed_books.stream().filter(m -> m.isOverdue(current_date)).count();
        email_server.sendEmail(
                email,
                "Overdue book(s)/CD(s)",
                String.format("You have %s overdue book(s)/CD(s)", amount_of_overdue_media));
    }


    public double getFine(Calendar current_date){
        if(!hasFine){
            double fine_val = borrowed_books.stream().filter(m -> m.isOverdue(current_date))
                    .mapToDouble(Book::getFine)
                    .sum();
            if(fine_val == 0){
                fine = 0;
                hasFine = false;
            } else {
                fine = fine_val;
                hasFine = true;
            }
        }
        return hasFine ? fine : 0;
    }

    public boolean hasFine(){
        return hasFine;
    }

    public void payFine(int money){
        if(fine == money){
            fine = 0;
            hasFine = false;
        } else {
            fine = fine - money;
            hasFine = true;
        }
    }

}
