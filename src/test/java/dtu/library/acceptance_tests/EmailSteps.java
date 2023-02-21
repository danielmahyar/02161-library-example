package dtu.library.acceptance_tests;

import dtu.library.app.LibraryApp;
import dtu.library.app.exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.mockito.Mockito.verify;

public class EmailSteps {

    private final LibraryApp library_app;
    private final ErrorMessageHolder error_message_holder;
    private final UserHelper user_helper;
    private final BookHelper book_helper;
    private MockEmailHolder email_server_holder;

    public EmailSteps(LibraryApp library_app, ErrorMessageHolder error_message_holder, UserHelper user_helper, BookHelper book_helper, MockEmailHolder email_server_holder) {
        this.library_app = library_app;
        this.error_message_holder = error_message_holder;
        this.user_helper = user_helper;
        this.book_helper = book_helper;
        this.email_server_holder = email_server_holder;
    }

    @Then("then the user receives an email with subject {string} and text {string}")
    public void thenTheUserReceivesAnEmailWithSubjectAndText(String subject, String text) {
        verify(email_server_holder.getMockEmailServer()).sendEmail(user_helper.getUser().getEmail(), subject, text);
    }

    @When("the administrator sends a reminder e-mail")
    public void theAdministratorSendsAReminderEMail() {
        try{
            library_app.sendReminder();
        } catch (OperationNotAllowedException e) {
            error_message_holder.setErrorMessage(e.getMessage());
        }
    }
}
