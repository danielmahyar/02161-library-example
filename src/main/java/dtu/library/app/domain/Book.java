package dtu.library.app.domain;

import dtu.library.app.dto.BookInfo;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class represents a book with title, author, and signature, where signature
 * is a unique key used by the librarian to identify the book. Very often it is 
 * composed of the first letters of the authors plus the year the book was published.
 * @author Hubert
 *
 */
public class Book {

    private String title, author, signature;
    private Calendar due_date;

    private static final double FINE_DKK = 100;
    private static final int MAX_NUMBER_OF_DAYS = 28;

    public Book(String title, String author, String signature){
        this.title = title;
        this.author = author;
        this.signature = signature;
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

    public boolean match(String search_text){
        return 	getSignature().contains(search_text) ||
                getTitle().contains(search_text) ||
                getAuthor().contains(search_text);
    }

    public void setDueDateFromBorrowDate(Calendar borrow_date){
        setDueDate(new GregorianCalendar());
        getDueDate().setTime(borrow_date.getTime());
        getDueDate().add(Calendar.DAY_OF_YEAR, getMaxNumberOfDays());
    }

    public BookInfo asMediumInfo(){
        return new BookInfo(getTitle(), getAuthor(), getSignature());
    }

    public int getMaxNumberOfDays(){
        return MAX_NUMBER_OF_DAYS;
    }

    public double getFine(){
        return FINE_DKK;
    }

    public void setDueDate(Calendar due_date){
        assert due_date != null;
        this.due_date = due_date;
    }

    public Calendar getDueDate(){
        return due_date;
    }

    public boolean isOverdue(Calendar current_date) {
        assert getDueDate() != null;
        return current_date.after(getDueDate());
    }
}
