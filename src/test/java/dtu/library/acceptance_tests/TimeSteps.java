package dtu.library.acceptance_tests;

import dtu.library.app.LibraryApp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TimeSteps {

    LibraryApp library_app;
    ErrorMessageHolder error_message_holder;
    UserHelper user_helper;
    BookHelper book_helper;
    MockDateHolder date_holder;

    public TimeSteps(LibraryApp library_app, ErrorMessageHolder error_message_holder, UserHelper user_helper, BookHelper book_helper, MockDateHolder date_holder) {
        this.library_app = library_app;
        this.error_message_holder = error_message_holder;
        this.user_helper = user_helper;
        this.book_helper = book_helper;
        this.date_holder = date_holder;
    }

    @Given("{int} days have passed")
    public void daysHavePassed(Integer days) {
        date_holder.advancedDateByDates(days);
    }

}

